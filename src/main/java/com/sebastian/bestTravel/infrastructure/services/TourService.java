package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.request.TourRequest;
import com.sebastian.bestTravel.api.models.response.TourResponse;
import com.sebastian.bestTravel.domain.entities.FlyEntity;
import com.sebastian.bestTravel.domain.entities.HotelEntity;
import com.sebastian.bestTravel.domain.entities.TourEntity;
import com.sebastian.bestTravel.domain.repositories.CustomerRepository;
import com.sebastian.bestTravel.domain.repositories.FlyRepository;
import com.sebastian.bestTravel.domain.repositories.HotelRepository;
import com.sebastian.bestTravel.domain.repositories.TourRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITourService;
import com.sebastian.bestTravel.infrastructure.helpers.TourHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {
    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;

    @Override
    public TourResponse create(TourRequest request) {
        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly -> flights.add(flyRepository.findById(fly.getId()).orElseThrow()));
        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel -> hotels.put(hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays()));
        var tourToSave = TourEntity.builder()
                .tickets(tourHelper.createTickets(flights, customer))
                .reservations(tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();
        var tourSaved = tourRepository.save(tourToSave);
        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream().map(reservationEntity -> reservationEntity.getId()).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(ticketEntity -> ticketEntity.getId()).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long aLong) {
        var tourFromDB = tourRepository.findById(aLong).orElseThrow();
        return TourResponse.builder()
                .reservationIds(tourFromDB.getReservations().stream().map(reservationEntity -> reservationEntity.getId()).collect(Collectors.toSet()))
                .ticketIds(tourFromDB.getTickets().stream().map(ticketEntity -> ticketEntity.getId()).collect(Collectors.toSet()))
                .id(tourFromDB.getId())
                .build();
    }

    @Override
    public void delete(Long aLong) {
        var tourToDelete = tourRepository.findById(aLong).orElseThrow();
        tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long reservationId, Long tourId) {
        return null;
    }
}