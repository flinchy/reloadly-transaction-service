package com.chisom.transactionservice.controller;

import com.chisom.transactionservice.dto.response.ApiResponse;
import com.chisom.transactionservice.dto.response.CreateTransactionResponse;
import com.chisom.transactionservice.dto.request.TransactionRequest;
import com.chisom.transactionservice.dto.response.GetUserTransactionResponse;
import com.chisom.transactionservice.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Chisom.Iwowo
 */
@Service
public class TransactionFacade implements TransactionController {

    private final TransactionService transactionService;

    public TransactionFacade(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * create a new transaction.
     *
     * @param transactionRequest transaction request.
     * @return Object
     */
    @Override
    public CreateTransactionResponse createNewTransaction(
            @Valid TransactionRequest transactionRequest
    ) {
        transactionService.createNewTransaction(transactionRequest);
        return new CreateTransactionResponse(
                ZonedDateTime.now(), "transaction was successful", true);
    }

    /**
     * get a users transaction
     *
     * @param userId user id
     * @return Object
     */
    @Override
    public ApiResponse<List<GetUserTransactionResponse>> getUsersTransactions(
            Long userId, String page
    ) {
        List<GetUserTransactionResponse> transactionResponses =
                transactionService.getUsersTransactions(userId, page);

        return new ApiResponse<>(transactionResponses,
                "retrieved transaction successfully", true);
    }
}
