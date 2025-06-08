package org.example.visual_programming;

import java.util.ArrayList;
import java.util.List;

public record ValidationResult(
        List<String> errors,
        List<String> warnings,
        List<String> hints
) {
    public ValidationResult() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void addError(String message) {
        errors.add(message);
    }

    public void addWarning(String message) {
        warnings.add(message);
    }

    public void addHint(String message) {
        hints.add(message);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
