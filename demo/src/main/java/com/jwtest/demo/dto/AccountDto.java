package com.jwtest.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AccountDto {
    private String clientName;
    private Instant dateCreated;
    private int money;
    private String type;
    private String number;
}
