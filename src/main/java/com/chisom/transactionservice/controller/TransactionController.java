package com.chisom.transactionservice.controller;

import com.chisom.transactionservice.dto.response.ApiResponse;
import com.chisom.transactionservice.dto.response.CreateTransactionResponse;
import com.chisom.transactionservice.dto.request.TransactionRequest;
import com.chisom.transactionservice.dto.response.GetUserTransactionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Transaction Resource"})
@SwaggerDefinition(tags = {
        @Tag(name = "Transaction Resource", description = "REST API for Transaction.")
})
@RestController
@RequestMapping("/transaction")
public interface TransactionController {

    /**
     * create a new transaction.
     *
     * @param transactionRequest transaction request.
     * @return Object
     */
    @ApiOperation(
            value = "${api.transaction.create.description}")
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
    @ApiOperation(
            value = "${api.transaction.get-user}")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    ApiResponse<List<GetUserTransactionResponse>> getUsersTransactions(
            @PathVariable Long userId, @RequestParam(required = false, defaultValue = "0") String page);

}
