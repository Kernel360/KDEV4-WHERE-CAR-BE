package com.wherecar.collector.dto;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS("000", "Success"),
    INVALID_ACCESS_PATH("100", "Invalid access path"),
    WRONG_APPROACH("101", "This is the wrong approach"),
    CONTENT_TYPE_ERROR("102", "Content-Type error"),
    CONTENT_LENGTH_ERROR("103", "Content-Length error"),
    ACCEPT_ERROR("104", "ACCEPT error"),
    CACHE_CONTROL_ERROR("105", "Cache-Control error"),
    ACCEPT_ENCODING_ERROR("106", "Accept-Encoding error"),
    TIMESTAMP_ERROR("107", "Timestamp error"),
    TUID_ERROR("108", "TUID error"),
    MISSING_KEY_VERSION("109", "Missing Key-Version"),
    NOT_JSON_HEADER_TYPE("110", "Not json header type"),
    MISSING_TOKEN("200", "Missing Token"),
    INVALID_TOKEN("201", "Invalid Token"),
    UNUSABLE_TOKEN("202", "Unusable Token"),
    PROTOCOL_FORMAT_ERROR("300", "This is a protocol format error"),
    REQUIRED_PARAMETER_ERROR("301", "Required parameter error"),
    NO_SEARCH_RESULTS("302", "There are no search results"),
    DECRYPTION_ERROR("303", "Decryption error"),
    MISMATCHED_MDN("304", "Mismatched MDN"),
    DATA_PROCESSING_ERROR("400", "An error occurred while processing data"),
    UNDEFINED_ERROR("500", "An undefined error has occurred");

    private final String code;
    private final String message;

    private ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
