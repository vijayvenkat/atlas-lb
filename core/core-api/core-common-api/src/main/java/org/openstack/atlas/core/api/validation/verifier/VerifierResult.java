package org.openstack.atlas.core.api.validation.verifier;

import org.openstack.atlas.core.api.validation.expectation.ValidationResult;

import java.util.LinkedList;
import java.util.List;

public class VerifierResult {
    private final boolean passed;
    private final List<ValidationResult> resultList;

    public VerifierResult(boolean passed) {
        this.passed = passed;
        this.resultList = new LinkedList<ValidationResult>();
    }


    // :(
    public VerifierResult(boolean passed, List<ValidationResult> resultList) {
        this(passed);
        this.resultList.addAll(resultList);
    }

    public boolean passed() {
        return passed;
    }

    public List<ValidationResult> getResultList() {
        return resultList;
    }
}
