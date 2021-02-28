package com.chisom.transactionservice.service;

import com.chisom.transactionservice.dto.request.TransactionRequest;
import com.chisom.transactionservice.dto.response.GetUserTransactionResponse;

import java.util.List;

/**
 * @author Chisom.Iwowo
 */
public interface TransactionService {

    /**
     * create a new transaction.
     *
     * @param transactionRequest transaction request
     */
    void createNewTransaction(TransactionRequest transactionRequest);

    /**
     * get users transaction.
     *
     * @param userId user id
     * @param page   page number
     * @return List<GetUserTransactionResponse>
     */
    List<GetUserTransactionResponse> getUsersTransactions(Long userId, String page);
}
