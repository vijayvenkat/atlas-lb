package org.openstack.atlas.ctxs.api.mapper.dozer.factory;

import org.dozer.BeanFactory;
import org.openstack.atlas.core.api.v1.LoadBalancer;

public class CtxsLoadBalancerFactory implements BeanFactory {

    @Override
    public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {
        if (sourceClass.equals(org.openstack.atlas.ctxs.service.domain.entity.CtxsLoadBalancer.class)
                || sourceClass.equals(org.openstack.atlas.service.domain.entity.LoadBalancer.class)) {
            LoadBalancer lb = new LoadBalancer();
            lb.setNodes(null);
            lb.setVirtualIps(null);
            return lb;
        }

        if (sourceClass.equals(LoadBalancer.class)) {
            org.openstack.atlas.ctxs.service.domain.entity.CtxsLoadBalancer lb = new org.openstack.atlas.ctxs.service.domain.entity.CtxsLoadBalancer();
            return lb;
        }

        return null;
    }
}
