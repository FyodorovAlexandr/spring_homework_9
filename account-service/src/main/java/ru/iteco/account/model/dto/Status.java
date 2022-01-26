package ru.iteco.account.model.dto;

public enum Status {

    PROCESSING("processing"),
    SUCCESSFUL("successful"),
    DECLINED("declined");

    public final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
