package com.github.estebanwasinger.dataweave.executor.remote;

import java.util.List;
import java.util.Map;

public class TransformationResponse {
    boolean success;
    Map<String, String> result;
    List<Object> logs;

    public TransformationResponse(boolean success, Map<String, String> result, List<Object> logs) {
        this.success = success;
        this.result = result;
        this.logs = logs;
    }

    public TransformationResponse() {
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
