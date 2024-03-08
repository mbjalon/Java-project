package impl.reservationsystemapp;

import impl.reservationsystemapp.Authentication.util.JwtUtils;
import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Entities.User;
import impl.reservationsystemapp.Repositories.CourtRepository;
import impl.reservationsystemapp.Repositories.ReservationRepository;
import impl.reservationsystemapp.Repositories.SurfaceRepository;
import impl.reservationsystemapp.Repositories.UserRepository;
import impl.reservationsystemapp.Services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicesTest {

    @Mock
    private SurfaceRepository surfaceRepository;

    @InjectMocks
    private SurfaceService surfaceService;

    @Mock
    private CourtRepository courtRepository;

    @InjectMocks
    private CourtService courtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateSurface() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        when(surfaceRepository.save(surface)).thenReturn(surface);
        Surface createdSurface = surfaceService.createSurface(surface);
        assertEquals(surface, createdSurface);
        verify(surfaceRepository, times(1)).save(surface);
    }

    @Test
    public void testUpdateSurface() {
        Surface existingSurface = new Surface(1L, "Grass", 10.0);
        Surface updatedSurface = new Surface(1L, "Clay", 12.0);
        when(surfaceRepository.findById(1L)).thenReturn(Optional.of(existingSurface));
        when(surfaceRepository.save(updatedSurface)).thenReturn(updatedSurface);
        Surface result = surfaceService.updateSurface(1L, updatedSurface);
        assertEquals(updatedSurface, result);
        verify(surfaceRepository, times(1)).findById(1L);
        verify(surfaceRepository, times(1)).save(updatedSurface);
    }

    @Test
    public void testUpdateSurfaceForNonExistentSurface() {
        Surface updatedSurface = new Surface(1L, "Clay", 12.0);
        when(surfaceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> surfaceService.updateSurface(1L, updatedSurface));
        verify(surfaceRepository, times(1)).findById(1L);
        verify(surfaceRepository, never()).save(updatedSurface);
    }

    @Test
    public void testDeleteSurface() {
        Surface existingSurface = new Surface(1L, "Grass", 10.0);
        when(surfaceRepository.findById(1L)).thenReturn(Optional.of(existingSurface));
        surfaceService.deleteSurface(1L);
        verify(surfaceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSurfaceForNonExistentSurface() {
        when(surfaceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> surfaceService.deleteSurface(1L));
        verify(surfaceRepository, times(1)).findById(1L);
        verify(surfaceRepository, never()).deleteById(1L);
    }

    @Test
    public void testGetAllSurfaces() {
        List<Surface> surfaces = new ArrayList<>();
        Surface surface1 = new Surface(1L, "Grass", 10.0);
        Surface surface2 = new Surface(2L, "Clay", 12.0);
        surfaces.add(surface1);
        surfaces.add(surface2);
        when(surfaceRepository.findAll()).thenReturn(surfaces);
        List<Surface> result = surfaceService.getAllSurfaces();
        assertEquals(surfaces.size(), result.size());
        assertEquals(surfaces, result);
        verify(surfaceRepository, times(1)).findAll();
    }

    @Test
    public void testGetSurfaceById() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        when(surfaceRepository.findById(1L)).thenReturn(Optional.of(surface));
        Surface result = surfaceService.getSurfaceById(1L);
        assertEquals(surface, result);
        verify(surfaceRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetSurfaceByIdForNonExistentSurface() {
        when(surfaceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> surfaceService.getSurfaceById(1L));
        verify(surfaceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSurfaceById_ExistingId() {
        Surface surface = new Surface(1L, "Clay", 10.0);
        when(surfaceRepository.findById(1L)).thenReturn(java.util.Optional.of(surface));

        Surface result = surfaceService.getSurfaceById(1L);

        assertNotNull(result);
        assertEquals("Clay", result.getName());
        assertEquals(10.0, result.getRentalPrice());
    }

    @Test
    void testGetSurfaceById_NonExistingId() {
        when(surfaceRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> surfaceService.getSurfaceById(1L));
    }

    @Test
    public void testCreateCourt() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        Court court = new Court(1L, "Melbourne", surface);
        when(courtRepository.save(court)).thenReturn(court);
        Court createdCourt = courtService.createCourt(court);
        assertEquals(court, createdCourt);
        verify(courtRepository, times(1)).save(court);
    }

    @Test
    public void testUpdateCourt() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        Court existingCourt = new Court(1L, "Melbourne", surface);
        Court updatedCourt = new Court(1L, "Melbourne 1", surface);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(existingCourt));
        when(courtRepository.save(updatedCourt)).thenReturn(updatedCourt);
        Court result = courtService.updateCourt(1L, updatedCourt);
        assertEquals(updatedCourt, result);
        verify(courtRepository, times(1)).findById(1L);
        verify(courtRepository, times(1)).save(updatedCourt);
    }

    @Test
    public void testUpdateCourtForNonExistentCourt() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        Court updatedCourt = new Court(1L, "Melbourne 1", surface);
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> courtService.updateCourt(1L, updatedCourt));
        verify(courtRepository, times(1)).findById(1L);
        verify(courtRepository, never()).save(updatedCourt);
    }

    @Test
    public void testDeleteCourt() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        Court existingCourt = new Court(1L, "Melbourne", surface);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(existingCourt));
        courtService.deleteCourt(1L);
        verify(courtRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCourtForNonExistentCourt() {
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> courtService.deleteCourt(1L));
        verify(courtRepository, times(1)).findById(1L);
        verify(courtRepository, never()).deleteById(1L);
    }

    @Test
    public void testGetAllCourts() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        List<Court> courts = new ArrayList<>();
        Court court1 = new Court(1L, "Melbourne", surface);
        Court court2 = new Court(2L, "Wimbledon", surface);
        courts.add(court1);
        courts.add(court2);
        when(courtRepository.findAll()).thenReturn(courts);
        List<Court> result = courtService.getAllCourts();
        assertEquals(courts.size(), result.size());
        assertEquals(courts, result);
        verify(courtRepository, times(1)).findAll();
    }

    @Test
    public void testGetCourtById() {
        Surface surface = new Surface(1L, "Grass", 10.0);
        Court court = new Court(1L, "Melbourne", surface);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        Court result = courtService.getCourtById(1L);
        assertEquals(court, result);
        verify(courtRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCourtByIdForNonExistentCourt() {
        when(courtRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> courtService.getCourtById(1L));
        verify(courtRepository, times(1)).findById(1L);
    }


    @Test
    public void testCreateUser() {
        User user = new User("002913810", "In Qool", "q00l1n", "inQool", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User(1L, "93810371", "Joe Test", "J0etst", "joeTest", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        User updatedUser = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        User result = userService.updateUser(1L, updatedUser);
        assertEquals(updatedUser, result);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    public void testUpdateUserForNonExistentUser() {
        User updatedUser = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.updateUser(1L, updatedUser));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        User existingUser = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUserForNonExistentUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User(1L, "1873917", "Luke Zixs", "Z1xlks", "zixis", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        User user2 = new User(2L, "12984491", "Mark Quinn", "quinno00M", "marxq", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(users.size(), result.size());
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User(2L, "12984491", "Mark Quinn", "quinno00M", "marxq", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserByIdForNonExistentUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateReservation() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation createdReservation = reservationService.createReservation(reservation);
        assertEquals(reservation, createdReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testUpdateReservation() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        Reservation existingReservation = new Reservation(court, user, startTime, endTime, false);
        Reservation updatedReservation = new Reservation(court, user, startTime, endTime.plusHours(1), true);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(reservationRepository.save(updatedReservation)).thenReturn(updatedReservation);
        Reservation result = reservationService.updateReservation(1L, updatedReservation);
        assertEquals(updatedReservation, result);
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(updatedReservation);
    }

    @Test
    public void testUpdateReservationForNonExistentReservation() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(2L, "12984491", "Mark Quinn", "quinno00M", "marxq", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        Reservation updatedReservation = new Reservation(court, user, startTime, endTime.plusHours(1), true);
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> reservationService.updateReservation(1L, updatedReservation));
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).save(updatedReservation);
    }

    @Test
    public void testDeleteReservation() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(2L, "12984491", "Mark Quinn", "quinno00M", "marxq", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        Reservation existingReservation = new Reservation(court, user, startTime, endTime, false);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));
        reservationService.deleteReservation(1L);
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteReservationForNonExistentReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> reservationService.deleteReservation(1L));
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).deleteById(1L);
    }

    @Test
    public void testGetAllReservations() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(2L, "12984491", "Mark Quinn", "quinno00M", "marxq", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation(court, user, startTime, endTime, false);
        Reservation reservation2 = new Reservation(court, user, startTime.plusDays(1), endTime.plusDays(1), true);
        reservations.add(reservation1);
        reservations.add(reservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationService.getAllReservations();
        assertEquals(reservations.size(), result.size());
        assertEquals(reservations, result);
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testGetReservationById() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(2L, "12984491", "Mark Quinn", "quinno00M", "marxq", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Reservation result = reservationService.getReservationById(1L);
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetReservationByIdForNonExistentReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> reservationService.getReservationById(1L));
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void testLogin_Success() {
        String username = "testuser";
        String password = "testpassword";
        String encodedPassword = "encodedPassword";
        String token = "generatedToken";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.existsByUsername(username)).thenReturn(true);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(userDetails, password));

        mockStatic(JwtUtils.class);
        when(JwtUtils.generateToken(username)).thenReturn(token);

        String authToken = authService.login(username, password);

        assertEquals(token, authToken);
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void testLogin_Failure() {
        String username = "testuser";
        String password = "testpassword";

        when(userRepository.existsByUsername(username)).thenReturn(false);
        verify(authenticationManager, never()).authenticate(any());

        assertThrows(RuntimeException.class, () -> authService.login(username, password));
    }

    @Test
    public void testSignUp_Success() {
        String phoneNumber = "1234567890";
        String name = "Test User";
        String username = "testUser";
        String password = "testPassword";
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        String token = authService.signUp(phoneNumber, name, username, password);

        assertNotNull(token);
    }

    @Test
    void testSignUp_Failure_UserAlreadyExists() {
        String phoneNumber = "1234567890";
        String name = "Test User";
        String username = "testuser";
        String password = "testpassword";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.signUp(phoneNumber, name, username, password));
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testSignUp_UserAlreadyExists() {
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        AuthService authService = new AuthService(authenticationManager, passwordEncoder, userRepository);

        String phoneNumber = "123456789";
        String name = "Test User";
        String username = "testUser";
        String password = "testPassword";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.signUp(phoneNumber, name, username, password));
        verify(userRepository, times(1)).existsByUsername(username);
        verify(passwordEncoder, never()).encode(password);
        verify(userRepository, never()).save(any(User.class));
    }
}