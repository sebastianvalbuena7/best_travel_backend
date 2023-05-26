package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.request.TourRequest;
import com.sebastian.bestTravel.api.models.response.TourResponse;
import com.sebastian.bestTravel.domain.entities.jpa.FlyEntity;
import com.sebastian.bestTravel.domain.entities.jpa.HotelEntity;
import com.sebastian.bestTravel.domain.entities.jpa.TourEntity;
import com.sebastian.bestTravel.domain.repositories.jpa.CustomerRepository;
import com.sebastian.bestTravel.domain.repositories.jpa.FlyRepository;
import com.sebastian.bestTravel.domain.repositories.jpa.HotelRepository;
import com.sebastian.bestTravel.domain.repositories.jpa.TourRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITourService;
import com.sebastian.bestTravel.infrastructure.helpers.BlackListHelper;
import com.sebastian.bestTravel.infrastructure.helpers.CustomerHelper;
import com.sebastian.bestTravel.infrastructure.helpers.EmailHelper;
import com.sebastian.bestTravel.infrastructure.helpers.TourHelper;
import com.sebastian.bestTravel.util.enums.Tables;
import com.sebastian.bestTravel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
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
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;

    @Override
    public TourResponse create(TourRequest request) {
        blackListHelper.isInBlackListCustomer(request.getCustomerId());
        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly -> flights.add(flyRepository.findById(fly.getId()).orElseThrow(() -> new IdNotFoundException(Tables.fly.name()))));
        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel -> hotels.put(hotelRepository.findById(hotel.getId()).orElseThrow(() -> new IdNotFoundException(Tables.hotel.name())), hotel.getTotalDays()));
        var tourToSave = TourEntity.builder()
                .tickets(tourHelper.createTickets(flights, customer))
                .reservations(tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();
        var tourSaved = tourRepository.save(tourToSave);
        customerHelper.increase(customer.getDni(), TourService.class);
        if(Objects.nonNull(request.getEmail())) emailHelper.sendMail(request.getEmail(), customer.getFullName(), Tables.tour.name());
        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream().map(reservationEntity -> reservationEntity.getId()).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(ticketEntity -> ticketEntity.getId()).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long aLong) {
        var tourFromDB = tourRepository.findById(aLong).orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        return TourResponse.builder()
                .reservationIds(tourFromDB.getReservations().stream().map(reservationEntity -> reservationEntity.getId()).collect(Collectors.toSet()))
                .ticketIds(tourFromDB.getTickets().stream().map(ticketEntity -> ticketEntity.getId()).collect(Collectors.toSet()))
                .id(tourFromDB.getId())
                .build();
    }

    @Override
    public void delete(Long aLong) {
        var tourToDelete = tourRepository.findById(aLong).orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {
        var tourUpdate = tourRepository.findById(tourId).orElseThrow();
        tourUpdate.removeTicket(ticketId);
        tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        var tourUpdate = tourRepository.findById(tourId).orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        var fly = flyRepository.findById(flyId).orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
        var ticket = tourHelper.createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {
        var tourUpdate = tourRepository.findById(tourId).orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        tourUpdate.removeReservation(reservationId);
        tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long hotelId, Long tourId, Integer totalDays) {
        var tourUpdate = tourRepository.findById(tourId).orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        var hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        var reservation = tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
        tourUpdate.addReservation(reservation);
        tourRepository.save(tourUpdate);
        return reservation.getId();
    }
}