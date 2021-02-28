package com.chisom.transactionservice.service.impl;

import com.chisom.transactionservice.dto.request.TransactionRequest;
import com.chisom.transactionservice.dto.response.GetUserTransactionResponse;
import com.chisom.transactionservice.model.Transaction;
import com.chisom.transactionservice.repository.TransactionRepository;
import com.chisom.transactionservice.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.chisom.transactionservice.utils.TransactionServiceUtils.validatePageNumber;

/**
 * @author Chisom.Iwowo
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private final String transactionUrl;
    private final ModelMapper modelMapper;
    private static final int LIMIT = 10;

    @Autowired
    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            RestTemplate restTemplate, ModelMapper modelMapper,
            @Value("${transaction-server-health}") String transactionUrl
    ) {
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
        this.transactionUrl = transactionUrl;
        this.modelMapper = modelMapper;
    }

    /**
     * create a new transaction.
     *
     * @param transactionRequest transaction request
     */
    @Override
    public void createNewTransaction(
            final TransactionRequest transactionRequest
    ) {
        try {
            transactionRepository.save(modelMapper.map(transactionRequest,
                    Transaction.class));

        } catch (Exception exception) {
            log.error("could not process data...", exception);
        }
    }

    /**
     * get users transaction.
     *
     * @param userId user id
     * @param page   page number
     * @return List<GetUserTransactionResponse>
     */
    @Override
    public List<GetUserTransactionResponse> getUsersTransactions(
            Long userId, String page
    ) {
        try {
            //validate page number
            int parsePageNumber = validatePageNumber(page);
            //check if user exist
            int checkIfUserIdExist = transactionRepository.countByUserId(userId);

            if (checkIfUserIdExist > 0) {
                return transactionRepository.findAllByUserId(userId, LIMIT,
                        LIMIT * parsePageNumber).stream()
                        .map(this::processTransactionResponse)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }

        } catch (Exception exception) {
            log.error("could not process data...", exception);
            return Collections.emptyList();
        }
    }

    private GetUserTransactionResponse processTransactionResponse(
            Transaction transaction
    ) {

        return GetUserTransactionResponse.builder()
                .accountBalance(transaction.getAccountBalance())
                .amount(transaction.getAmount())
                .narration(transaction.getNarration())
                .build();
    }

    /**
     * ping url every 5min to keep alive
     */
    @Async
    @Scheduled(fixedRate = 300000)
    public void health() {
        try {
            CompletableFuture.runAsync(() ->
                    restTemplate.getForObject(transactionUrl, Object.class));
        } catch (Exception e) {
            log.error("caught an exception :::", e);
        }
    }
}
