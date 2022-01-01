package org.aakashtushar.response.auth;

import org.aakashtushar.response.SuccessfulResponse;

import java.util.HashMap;

public class UserCreatedResponse implements SuccessfulResponse {
    private final Boolean isSuccessfullyCreated;

    public UserCreatedResponse(Boolean isSuccessfullyCreated) {
        this.isSuccessfullyCreated = isSuccessfullyCreated;
    }

    @Override
    public Object getData() {
        var data = new HashMap<String, Boolean>();
        data.put("isSuccessfullyCreated", isSuccessfullyCreated);

        return data;
    }
}
