package com.github.estebanwasinger.dataweave.executor.remote;

public class TransformationError {
    String kind;
    String message;

    public TransformationError(String kind, String message) {
        this.kind = kind;
        this.message = message;
    }

    public String getKind() {
        return kind;
    }

    public String getMessage() {
        return message;
    }
}
