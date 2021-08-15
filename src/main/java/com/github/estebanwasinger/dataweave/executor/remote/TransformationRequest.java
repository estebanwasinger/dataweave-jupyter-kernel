package com.github.estebanwasinger.dataweave.executor.remote;

import java.util.Map;

public class TransformationRequest {
    String main;
    Map<String, Input> inputs;
    Map<String, String> fs;

    public TransformationRequest(String main, Map<String, Input> inputs, Map<String, String> fs) {
        this.main = main;
        this.inputs = inputs;
        this.fs = fs;
    }

    public TransformationRequest() {
    }

    public String getMain() {
        return main;
    }

    public Map<String, Input> getInputs() {
        return inputs;
    }

    public Map<String, String> getFs() {
        return fs;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setInputs(Map<String, Input> inputs) {
        this.inputs = inputs;
    }

    public void setFs(Map<String, String> fs) {
        this.fs = fs;
    }
}
