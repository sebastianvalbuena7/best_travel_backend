package com.sebastian.bestTravel.domain.entities.jpa;

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

    // Ciclo de vida de una entidad♥️
    @PrePersist // Antes de guardar en la base de datos actualiza las claves foraneas
    @PreRemove // Antes de que se vaya a remover elimina las claves foraneas con sus tickets y reservations
    public void updateFks() {
        this.tickets.forEach(ticketEntity -> ticketEntity.setTour(this));
        this.reservations.forEach(reservationEntity -> reservationEntity.setTour(this));
    }

    public void removeTicket(UUID idTicket) {
        this.tickets.forEach(ticketEntity -> {
            if (ticketEntity.getId().equals(idTicket)) {
                ticketEntity.setTour(null);
            }
        });
    }

    public void addTicket(TicketEntity ticket) {
        if(Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.add(ticket);
        this.tickets.forEach(ticketEntity -> ticketEntity.setTour(this));
    }

    public void removeReservation(UUID idReservation) {
        this.reservations.forEach(reservationEntity -> {
            if (reservationEntity.getId().equals(idReservation)) {
                reservationEntity.setTour(null);
            }
        });
    }

    public void addReservation(ReservationEntity reservation) {
        if(Objects.isNull(this.reservations)) this.reservations = new HashSet<>();
        this.reservations.add(reservation);
        this.reservations.forEach(reservationEntity -> reservationEntity.setTour(this));
    }

//    public void removeTicket(UUID id) {
//        if(Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
//        this.tickets.removeIf(ticketEntity -> ticketEntity.getId().equals(id));
//    }
}