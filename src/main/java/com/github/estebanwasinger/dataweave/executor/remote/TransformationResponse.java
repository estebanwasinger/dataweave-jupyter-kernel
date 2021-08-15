package com.github.estebanwasinger.dataweave.executor.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class TransformationResponse {
    boolean success;
    Map<String, String> result;
    List<Object> logs;
    @SerializedName("error")
    TransformationError error;

    public TransformationResponse(boolean success, Map<String, String> result, List<Object> logs, TransformationError error) {
        this.success = success;
        this.result = result;
        this.logs = logs;
        this.error = error;
    }

    public TransformationResponse() {
    }

    public TransformationError getError() {
        return error;
    }

    public void setError(TransformationError error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }

    public List<Object> getLogs() {
        return logs;
    }

    public void setLogs(List<Object> logs) {
        this.logs = logs;
    }
}
