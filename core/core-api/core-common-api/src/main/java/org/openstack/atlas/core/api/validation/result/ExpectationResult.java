package org.openstack.atlas.core.api.validation.result;

import org.openstack.atlas.core.api.validation.exception.ValidationException;

public class ExpectationResult {

    private ValidationException error;
    private String message, targetName;
    private boolean passed;

    public ExpectationResult(ValidationException error, String message, String targetName, boolean passed) {
        this.error = error;
        this.message = message;
        this.targetName = targetName;
        this.passed = passed;
    }

    public ValidationException getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }

    public String getMessage() {
        return message;
    }

    public String getValidationTargetName() {
        return targetName;
    }

    public boolean expectationPassedValidation() {
        return passed;
    }
}
