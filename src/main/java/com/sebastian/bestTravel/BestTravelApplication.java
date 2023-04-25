package com.sebastian.bestTravel;

import com.sebastian.bestTravel.domain.entities.ReservationEntity;
import com.sebastian.bestTravel.domain.entities.TicketEntity;
import com.sebastian.bestTravel.domain.entities.TourEntity;
import com.sebastian.bestTravel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
//@Slf4j
public class BestTravelApplication implements CommandLineRunner {
//	private final HotelRepository hotelRepository;
//	private final FlyRepository flyRepository;
//	private final TicketRepository ticketRepository;
//	private final CustomerRepository customerRepository;
//	private final ReservationRepository reservationRepository;
//	private final TourRepository tourRepository;
//
//	public BestTravelApplication(HotelRepository hotelRepository,
//								 FlyRepository flyRepository,
//								 TicketRepository ticketRepository,
//								 CustomerRepository customerRepository,
//								 ReservationRepository reservationRepository,
//								 TourRepository tourRepository) {
//		this.hotelRepository = hotelRepository;
//		this.flyRepository = flyRepository;
//		this.ticketRepository = ticketRepository;
//		this.customerRepository = customerRepository;
//		this.reservationRepository = reservationRepository;
//		this.tourRepository = tourRepository;
//	}

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Todo: Uso Basico de JPA
//		var hotel = hotelRepository.findById(7L).get();
//		var ticket = ticketRepository.findById(UUID.fromString("22345678-1234-5678-3235-567812345678")).get();
//		var reservation = reservationRepository.findById(UUID.fromString("22345678-1234-5678-1234-567812345678")).get();
//		var customer = customerRepository.findById("VIKI771012HMCRG093").get();
//		var fly = flyRepository.findById(15L).get();

//		log.info(String.valueOf(reservation));
//		log.info(String.valueOf(ticket));
//		log.info(String.valueOf(fly));
//		log.info(String.valueOf(hotel));
//		log.info(String.valueOf(customer));

		// Todo: Consultas con JPQL

		// flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(f -> System.out.println(f));

		// flyRepository.selectBetweenPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15)).forEach(f -> System.out.println(f));

//		flyRepository.selectOriginDestiny("Grecia", "Mexico").forEach(flyEntity -> System.out.println(flyEntity));
//		var fly =  flyRepository.findByTicketId(UUID.fromString("22345678-1234-5678-3235-567812345678")).orElse(null);
//		System.out.println(fly);


		// Todo: Consultas con lenguaje inclusivo JPA

//		hotelRepository.findByPriceLessThan(BigDecimal.valueOf(90)).forEach(h -> System.out.println(h));

//		hotelRepository.findByPriceBetween(BigDecimal.valueOf(50), BigDecimal.valueOf(100)).forEach(f -> System.out.println(f));

//		hotelRepository.findByRatingGreaterThan(4).forEach(System.out::println);

//		var hotel = hotelRepository.findByReservationId(UUID.fromString("12345678-1234-5678-1234-567812345678"));
//		System.out.println(hotel);

		// Todo: Probando relaciones
//		var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
//		log.info("Client name " + customer.getFullName());
//		var fly = flyRepository.findById(11L).orElseThrow();
//		log.info("Fly " + fly.getDestinyName());
//		var hotel = hotelRepository.findById(3L).orElseThrow();
//		log.info("Hotel" + hotel.getName());
//
//		var tour = TourEntity.builder()
//				.customer(customer)
//				.build();
//
//		var ticket = TicketEntity.builder()
//				.id(UUID.randomUUID())
//				.price(fly.getPrice().multiply(BigDecimal.TEN))
//				.arrivalDate(LocalDateTime.now())
//				.departureDate(LocalDateTime.now())
//				.purchaseDate(LocalDate.now())
//				.customer(customer)
//				.tour(tour)
//				.fly(fly)
//				.build();
//
//		var reservation = ReservationEntity.builder()
//				.id(UUID.randomUUID())
//				.dateTimeReservation(LocalDateTime.now())
//				.dateStart(LocalDate.now().plusDays(1))
//				.dateEnd(LocalDate.now().plusDays(2))
//				.hotel(hotel)
//				.customer(customer)
//				.tour(tour)
//				.totalDays(1)
//				.price(hotel.getPrice().multiply(BigDecimal.TEN))
//				.build();
//
//		tour.addReservation(reservation);
//		tour.updateReservation();
//
//		tour.addTicket(ticket);
//		tour.updateTickets();
//
//		var tourSaved = tourRepository.save(tour);
//		tourRepository.deleteById(tourSaved.getId());
	}
}