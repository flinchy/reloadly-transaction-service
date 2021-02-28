package com.chisom.transactionservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class GetUserTransactionResponse {
    private BigDecimal accountBalance;
    private BigDecimal amount;
    private String narration;
}
