package org.openstack.atlas.api.validation.verifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IsInstanceOf implements Verifier {
    private final Class classType;
    public static Log LOG = LogFactory.getLog(IsInstanceOf.class.getName());

    public IsInstanceOf(Class classType) {
        this.classType = classType;
    }

    @Override
    public VerifierResult verify(Object obj) {
        LOG.info("inside log + IsInstanceOf verify1 class is " + obj.getClass());
        LOG.info("inside log + IsInstanceOf verify2 class is " + classType);

        return new VerifierResult(obj.getClass().equals(classType));
    }
}
