package org.openstack.atlas.ctxs.service.domain.service;

import java.util.List;
import java.util.Set;

import org.openstack.atlas.ctxs.service.domain.entity.Certificate;
import org.openstack.atlas.service.domain.exception.BadRequestException;
import org.openstack.atlas.service.domain.exception.EntityNotFoundException;
import org.openstack.atlas.service.domain.exception.ImmutableEntityException;
import org.openstack.atlas.service.domain.exception.UnprocessableEntityException;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/11/12
 * Time: 3:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CertificateService {

    Certificate createCertificate(Certificate certificate, org.openstack.atlas.api.v1.extensions.ctxs.Certificate apiCert) throws EntityNotFoundException, ImmutableEntityException, UnprocessableEntityException, BadRequestException;
    void preDelete(Integer accountId, Integer id) throws EntityNotFoundException, ImmutableEntityException, UnprocessableEntityException, BadRequestException;
    Certificate getCertificate(Integer accountId, Integer id) throws EntityNotFoundException, UnprocessableEntityException;
}
