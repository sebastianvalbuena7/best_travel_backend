package com.sebastian.bestTravel.infrastructure.helpers;

import com.sebastian.bestTravel.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {
    public void isInBlackListCustomer(String customerId) {
        if(customerId.equals("GOTW771012HMRGR087")) {
            throw new ForbiddenCustomerException();
        }
    }
}