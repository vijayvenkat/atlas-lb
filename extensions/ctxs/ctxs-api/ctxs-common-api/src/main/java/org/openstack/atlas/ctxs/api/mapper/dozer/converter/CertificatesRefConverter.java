package org.openstack.atlas.ctxs.api.mapper.dozer.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.CustomConverter;
import org.openstack.atlas.api.v1.extensions.ctxs.*;
import org.openstack.atlas.ctxs.datamodel.XmlHelper;
import org.openstack.atlas.ctxs.service.domain.helper.ExtensionConverter;
import org.openstack.atlas.service.domain.exception.NoMappableConstantException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CertificatesRefConverter implements CustomConverter {
    private static Log LOG = LogFactory.getLog(CertificatesRefConverter.class.getName());

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        if (sourceFieldValue == null) {
            return null;
        }


        if (destinationClass == Set.class) {     // datamodel to domain
            Set<org.openstack.atlas.ctxs.service.domain.entity.CertificateRef> certificateRefs = new HashSet<org.openstack.atlas.ctxs.service.domain.entity.CertificateRef>();
            CertificatesRef _certificatesRef = ExtensionObjectMapper.getAnyElement((List<Object>) sourceFieldValue, CertificatesRef.class);

            if (_certificatesRef == null) return null;

            for (CertificateRef certificateRef : _certificatesRef.getCertificates()) {
                org.openstack.atlas.ctxs.service.domain.entity.CertificateRef dbCertificateRef = new org.openstack.atlas.ctxs.service.domain.entity.CertificateRef();
                dbCertificateRef.setIdref(certificateRef.getIdref());
                certificateRefs.add(dbCertificateRef);
            }

            return certificateRefs;
        }

        if (destinationClass == List.class && sourceFieldValue instanceof Set) { // domain to datamodel
            final Set<org.openstack.atlas.ctxs.service.domain.entity.CertificateRef> certificatesRefSet = (Set<org.openstack.atlas.ctxs.service.domain.entity.CertificateRef>) sourceFieldValue;
            List<Object> anies = (List<Object>) existingDestinationFieldValue;
            if (anies == null) anies = new ArrayList<Object>();

            try {
                CertificatesRef dataModelCertificatesRef = ExtensionConverter.convertCertificatesRef(certificatesRefSet);
                Element objectElement = XmlHelper.marshall(dataModelCertificatesRef);
                anies.add(objectElement);
            } catch (Exception e) {
                LOG.error("Error converting CertificateRef from domain to data model", e);
            }

            return anies;
        }

        throw new NoMappableConstantException("Cannot map source type: " + sourceClass.getName());
    }

}
