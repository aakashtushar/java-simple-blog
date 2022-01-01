package org.aakashtushar.response;

public interface SuccessfulResponse<T> extends Response<T> {
    @Override
    default String getStatus() {
        return "success";
    }

    @Override
    default String getMessage() {
        return "OK";
    }
}
