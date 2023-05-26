package com.sebastian.bestTravel.infrastructure.helpers;

import com.sebastian.bestTravel.domain.repositories.jpa.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@AllArgsConstructor
public class CustomerHelper {
    private final CustomerRepository customerRepository;

    public void increase(String customerId, Class<?> type) {
        var customerToUpdate = customerRepository.findById(customerId).orElseThrow();
        switch (type.getSimpleName()) {
            case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() + 1);
            case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() + 1);
            case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() + 1);
        }
        customerRepository.save(customerToUpdate);
    }

    public void decrease(String customerId, Class<?> type) {
        var customerToUpdate = customerRepository.findById(customerId).orElseThrow();
        switch (type.getSimpleName()) {
            case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() - 1);
            case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() - 1);
            case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() - 1);
        }
        customerRepository.save(customerToUpdate);
    }
}