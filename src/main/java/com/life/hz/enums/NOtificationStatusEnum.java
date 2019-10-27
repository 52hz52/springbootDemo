package com.life.hz.enums;

public enum NOtificationStatusEnum {
    UNREAD(0),
    READ(1),
    ;

    private int status;

    public int getStatus() {
        return status;
    }

    NOtificationStatusEnum(int status) {
        this.status = status;
    }
}
