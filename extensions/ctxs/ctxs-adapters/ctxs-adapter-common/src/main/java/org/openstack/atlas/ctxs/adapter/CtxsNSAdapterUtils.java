package org.openstack.atlas.ctxs.adapter;

import java.util.*;
import java.io.*;
import java.net.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openstack.atlas.adapter.exception.*;
import org.openstack.atlas.service.domain.entity.*;
import org.openstack.atlas.adapter.LoadBalancerEndpointConfiguration;
import org.openstack.atlas.ctxs.service.domain.entity.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.openstack.atlas.adapter.netscaler.NSAdapterUtils;

@Component
@Primary
public class CtxsNSAdapterUtils extends NSAdapterUtils
{
    public Log LOG = LogFactory.getLog(CtxsNSAdapterUtils.class.getName());

    public void populateNSLoadBalancer(LoadBalancer lb, com.citrix.cloud.netscaler.atlas.docs.loadbalancers.api.v1.LoadBalancer nsLB)
           throws BadRequestException
    {
        super.populateNSLoadBalancer(lb, nsLB);
        populateExtensionNSLoadBalancer((CtxsLoadBalancer)lb, nsLB);

    }

    public void populateExtensionNSLoadBalancer(CtxsLoadBalancer lb, com.citrix.cloud.netscaler.atlas.docs.loadbalancers.api.v1.LoadBalancer nsLB)
           throws BadRequestException
    {
        Set<CertificateRef> certificateRefs = lb.getCertificates();
        if (certificateRefs != null && certificateRefs.size() > 0)
        {
            populateNSCertificateRefs(certificateRefs, nsLB.getCertificates());
        } else {
            nsLB.setCertificates(null);
        }
    }

    public void populateNSCertificateRefs(Set<CertificateRef> certificateRefs, List<com.citrix.cloud.netscaler.atlas.docs.loadbalancers.api.v1.CertificateRef> nsCertificateRefs)
           throws BadRequestException
    {
    	if ((certificateRefs != null) && (certificateRefs.size() > 0))
        {
            LOG.debug(String.format("This loadBalancer has got %d certificateRefs", certificateRefs.size()));
			boolean forUpdate = false;
            for (CertificateRef certificateRef : certificateRefs)
    	    {
                com.citrix.cloud.netscaler.atlas.docs.loadbalancers.api.v1.CertificateRef nsCertificateRef = new com.citrix.cloud.netscaler.atlas.docs.loadbalancers.api.v1.CertificateRef();
                nsCertificateRef.setIdref(certificateRef.getIdref());
               nsCertificateRefs.add(nsCertificateRef);
            }
    	}
    }
}
