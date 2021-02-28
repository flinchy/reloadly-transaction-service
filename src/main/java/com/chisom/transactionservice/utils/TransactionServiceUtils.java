package com.chisom.transactionservice.utils;

import com.chisom.transactionservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public final class TransactionServiceUtils {

    /**
     * private constructor.
     * this class will never be initialized
     */
    private TransactionServiceUtils() {

    }

    /**
     * This method validates a number (String value)
     *
     * @param num, number string to be validated
     * @return boolean
     * @author Chisom.Iwowo
     */
    public static Boolean validateNum(String num) {
        try {
            return Long.parseLong(num) >= 0;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * validate page number
     *
     * @param page page
     * @return int
     */
    public static int validatePageNumber(String page) {
        if (validateNum(page).equals(false))
            throw new CustomException("invalid page number", HttpStatus.BAD_REQUEST);

        int parsePageNumber = Integer.parseInt(page);

        if (parsePageNumber > 0) parsePageNumber--;

        return parsePageNumber;
    }

}
