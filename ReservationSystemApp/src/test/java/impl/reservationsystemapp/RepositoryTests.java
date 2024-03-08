package impl.reservationsystemapp;

import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Entities.User;
import impl.reservationsystemapp.Repositories.CourtRepository;
import impl.reservationsystemapp.Repositories.ReservationRepository;
import impl.reservationsystemapp.Repositories.SurfaceRepository;
import impl.reservationsystemapp.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepositoryTests {
    @Mock
    private CourtRepository courtRepository;

    @Mock
    private SurfaceRepository surfaceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllSurfaces() {
        List<Surface> surfaces = new ArrayList<>();
        surfaces.add(new Surface(1L, "Grass", 10.0));
        surfaces.add(new Surface(2L, "Clay", 8.0));
        when(surfaceRepository.findAll()).thenReturn(surfaces);
        List<Surface> result = surfaceRepository.findAll();
        assertEquals(surfaces.size(), result.size());
        assertEquals(surfaces, result);
        verify(surfaceRepository, times(1)).findAll();
    }

    @Test
    public void testGetSurfaceById() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        when(surfaceRepository.findById(1L)).thenReturn(Optional.of(surface));
        Optional<Surface> result = surfaceRepository.findById(1L);
        assertEquals(surface, result.get());
        verify(surfaceRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllCourts() {
        List<Court> courts = new ArrayList<>();
        courts.add(new Court(1L, "Sydney", new Surface()));
        courts.add(new Court(2L, "London", new Surface()));
        when(courtRepository.findAll()).thenReturn(courts);
        List<Court> result = courtRepository.findAll();
        assertEquals(courts.size(), result.size());
        assertEquals(courts, result);
        verify(courtRepository, times(1)).findAll();
    }

    @Test
    public void testGetCourtById() {
        Court court = new Court(1L, "New York", new Surface());
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        Optional<Court> result = courtRepository.findById(1L);
        assertEquals(court, result.get());
        verify(courtRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("002913810", "In Qool", "q00l1n", "inQool", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        users.add(new User(1L, "93810371", "Joe Test", "J0etst", "joeTest", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userRepository.findAll();
        assertEquals(users.size(), result.size());
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "93810371", "Joe Test", "J0etst", "joeTest", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userRepository.findById(1L);
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllReservations() {
        Court court1 = new Court(1L, "Brno", new Surface());
        Court court2 = new Court(2L, "Praha", new Surface());
        User user = new User(1L, "93810371", "Joe Test", "J0etst", "joeTest", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime1 = LocalDateTime.now();
        LocalDateTime endTime1 = startTime1.plusHours(1);
        LocalDateTime startTime2 = startTime1.plusHours(2);
        LocalDateTime endTime2 = startTime2.plusHours(1);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(court1, user, startTime1, endTime1, false));
        reservations.add(new Reservation(court2, user, startTime2, endTime2, false));

        when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationRepository.findAll();
        assertEquals(reservations.size(), result.size());
        assertEquals(reservations, result);
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testGetReservationById() {
        Court court = new Court(1L, "Baník Ostrava", new Surface());
        User user = new User(1L, "93810371", "Joe Test", "J0etst", "joeTest", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Optional<Reservation> result = reservationRepository.findById(1L);
        assertEquals(reservation, result.get());
        verify(reservationRepository, times(1)).findById(1L);
    }
}
