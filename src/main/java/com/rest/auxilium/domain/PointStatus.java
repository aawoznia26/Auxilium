package com.rest.auxilium.domain;

public enum PointStatus {
    ISSUED("wydane"),
    USED("wykorzystane"),
    EXPIRED("przeterminowane");


    public final String label;

    private PointStatus(String label) {
        this.label = label;
    }

}
