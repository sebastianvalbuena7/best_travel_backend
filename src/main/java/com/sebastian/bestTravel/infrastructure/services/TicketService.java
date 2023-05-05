package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.api.models.request.TicketRequest;
import com.sebastian.bestTravel.api.models.response.FlyResponse;
import com.sebastian.bestTravel.api.models.response.TicketResponse;
import com.sebastian.bestTravel.domain.entities.TicketEntity;
import com.sebastian.bestTravel.domain.repositories.CustomerRepository;
import com.sebastian.bestTravel.domain.repositories.FlyRepository;
import com.sebastian.bestTravel.domain.repositories.TicketRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.ITicketService;
import com.sebastian.bestTravel.infrastructure.helpers.BlackListHelper;
import com.sebastian.bestTravel.infrastructure.helpers.CustomerHelper;
import com.sebastian.bestTravel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;
    private BlackListHelper blackListHelper;

    @Override
    public BigDecimal findPrice(Long flyId) {
        var fly = flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));
    }

    @Override
    public TicketResponse create(TicketRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.laterRandomLater())
                .departureDate(BestTravelUtil.getRandomSoon())
                .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                .build();

        var ticketPersistent = ticketRepository.save(ticketToPersist);
        customerHelper.increase(customer.getDni(), TicketService.class);
        log.info("Ticket Saved with id: " + ticketPersistent.getId());

        return entityToResponse(ticketPersistent);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = ticketRepository.findById(uuid).orElseThrow();
        return entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow();
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)));
        ticketToUpdate.setArrivalDate(BestTravelUtil.laterRandomLater());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        var ticketUpdated = ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated" + ticketUpdated.getId());
        return entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = ticketRepository.findById(uuid).orElseThrow();
        ticketRepository.delete(ticketToDelete);
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
//        response.setId(entity.getId()); // Eso seria sin BeanUtils.
        BeanUtils.copyProperties(entity, response); // Matchea las propiedades entre TicketResponse y TicketEntity
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
}