package org.openstack.atlas.ctxs.service.domain.pojo;

import org.openstack.atlas.service.domain.pojo.MessageDataContainer;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: vijayve
 * Date: 1/27/12
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CtxsMessageDataContainer extends MessageDataContainer{

    HashMap<String, Object> hashData;

    public HashMap<String, Object> getHashData() {
        return hashData;
    }

    public void setHashData(HashMap<String, Object> hashData) {
        this.hashData = hashData;
    }

}
