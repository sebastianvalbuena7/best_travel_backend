package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.request.ReservationRequest;
import com.sebastian.bestTravel.api.models.response.HotelResponse;
import com.sebastian.bestTravel.api.models.response.ReservationResponse;
import com.sebastian.bestTravel.domain.entities.ReservationEntity;
import com.sebastian.bestTravel.domain.repositories.CustomerRepository;
import com.sebastian.bestTravel.domain.repositories.HotelRepository;
import com.sebastian.bestTravel.domain.repositories.ReservationRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.IReservationService;
import com.sebastian.bestTravel.infrastructure.helpers.CustomerHelper;
import com.sebastian.bestTravel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;

    @Override
    public BigDecimal findPrice(UUID uuid) {
        var reservation = reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException("reservation"));
        return reservation.getPrice().add(reservation.getPrice().multiply(charger_price_percentage));
    }

    @Override
    public ReservationResponse create(ReservationRequest request) {
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException("hotel"));
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow(() -> new IdNotFoundException("customer"));
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .hotel(hotel)
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)))
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .dateStart(LocalDate.now())
                .dateTimeReservation(LocalDateTime.now())
                .totalDays(request.getTotalDays())
                .build();
        var reservationPersistent = reservationRepository.save(reservationToPersist);
        customerHelper.increase(customer.getDni(), ReservationService.class);
        return entityToResponse(reservationPersistent);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservation = reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException("reservation"));
        return entityToResponse(reservation);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException("hotel"));
        var reservationToUpdate = reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException("reservation"));
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)));
        var reservationUpdated = reservationRepository.save(reservationToUpdate);
        return entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var reservationDelete = reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException("reservation"));
        reservationRepository.delete(reservationDelete);
    }

    private ReservationResponse entityToResponse(ReservationEntity reservationEntity) {
        var response = new ReservationResponse();
        BeanUtils.copyProperties(reservationEntity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(reservationEntity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    private static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
}