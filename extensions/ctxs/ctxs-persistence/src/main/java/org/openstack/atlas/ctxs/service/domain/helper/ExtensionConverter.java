package org.openstack.atlas.ctxs.service.domain.helper;

import org.openstack.atlas.ctxs.service.domain.entity.CertificateRef;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/27/12
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtensionConverter {

        public static org.openstack.atlas.api.v1.extensions.ctxs.CertificatesRef convertCertificatesRef(Set<CertificateRef> dbCertificateSet) {
        org.openstack.atlas.api.v1.extensions.ctxs.CertificatesRef dataModelCertificatesRef = new org.openstack.atlas.api.v1.extensions.ctxs.CertificatesRef();
        for (CertificateRef dbCertificateRef : dbCertificateSet) {
           org.openstack.atlas.api.v1.extensions.ctxs.CertificateRef certificateRef = new org.openstack.atlas.api.v1.extensions.ctxs.CertificateRef();
            certificateRef.setIdref(dbCertificateRef.getIdref());
            dataModelCertificatesRef.getCertificates().add(certificateRef);
        }
        return dataModelCertificatesRef;
    }
}
