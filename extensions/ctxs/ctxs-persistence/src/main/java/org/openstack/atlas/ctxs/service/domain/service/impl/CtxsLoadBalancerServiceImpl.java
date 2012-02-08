package org.openstack.atlas.ctxs.service.domain.service.impl;

import org.openstack.atlas.ctxs.service.domain.entity.CertificateRef;
import org.openstack.atlas.ctxs.service.domain.entity.CtxsLoadBalancer;
import org.openstack.atlas.ctxs.service.domain.repository.CertificateRepository;
import org.openstack.atlas.service.domain.entity.LoadBalancer;
import org.openstack.atlas.service.domain.exception.BadRequestException;
import org.openstack.atlas.service.domain.exception.EntityNotFoundException;
import org.openstack.atlas.service.domain.exception.LimitReachedException;
import org.openstack.atlas.service.domain.service.impl.LoadBalancerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 2/8/12
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
@Primary
public class CtxsLoadBalancerServiceImpl extends LoadBalancerServiceImpl {

    @Autowired
    CertificateRepository certificateRepository;

    @Override
    protected void validateCreate(LoadBalancer loadBalancer) throws BadRequestException, EntityNotFoundException, LimitReachedException {
        super.validateCreate(loadBalancer);    //To change body of overridden methods use File | Settings | File Templates.

        System.out.println("Inside validateCreate");

        CtxsLoadBalancer ctxsLoadBalancer = ((CtxsLoadBalancer)loadBalancer);
        Set<CertificateRef> certificateRefs = ctxsLoadBalancer.getCertificates();
        if(certificateRefs != null)
        {
            System.out.println("Inside validateCreate 1");
            for(CertificateRef ref : certificateRefs)
            {
                System.out.println("Inside validateCreate 2");

                certificateRepository.getById(ref.getIdref()); //EntityNotFoundException is thrown if id specified in idRef's is not found in certifiate repository
            }
        }
    }
}
