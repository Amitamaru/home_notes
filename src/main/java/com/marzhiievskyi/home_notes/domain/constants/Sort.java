package com.marzhiievskyi.home_notes.domain.constants;

public enum Sort {
    TIME_INSERT("note.time_insert DESC"),
    RANDOM("RAND()");

    private final String value;

    Sort(String value) {
        this.value =value;
    }

    public String getValue() {
        return value;
    }
}
