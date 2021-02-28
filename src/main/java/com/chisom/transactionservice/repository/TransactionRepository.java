package com.chisom.transactionservice.repository;

import com.chisom.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * get users transaction.
     *
     * @param userId user id
     * @param limit  limit
     * @param offset offset
     * @return List<Transaction>
     */
    @Query(value = "select * from transaction where user_id =:userId ORDER BY created_at " +
            "DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Transaction> findAllByUserId(Long userId, int limit, int offset);

    /**
     * check if id exist.
     *
     * @param userId user id
     * @return int
     */
    int countByUserId(Long userId);
}
