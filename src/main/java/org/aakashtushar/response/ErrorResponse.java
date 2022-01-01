package org.aakashtushar.response;

public interface ErrorResponse extends Response {
    @Override
    default String getStatus() {
        return "error";
    }

    @Override
    default Object getData(){
        return null;
    }
}
