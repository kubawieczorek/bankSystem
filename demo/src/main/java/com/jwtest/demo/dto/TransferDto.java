package com.jwtest.demo.dto;

import lombok.Data;

@Data
public class TransferDto {
    private int money;
    private String targetAccountNumber;
}
