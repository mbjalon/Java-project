package impl.reservationsystemapp;

import impl.reservationsystemapp.Entities.*;
import impl.reservationsystemapp.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RepositoryTests {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private SurfaceRepository surfaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testCourtRepository() {
        Surface surface = new Surface();
        surface.setName("Grass");
        surface.setRentalPrice(10.0);
        surfaceRepository.save(surface);

        Court court = new Court();
        court.setName("Court 1");
        court.setSurface(surface);
        courtRepository.save(court);

        Court foundCourt = courtRepository.findById(court.getId()).orElse(null);
        assertThat(foundCourt).isNotNull();
        assert foundCourt != null;
        assertThat(foundCourt.getName()).isEqualTo("Court 1");
        assertThat(foundCourt.getSurface()).isEqualTo(surface);
    }

    @Test
    public void testSurfaceRepository() {
        Surface surface = new Surface();
        surface.setName("Grass");
        surface.setRentalPrice(10.0);
        surfaceRepository.save(surface);

        Surface foundSurface = surfaceRepository.findById(surface.getId()).orElse(null);
        assertThat(foundSurface).isNotNull();
        assert foundSurface != null;
        assertThat(foundSurface.getName()).isEqualTo("Grass");
        assertThat(foundSurface.getRentalPrice()).isEqualTo(10.0);
    }

    @Test
    public void testUserRepository() {
        User user = new User();
        user.setName("John Doe");
        user.setPhoneNumber("123456789");
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        assertThat(foundUser).isNotNull();
        assert foundUser != null;
        assertThat(foundUser.getName()).isEqualTo("John Doe");
        assertThat(foundUser.getPhoneNumber()).isEqualTo("123456789");
    }

    @Test
    public void testReservationRepository() {
        Surface surface = new Surface();
        surface.setName("Grass");
        surface.setRentalPrice(10.0);
        surfaceRepository.save(surface);

        Court court = new Court();
        court.setName("Court 1");
        court.setSurface(surface);
        courtRepository.save(court);

        User user = new User();
        user.setName("John Doe");
        user.setPhoneNumber("123456789");
        userRepository.save(user);

        Reservation reservation = new Reservation();
        reservation.setCourt(court);
        reservation.setStartTime(LocalDateTime.now().plusHours(1));
        reservation.setEndTime(LocalDateTime.now().plusHours(3));
        reservation.setUser(user);
        reservation.setDoubles(false);
        reservation.setPrice(10.0);
        reservationRepository.save(reservation);

        Reservation foundReservation = reservationRepository.findById(reservation.getId()).orElse(null);
        assertThat(foundReservation).isNotNull();
        assert foundReservation != null;
        assertThat(foundReservation.getCourt()).isEqualTo(court);
        assertThat(foundReservation.getUser()).isEqualTo(user);
        assertThat(foundReservation.isDoubles()).isFalse();
        assertThat(foundReservation.getPrice()).isEqualTo(10.0);
    }

    @Test
    public void testUpdateCourt() {
        Surface surface = new Surface();
        surface.setName("Grass");
        surface.setRentalPrice(10.0);
        surfaceRepository.save(surface);

        Court court = new Court();
        court.setName("Court 1");
        court.setSurface(surface);
        courtRepository.save(court);

        Court foundCourt = courtRepository.findById(court.getId()).orElse(null);
        assertThat(foundCourt).isNotNull();

        assert foundCourt != null;
        foundCourt.setName("Updated Court Name");
        courtRepository.save(foundCourt);

        Court updatedCourt = courtRepository.findById(court.getId()).orElse(null);
        assertThat(updatedCourt).isNotNull();
        assert updatedCourt != null;
        assertThat(updatedCourt.getName()).isEqualTo("Updated Court Name");
    }

    @Test
    public void testDeleteSurface() {
        Surface surface = new Surface();
        surface.setName("Grass");
        surface.setRentalPrice(10.0);
        surfaceRepository.save(surface);

        surfaceRepository.deleteById(surface.getId());

        Surface deletedSurface = surfaceRepository.findById(surface.getId()).orElse(null);
        assertThat(deletedSurface).isNull();
    }

    @Test
    public void testReservationWithUserAndCourt() {
        User user = new User();
        user.setName("John Doe");
        user.setPhoneNumber("123456789");
        userRepository.save(user);

        Surface surface = new Surface();
        surface.setName("Grass");
        surface.setRentalPrice(10.0);
        surfaceRepository.save(surface);

        Court court = new Court();
        court.setName("Court 1");
        court.setSurface(surface);
        courtRepository.save(court);

        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);
        Reservation reservation = new Reservation();
        reservation.setCourt(court);
        reservation.setUser(user);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setDoubles(false);
        reservation.setPrice(20.0);
        reservationRepository.save(reservation);

        Reservation foundReservation = reservationRepository.findById(reservation.getId()).orElse(null);
        assertThat(foundReservation).isNotNull();
        assert foundReservation != null;
        assertThat(foundReservation.getCourt()).isEqualTo(court);
        assertThat(foundReservation.getUser()).isEqualTo(user);
        assertThat(foundReservation.getStartTime()).isEqualTo(startTime);
        assertThat(foundReservation.getEndTime()).isEqualTo(endTime);
    }
}
