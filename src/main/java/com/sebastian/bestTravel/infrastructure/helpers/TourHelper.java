package com.sebastian.bestTravel.infrastructure.helpers;

import com.sebastian.bestTravel.domain.entities.*;
import com.sebastian.bestTravel.domain.repositories.ReservationRepository;
import com.sebastian.bestTravel.domain.repositories.TicketRepository;
import com.sebastian.bestTravel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.sebastian.bestTravel.infrastructure.services.TicketService.charger_price_percentage;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer) {
        var response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(flyEntity -> {
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(flyEntity)
                    .customer(customer)
                    .purchaseDate(LocalDate.now())
                    .arrivalDate(BestTravelUtil.laterRandomLater())
                    .departureDate(BestTravelUtil.getRandomSoon())
                    .price(flyEntity.getPrice().add(flyEntity.getPrice().multiply(charger_price_percentage)))
                    .build();
            response.add(ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer) {
        var response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            var reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .customer(customer)
                    .hotel(hotel)
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)))
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .dateStart(LocalDate.now())
                    .dateTimeReservation(LocalDateTime.now())
                    .totalDays(totalDays)
                    .build();
            response.add(reservationRepository.save(reservationToPersist));
        });
        return response;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer) {
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.laterRandomLater())
                .departureDate(BestTravelUtil.getRandomSoon())
                .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                .build();
        return ticketRepository.save(ticketToPersist);
    }

    public ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays) {
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .hotel(hotel)
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)))
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .dateStart(LocalDate.now())
                .dateTimeReservation(LocalDateTime.now())
                .totalDays(totalDays)
                .build();
        return reservationRepository.save(reservationToPersist);
    }
}