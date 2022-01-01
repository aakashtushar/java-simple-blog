package org.aakashtushar.response;

public interface Response<T> {
    String getStatus();
    String getMessage();
    T getData();
}
