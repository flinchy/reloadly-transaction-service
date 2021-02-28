package com.chisom.transactionservice.controller;

import com.chisom.transactionservice.dto.response.ApiResponse;
import com.chisom.transactionservice.dto.response.CreateTransactionResponse;
import com.chisom.transactionservice.dto.request.TransactionRequest;
import com.chisom.transactionservice.dto.response.GetUserTransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public interface TransactionController {

    /**
     * create a new transaction.
     *
     * @param transactionRequest transaction request.
     * @return Object
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    CreateTransactionResponse createNewTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest);

    /**
     * get a users transactions
     *
     * @param userId user id
     * @return Object
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    ApiResponse<List<GetUserTransactionResponse>> getUsersTransactions(
            @PathVariable Long userId, @RequestParam(required = false, defaultValue = "0") String page);

}
