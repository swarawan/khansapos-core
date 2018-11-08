package com.khansapos.core.api.response;

public class MetaResponse {

    public int code;
    public String message;
    public String debugInfo;

    public MetaResponse(String message, int code) {
        this.message = message;
        this.code = code;
        this.debugInfo = "";
    }

    public MetaResponse(int code, String message, String debugInfo) {
        this.code = code;
        this.message = message;
        this.debugInfo = debugInfo;
    }
}
