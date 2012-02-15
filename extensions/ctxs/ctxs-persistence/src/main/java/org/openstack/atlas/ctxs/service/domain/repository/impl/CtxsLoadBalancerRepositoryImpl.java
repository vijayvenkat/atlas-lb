package org.openstack.atlas.ctxs.service.domain.repository.impl;

import org.openstack.atlas.ctxs.service.domain.entity.CertificateRef;
import org.openstack.atlas.ctxs.service.domain.entity.CtxsLoadBalancer;
import org.openstack.atlas.service.domain.entity.LoadBalancer;
import org.openstack.atlas.service.domain.entity.LoadBalancerJoinVip;
import org.openstack.atlas.service.domain.repository.impl.LoadBalancerRepositoryImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/27/12
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Primary
@Repository
@Transactional
public class CtxsLoadBalancerRepositoryImpl extends LoadBalancerRepositoryImpl {

    @Override
    public LoadBalancer create(LoadBalancer loadBalancer) {
        final Set<CertificateRef> certificateRefs = ((CtxsLoadBalancer)loadBalancer).getCertificates();
        ((CtxsLoadBalancer) loadBalancer).setCertificates(null);
        loadBalancer = super.create(loadBalancer);
        for(CertificateRef certificateRef: certificateRefs)
        {
            CertificateRef certificateRefWithLB = new CertificateRef(((CtxsLoadBalancer)loadBalancer), certificateRef.getIdRef());
            entityManager.merge(certificateRefWithLB);
        }
        return loadBalancer;
    }

    @Override
    protected void setLbIdOnChildObjects(final LoadBalancer loadBalancer) {
        super.setLbIdOnChildObjects(loadBalancer);
        if (loadBalancer instanceof CtxsLoadBalancer) {
            if (((CtxsLoadBalancer) loadBalancer).getCertificates() != null) {
                for (CertificateRef certificateRef : ((CtxsLoadBalancer) loadBalancer).getCertificates()) {
                    certificateRef.setLoadbalancer(((CtxsLoadBalancer) loadBalancer));
                }
            }
        }
    }

}
