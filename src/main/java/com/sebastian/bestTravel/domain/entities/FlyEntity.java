package com.sebastian.bestTravel.domain.entities;

import com.sebastian.bestTravel.util.AeroLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "fly")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class FlyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private BigDecimal price;
    @Column(length = 20)
    private String destinyName;
    @Column(length = 20)
    private String originName;
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fly", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TicketEntity> ticket;
}