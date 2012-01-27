package org.openstack.atlas.ctxs.api.async;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.openstack.atlas.api.async.BaseListener;
import org.openstack.atlas.service.domain.pojo.MessageDataContainer;
import javax.jms.Message;
import java.util.HashMap;
import java.util.List;
import org.openstack.atlas.ctxs.service.domain.entity.Certificate;
import org.openstack.atlas.ctxs.service.domain.repository.CertificateRepository;
import org.openstack.atlas.ctxs.service.domain.service.CertificateService;
import org.springframework.stereotype.Component;
import org.openstack.atlas.ctxs.service.domain.pojo.CtxsMessageDataContainer;
import org.openstack.atlas.ctxs.api.integration.CtxsReverseProxyLoadBalancerService;

@Component
public class CreateCertificateListener  extends BaseListener {

    private final Log LOG = LogFactory.getLog(CreateCertificateListener.class);

    @Autowired
    private CertificateRepository certificateRepository;

    @Override
    public void doOnMessage(final Message message) throws Exception {

        LOG.debug("Entering " + getClass());
        LOG.debug(message);
        CtxsMessageDataContainer dataContainer = (CtxsMessageDataContainer) getDataContainerFromMessage(message);

        HashMap<String, Object> queueData = dataContainer.getHashData();

        List<org.openstack.atlas.api.v1.extensions.ctxs.Certificate> apiCerts = ( List<org.openstack.atlas.api.v1.extensions.ctxs.Certificate> ) queueData.get("API_CERTS");
        List<Certificate> dbcerts = ( List<Certificate> ) queueData.get("DOMAIN_CERTS");
        boolean berror = false;
        try
        {
            LOG.info(String.format("Creating '%d' certificates.", dbcerts.size()));
            ((CtxsReverseProxyLoadBalancerService)reverseProxyLoadBalancerService).createCertificates(dbcerts, apiCerts);
            LOG.info(String.format("Successfully created '%d' certificates.", dbcerts.size()));

        } catch (Exception e) {
            berror = true;
            String alertDescription = String.format("An error occurred while creating certificates via adapter.");
            LOG.error(alertDescription, e);
        }

        for (Certificate dbcert: dbcerts)
        {
            String status = (berror)? "ERROR": "ACTIVE";
            if(!berror)
            {
                try {
                    Certificate temp_dbcert = ((CtxsReverseProxyLoadBalancerService)reverseProxyLoadBalancerService).getCertificate(dbcert);
                    temp_dbcert.setAccountId(dbcert.getAccountId());
                    dbcert = temp_dbcert;
                }
                catch(Exception ex) {
                    status = "ERROR";
                }
            }
            dbcert.setStatus(status);
            dbcert = certificateRepository.update(dbcert);
        }
    }
}
