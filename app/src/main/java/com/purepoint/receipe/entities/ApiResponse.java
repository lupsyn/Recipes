package com.purepoint.receipe.entities;

import java.util.List;

public class ApiResponse {
    private List<Receipe> receipes;
    private Throwable error;

    public ApiResponse(List<Receipe> receipes) {
        this.receipes = receipes;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.receipes = null;
    }

    public List<Receipe> getReceipes() {
        return receipes;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
