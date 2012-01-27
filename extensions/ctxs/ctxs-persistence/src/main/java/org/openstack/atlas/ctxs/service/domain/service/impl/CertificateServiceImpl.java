package org.openstack.atlas.ctxs.service.domain.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.atlas.ctxs.service.domain.entity.Certificate;
import org.openstack.atlas.ctxs.service.domain.repository.CertificateRepository;
import org.openstack.atlas.ctxs.service.domain.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.openstack.atlas.service.domain.exception.*;
import org.springframework.stereotype.Service;


/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/11/12
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CertificateServiceImpl implements CertificateService{
    public static Log LOG = LogFactory.getLog(CertificateServiceImpl.class.getName());
    @Autowired
    protected CertificateRepository certificateRepository;

    @Override
    public Certificate createCertificate(Certificate certificate, org.openstack.atlas.api.v1.extensions.ctxs.Certificate apiCert) throws EntityNotFoundException, ImmutableEntityException, UnprocessableEntityException, BadRequestException {
        certificate.setStatus("BUILD"); // Status is set to QUEUED in loadbalancer in Service before creation. Not sure where the status build is set.
        Certificate dbcert= certificateRepository.create(certificate);
        return dbcert;
    }

    @Override
    public void preDelete(Integer accountId, Integer id) throws EntityNotFoundException, ImmutableEntityException, UnprocessableEntityException, BadRequestException {
        Certificate dbcert = null;
        try{
            dbcert = certificateRepository.getByIdAndAccountId(id, accountId);
        } catch (EntityNotFoundException e) {
            throw new BadRequestException(String.format("No Certificate with id '%d' found.", dbcert.getId()));
        }

        if (!dbcert.getStatus().equals("ACTIVE")) {
            LOG.warn(String.format("Certificate '%d' has a status of '%s' and is considered immutable.", id, dbcert.getStatus()));
            throw new BadRequestException(String.format("Certificate '%d' has a status of '%s' and is considered immutable.", id, dbcert.getStatus()));
        }
        certificateRepository.changeStatus(accountId, id, "PENDING_DELETE");
    }

}
