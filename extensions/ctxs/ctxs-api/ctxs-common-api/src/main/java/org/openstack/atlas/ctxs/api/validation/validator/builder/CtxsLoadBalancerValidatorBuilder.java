package org.openstack.atlas.ctxs.api.validation.validator.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.atlas.api.validation.validator.builder.*;
import org.openstack.atlas.api.validation.verifier.MustBeEmptyOrNull;
import org.openstack.atlas.api.validation.verifier.Verifier;
import org.openstack.atlas.api.validation.verifier.VerifierResult;
import org.openstack.atlas.core.api.v1.LoadBalancer;
import org.openstack.atlas.datamodel.AlgorithmType;
import org.openstack.atlas.datamodel.ProtocolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.openstack.atlas.ctxs.api.validation.validator.CtxsLoadBalancerValidator;
import org.openstack.atlas.ctxs.api.mapper.dozer.converter.ExtensionObjectMapper;

import javax.xml.namespace.QName;
import java.util.Map;


//import org.openstack.atlas.api.v1.extensions.ctxs.LoadBalancer;
import org.openstack.atlas.ctxs.api.validation.validator.CertificateRefValidator;

import static org.openstack.atlas.api.validation.context.HttpRequestType.POST;

@Primary
@Component
@Scope("request")
public class CtxsLoadBalancerValidatorBuilder extends LoadBalancerValidatorBuilder {
    protected final Log LOG = LogFactory.getLog(CtxsLoadBalancerValidatorBuilder.class);
    private final int MIN_CERTIFICATES = 1;
    private final int MAX_CERTIFICATES = 10;
    
    @Autowired
    public CtxsLoadBalancerValidatorBuilder(AlgorithmType algorithmType,
                                           ProtocolType protocolType,
                                           NodeValidatorBuilder nodeValidatorBuilder,
                                           VirtualIpValidatorBuilder virtualIpValidatorBuilder,
                                           HealthMonitorValidatorBuilder healthMonitorValidatorBuilder,
                                           ConnectionThrottleValidatorBuilder connectionThrottleValidatorBuilder,
                                           SessionPersistenceValidatorBuilder sessionPersistenceValidatorBuilder,
                                           CertificateRefValidatorBuilder certRefValidatorBuilder) {
                                           
        super(algorithmType, protocolType, nodeValidatorBuilder, virtualIpValidatorBuilder, healthMonitorValidatorBuilder, connectionThrottleValidatorBuilder, sessionPersistenceValidatorBuilder);

        // POST EXPECTATIONS
        result(validationTarget().getAnies()).if_().exist().then().must().delegateTo(new CtxsLoadBalancerValidator().getValidator(), POST).forContext(POST);
//        Verifier<LoadBalancer> verifier = new Verifier<LoadBalancer>() {
//            @Override
//            public VerifierResult verify(LoadBalancer loadbalancer) {
//                Map<QName, String> otherAttributes = loadbalancer.getOtherAttributes();
//                final String protocol = loadbalancer.getProtocol();
//                LOG.debug("Inside sslMode verification");
//                String sslModeValue = ExtensionObjectMapper.getOtherAttribute(otherAttributes, "sslMode");
//                if(sslModeValue != null)
//                {
//                    System.out.println("Protocol during verification " + protocol);
//                    if(protocol != null)
//                        return new VerifierResult(protocol.equals("HTTPS"));
//                }
//
//                return new VerifierResult(true);
//            }
//        };
//        result(validationTarget()).if_().not().adhereTo(new MustBeEmptyOrNull()).then().must().adhereTo(verifier).forContext(POST).withMessage("'sslMode' attribute can be set only if protocol is 'HTTPS'!");


        Verifier<Map<QName, String>> verifier = new Verifier<Map<QName, String>>() {
            @Override
            public VerifierResult verify(Map<QName, String> otherAttributes) {
                LOG.debug("Inside sslMode verification");
                String sslModeValue = ExtensionObjectMapper.getOtherAttribute(otherAttributes, "sslMode");
                if(sslModeValue != null)
                {
                    System.out.println("target class is " + validationTarget().getClass());
                    System.out.println("Protocol during verification " + validationTarget().getProtocol());
                    if(validationTarget().getProtocol() != null)
                        return new VerifierResult(validationTarget().getProtocol().equals("HTTPS"));
                }

                return new VerifierResult(true);
            }
        };

        result(validationTarget().getOtherAttributes()).if_().not().adhereTo(new MustBeEmptyOrNull()).then().must().adhereTo(verifier).forContext(POST).withMessage("'sslMode' attribute can be set only if protocol is 'HTTPS'!");
        Verifier<Map<QName, String>> verifier1 = new Verifier<Map<QName, String>>() {
            @Override
            public VerifierResult verify(Map<QName, String> otherAttributes) {
                LOG.debug("Inside sslMode verification");
                String sslModeValue = ExtensionObjectMapper.getOtherAttribute(otherAttributes, "sslMode");
                if(sslModeValue != null)
                    return new VerifierResult(sslModeValue.equals("PASSTHROUGH") || sslModeValue.equals("OFFLOAD") || sslModeValue.equals("OFFLOAD_AND_REENCRYPT"));
                return new VerifierResult(true);
            }
        };
        result(validationTarget().getOtherAttributes()).if_().not().adhereTo(new MustBeEmptyOrNull()).then().must().adhereTo(verifier1).
                            forContext(POST).withMessage("'sslMode' attribute must equal PASSTHROUGH or OFFLOAD or OFFLOAD_AND_REENCRYPT!");

/*
        Object validationTarget = validationTarget();
        LOG.debug("inside constructor for CtxsLoadBalancerValidatorBuilder");
        LOG.debug(String.format("validation target object class is: %s", validationTarget.getClass().getName()));
        LOG.debug(String.format("validation target object name is: %s", ((org.openstack.atlas.core.api.v1.LoadBalancer) validationTarget).getName()));
                
        if (validationTarget instanceof LoadBalancer) {
            LoadBalancer ctxsLoadBalancer = (LoadBalancer) validationTarget;
        
            result(ctxsLoadBalancer.getCertificates()).must().exist().forContext(POST).withMessage("Must provide at least 1 certificate for the load balancer.");
            result(ctxsLoadBalancer.getCertificates()).must().haveSizeOfAtLeast(MIN_CERTIFICATES).forContext(POST).withMessage(String.format("Must have at least %d certificate(s).", MIN_CERTIFICATES));
            result(ctxsLoadBalancer.getCertificates()).must().haveSizeOfAtMost(MAX_CERTIFICATES).forContext(POST).withMessage(String.format("Must not provide more than %d certificates per load balancer.", MAX_CERTIFICATES));
            result(ctxsLoadBalancer.getCertificates()).if_().exist().then().must().delegateTo(new CertificateRefValidator(certRefValidatorBuilder).getValidator(), POST).forContext(POST);
        }
        */
        return;
    }
}
