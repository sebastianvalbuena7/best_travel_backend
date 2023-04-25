package com.sebastian.bestTravel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tour")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ReservationEntity> reservations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TicketEntity> tickets;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    public void addTicket(TicketEntity ticketEntity) {
        if(Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.add(ticketEntity);
    }

    public void removeTicket(UUID id) {
        if(Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.removeIf(ticketEntity -> ticketEntity.getId().equals(id));
    }

    public void updateTickets() {
        this.tickets.forEach(ticketEntity -> ticketEntity.setTour(this));
    }

    public void addReservation(ReservationEntity reservationEntity) {
        if(Objects.isNull(this.reservations)) this.reservations = new HashSet<>();
        this.reservations.add(reservationEntity);
    }

    public void removeReservation(UUID id) {
        if(Objects.isNull(this.reservations)) this.reservations = new HashSet<>();
        this.reservations.removeIf(reservationEntity -> reservationEntity.getId().equals(id));
    }

    public void updateReservation() {
        this.reservations.forEach(reservationEntity -> reservationEntity.setTour(this));
    }
}