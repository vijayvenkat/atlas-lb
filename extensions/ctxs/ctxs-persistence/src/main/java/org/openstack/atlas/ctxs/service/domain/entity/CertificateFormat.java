package org.openstack.atlas.ctxs.service.domain.entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/11/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public enum CertificateFormat implements Serializable{
    PEM, DER;
    private final static long serialVersionUID = 999999919L;
}
