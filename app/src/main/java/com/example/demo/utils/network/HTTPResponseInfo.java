package com.example.demo.utils.network;

public class HTTPResponseInfo {

    private int statusCode;

    private String statusDescription;

    private String statusText;

    private String type;

    public HTTPResponseInfo() {
        this.statusCode = 0;
        this.statusText = "-";
        this.statusDescription = "-";
        this.type = "-";
    }

    public int getStatusCode() {
        return statusCode;
    }

    void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusText() {
        return statusText;
    }

    void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }
}
