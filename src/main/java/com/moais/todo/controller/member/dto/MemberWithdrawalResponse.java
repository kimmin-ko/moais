package com.moais.todo.controller.member.dto;

public class MemberWithdrawalResponse {

    private final boolean isWithdrawal;

    public MemberWithdrawalResponse(boolean result) {
        this.isWithdrawal = result;
    }

    public boolean isWithdrawal() {
        return this.isWithdrawal;
    }
}
