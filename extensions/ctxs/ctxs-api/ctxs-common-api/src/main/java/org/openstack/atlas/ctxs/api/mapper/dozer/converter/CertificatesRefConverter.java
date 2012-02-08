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

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@XmlRootElement(name="certificates", namespace = "http://docs.openstack.org/atlas/api/v1.1/extensions/ctxs")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "certificatesRef", propOrder = {
    "certificates"
})
class CertificatesRefDuplicate  extends CertificatesRef
{
}

public class CertificatesRefConverter implements CustomConverter {
    private static Log LOG = LogFactory.getLog(CertificatesRefConverter.class.getName());

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        LOG.debug("CertificatesRefConverter 1");
        if (sourceFieldValue == null) {
            return null;
        }

        LOG.debug("CertificatesRefConverter 2");

        if (destinationClass == Set.class) {     // datamodel to domain
            LOG.debug("CertificatesRefConverter 3 " + ((List<Object>) sourceFieldValue).size());
            Set<org.openstack.atlas.ctxs.service.domain.entity.CertificateRef> certificateRefs = new HashSet<org.openstack.atlas.ctxs.service.domain.entity.CertificateRef>();

            CertificatesRefDuplicate _certificatesRef = ExtensionObjectMapper.getAnyElement((List<Object>) sourceFieldValue, CertificatesRefDuplicate.class);

            LOG.debug("CertificatesRefConverter 4");
            if (_certificatesRef == null) return null;

            LOG.debug("CertificatesRefConverter  5 " + _certificatesRef.getCertificates().size());
            for (CertificateRef certificateRef : _certificatesRef.getCertificates()) {
                LOG.debug("CertificatesRefConverter  6 ");
                org.openstack.atlas.ctxs.service.domain.entity.CertificateRef dbCertificateRef = new org.openstack.atlas.ctxs.service.domain.entity.CertificateRef();
                dbCertificateRef.setIdref(certificateRef.getIdref());
                certificateRefs.add(dbCertificateRef);
            }

            LOG.debug("CertificatesRefConverter  7 ");
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
