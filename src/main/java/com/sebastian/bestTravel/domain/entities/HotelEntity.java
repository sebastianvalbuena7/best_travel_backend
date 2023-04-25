package com.sebastian.bestTravel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "hotel")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String address;
    private Integer rating;
    private BigDecimal price;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ReservationEntity> reservation;
}