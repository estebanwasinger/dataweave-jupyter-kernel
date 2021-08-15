package com.github.estebanwasinger.dataweave.executor.remote;

import java.util.Map;

public class Input {
    String value;
    String kind;
    String encoding;
    String mimeType;
    Map<String, String> properties;

    public Input(String value, String kind, String encoding, String mimeType, Map<String, String> properties) {
        this.value = value;
        this.kind = kind;
        this.encoding = encoding;
        this.mimeType = mimeType;
        this.properties = properties;
    }

    public Input() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
