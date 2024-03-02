package impl.reservationsystemapp;

import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Entities.User;
import impl.reservationsystemapp.Services.CourtService;
import impl.reservationsystemapp.Services.ReservationService;
import impl.reservationsystemapp.Services.SurfaceService;
import impl.reservationsystemapp.Services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ServicesTest {
    @Autowired
    private CourtService courtService;

    @Autowired
    private SurfaceService surfaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @AfterEach
    public void cleanup() {
        List<Reservation> reservations = reservationService.getAllReservations();
        reservations.forEach(reservation -> reservationService.deleteReservation(reservation.getId()));

        List<Court> courts = courtService.getAllCourts();
        courts.forEach(court -> courtService.deleteCourt(court.getId()));

        List<Surface> surfaces = surfaceService.getAllSurfaces();
        surfaces.forEach(surface -> surfaceService.deleteSurface(surface.getId()));

        List<User> users = userService.getAllUsers();
        users.forEach(user -> userService.deleteUser(user.getId()));
    }


    @Test
    public void testCreateCourt() {
        Surface surface = new Surface("Clay", 2.3);
        surfaceService.createSurface(surface);

        Court court = new Court();
        court.setName("Court 21");
        court.setSurface(surface);
        courtService.createCourt(court);

        Court foundCourt = courtService.getCourtById(court.getId());
        assertThat(foundCourt).isNotNull();
        assertThat(foundCourt.getName()).isEqualTo("Court 21");
        assertThat(foundCourt.getSurface()).isEqualTo(surface);
    }

    @Test
    public void testCreateSurface() {
        Surface surface = new Surface("Clay", 3.2);
        surfaceService.createSurface(surface);

        Surface foundSurface = surfaceService.getSurfaceById(surface.getId());
        assertThat(foundSurface).isNotNull();
        assertThat(foundSurface.getName()).isEqualTo("Clay");
        assertThat(foundSurface.getRentalPrice()).isEqualTo(3.2);
    }

    @Test
    public void testCreateUser() {
        User user = new User("+5828919818", "Mike Scott");
        userService.createUser(user);

        User foundUser = userService.getUserById(user.getId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Mike Scott");
        assertThat(foundUser.getPhoneNumber()).isEqualTo("+5828919818");
    }

    @Test
    public void testCreateReservation() {
        Surface surface = new Surface("Clay", 3.2);
        surfaceService.createSurface(surface);

        Court court = new Court();
        court.setName("Court 43");
        court.setSurface(surface);
        courtService.createCourt(court);

        User user = new User("+5828919818", "Mike Scott");
        userService.createUser(user);

        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(3);
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);
        reservationService.createReservation(reservation);

        Reservation foundReservation = reservationService.getReservationById(reservation.getId());
        assertThat(foundReservation).isNotNull();
        assertThat(foundReservation.getCourt()).isEqualTo(court);
        assertThat(foundReservation.getUser()).isEqualTo(user);
        assertThat(foundReservation.getStartTime()).isEqualTo(startTime);
        assertThat(foundReservation.getEndTime()).isEqualTo(endTime);
    }

    @Test
    public void testCreateReservationWithInvalidTimeRange() {
        Surface surface = new Surface("Clay", 3.58);
        surfaceService.createSurface(surface);

        Court court = new Court();
        court.setName("Court 132");
        court.setSurface(surface);
        courtService.createCourt(court);

        User user = new User("+5828919818", "Mike Scott");
        userService.createUser(user);

        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        assertThrows(IllegalArgumentException.class, () -> new Reservation(court, user, startTime, endTime, false));
    }

    @Test
    public void testGetAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        assertThat(reservations).isNotNull();
    }

    @Test
    public void testCreateReservationWithNonExistingUser() {
        Surface surface = new Surface("Clay", 3.87);
        surfaceService.createSurface(surface);

        Court court = new Court();
        court.setName("Court 132");
        court.setSurface(surface);
        courtService.createCourt(court);

        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(3);
        User nonExistingUser = new User("999999999", "Non Existing User");

        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            Reservation reservation = new Reservation(court, nonExistingUser, startTime, endTime, false);
            reservationService.createReservation(reservation);
            reservationService.deleteReservation(reservation.getId());
        });

    }

}
