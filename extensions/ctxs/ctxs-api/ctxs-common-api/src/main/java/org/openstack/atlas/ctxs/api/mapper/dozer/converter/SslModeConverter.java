package org.openstack.atlas.ctxs.api.mapper.dozer.converter;

import org.dozer.CustomConverter;
import org.openstack.atlas.service.domain.exception.NoMappableConstantException;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 2/7/12
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SslModeConverter implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if(destinationClass == Map.class) { // Converting from domain to datamodel / api
            Map<QName, String> otherAttributes = (Map<QName, String>)(existingDestinationFieldValue);
            if (otherAttributes == null) otherAttributes = new HashMap<QName, String>();

            otherAttributes.put((new QName("http://docs.openstack.org/atlas/api/v1.1/extensions/ctxs", "sslMode", "ctxs")), (String) sourceFieldValue);

            return otherAttributes;
        }

        if(destinationClass == String.class) { // Converting from datamodel / api to domain
            return ExtensionObjectMapper.<String>getOtherAttribute((Map<QName, String>) sourceFieldValue, "sslMode");
        }

        // TODO How do i validate only certain values are present in sslMode

        throw new NoMappableConstantException("Cannot map source type: " + sourceClass.getName());
    }

}
