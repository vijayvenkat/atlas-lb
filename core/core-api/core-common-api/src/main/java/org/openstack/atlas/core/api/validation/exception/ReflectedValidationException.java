package org.openstack.atlas.core.api.validation.exception;

public class ReflectedValidationException extends ValidationException {

    public ReflectedValidationException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
