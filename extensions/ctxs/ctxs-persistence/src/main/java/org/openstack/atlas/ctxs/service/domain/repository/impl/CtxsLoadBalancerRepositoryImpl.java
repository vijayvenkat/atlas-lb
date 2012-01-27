package org.openstack.atlas.ctxs.service.domain.repository.impl;

import org.openstack.atlas.ctxs.service.domain.entity.CertificateRef;
import org.openstack.atlas.ctxs.service.domain.entity.CtxsLoadBalancer;
import org.openstack.atlas.service.domain.entity.LoadBalancer;
import org.openstack.atlas.service.domain.repository.impl.LoadBalancerRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/27/12
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CtxsLoadBalancerRepositoryImpl extends LoadBalancerRepositoryImpl {

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
