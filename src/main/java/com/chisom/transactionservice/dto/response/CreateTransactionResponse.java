package com.chisom.transactionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * @author Chisom.Iwowo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionResponse {

    /**
     * timestamp.
     */
    private ZonedDateTime timestamp;

    /**
     * message.
     */
    private String message;

    /**
     * status.
     */
    private boolean status;

}
