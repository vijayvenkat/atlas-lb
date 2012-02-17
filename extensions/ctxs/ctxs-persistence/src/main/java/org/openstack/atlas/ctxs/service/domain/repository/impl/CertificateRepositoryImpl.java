package org.openstack.atlas.ctxs.service.domain.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.atlas.common.crypto.CryptoUtil;
import org.openstack.atlas.ctxs.service.domain.entity.Certificate;
import org.openstack.atlas.ctxs.service.domain.entity.CertificateRef;
import org.openstack.atlas.ctxs.service.domain.repository.CertificateRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import org.openstack.atlas.service.domain.exception.*;

import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/11/12
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class CertificateRepositoryImpl implements CertificateRepository {
    final Log LOG = LogFactory.getLog(CertificateRepositoryImpl.class);
    @PersistenceContext(unitName = "loadbalancing")
    private EntityManager entityManager;

    @Override
    public Certificate getById(Integer id) throws EntityNotFoundException {
        Certificate certificate = entityManager.find(Certificate.class, id);
        if (certificate == null) {
            throw new EntityNotFoundException(String.format("Certificate with id %d not found", id));
        }
        return certificate;
    }

    @Override
    public List<Certificate> getByAccountId(Integer accountId) {
        return entityManager.createQuery("SELECT cert FROM Certificate cert WHERE cert.accountId = :accountId")
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public Certificate getByIdAndAccountId(Integer id, Integer accountId, Boolean... fetchLinkCertificates) throws EntityNotFoundException {

        Certificate certificate = getById(id);
        if (!certificate.getAccountId().equals(accountId)) {
            throw new EntityNotFoundException("Certificate not found");
        }

        if(fetchLinkCertificates != null && fetchLinkCertificates.length > 0 && fetchLinkCertificates[0] && certificate.getLcertificates() != null)
            // Fetching Link Certificates to be used for create in adapter
            LOG.debug("Number of link certificates passed : " + certificate.getLcertificates().size());

        return certificate;
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate = entityManager.merge(certificate);
        entityManager.flush();
        return certificate;
    }

    @Override
    public Certificate update(Certificate certificate) {
        certificate = entityManager.merge(certificate);
//        entityManager.flush();
        return certificate;
    }

    @Override
    public void delete(Integer accountId, Integer id) throws EntityNotFoundException, UnprocessableEntityException, ImmutableEntityException {
        Certificate certificate = getByIdAndAccountId(id, accountId);
        entityManager.remove(certificate);
        entityManager.flush();
    }

    @Override
    public void changeStatus(Integer accountId, Integer id, String newStatus) throws EntityNotFoundException, UnprocessableEntityException, ImmutableEntityException {
        Certificate certificate = getByIdAndAccountId(id, accountId);
        certificate.setStatus(newStatus);
        update(certificate);
    }

    public boolean isUsed(Integer id)
    {
        List list = entityManager.createQuery("SELECT certref FROM CertificateRef certref WHERE certref.id.idRef = :certid")
                .setParameter("certid", id).getResultList();

        if(list.size() > 0)
        {
            for(Object item : list)
            {
                CertificateRef ref = (CertificateRef) item;
                if(ref.getLoadbalancer().getStatus().equals("ACTIVE"))
                    return true;
            }
        }
        return false;

    }

    public boolean deleteLoadBalancerCertificates(Integer id)
    {
        List list = entityManager.createQuery("SELECT certref FROM CertificateRef certref WHERE certref.id.loadBalancerId = :lbId")
                .setParameter("lbId", id).getResultList();
        if(list.size() > 0)
        {
            System.out.println(String.format(" ref values size %d", list.size()));
            for(Object item : list)
            {
                CertificateRef ref = (CertificateRef) item;
                System.out.println(String.format(" ref values : idref %d lbid %d", ref.getIdRef(), ref.getLoadbalancer().getId()));
//                entityManager.remove(item);
            }
        }
        entityManager.flush();
        return true;

    }
}
