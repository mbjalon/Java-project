package impl.reservationsystemapp;


import impl.reservationsystemapp.Controllers.*;
import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Entities.User;
import impl.reservationsystemapp.Services.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class ControllersTests {

    @Mock
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;

    @Mock
    private SurfaceService surfaceService;
    @InjectMocks
    private SurfaceController surfaceController;

    @Mock
    private CourtService courtService;
    @InjectMocks
    private CourtController courtController;

   @Mock
   private UserService userService;
   @InjectMocks
   private UserController userController;

    @Mock
    private ReservationService reservationService;
    @InjectMocks
    private ReservationController reservationController;

    public ControllersTests() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

    }

    @Test
    public void testLogin_Success() {
        String username = "testUser";
        String password = "testPassword";
        String jwtToken = "jwtToken";

        AuthRequestDto authRequestDto = new AuthRequestDto("", "", username, password);

        when(authService.login(username, password)).thenReturn(jwtToken);

        ResponseEntity<AuthResponseDto> responseEntity = authController.login(authRequestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(jwtToken, responseEntity.getBody().token());
        assertEquals(AuthStatus.LOGIN_SUCCESS, responseEntity.getBody().authStatus());

        verify(authService).login(username, password);
    }

    @Test
    public void testSignUp_Success() {
        String phoneNumber = "1234567890";
        String name = "Test User";
        String username = "testUser";
        String password = "testPassword";
        String jwtToken = "jwtToken";

        AuthRequestDto authRequestDto = new AuthRequestDto(phoneNumber, name, username, password);

        when(authService.signUp(phoneNumber, name, username, password)).thenReturn(jwtToken);

        ResponseEntity<AuthResponseDto> responseEntity = authController.signUp(authRequestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(jwtToken, responseEntity.getBody().token());
        assertEquals(AuthStatus.USER_CREATED_SUCCESSFULLY, responseEntity.getBody().authStatus());

        verify(authService).signUp(phoneNumber, name, username, password);
    }

    @Test
    public void testGetAllSurfaces_Success() {
        List<Surface> surfaces = new ArrayList<>();
        surfaces.add(new Surface(1L, "Surface 1", 3.15));
        surfaces.add(new Surface(2L, "Surface 2", 2.8));

        when(surfaceService.getAllSurfaces()).thenReturn(surfaces);

        ResponseEntity<List<Surface>> responseEntity = surfaceController.getAllSurfaces();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(surfaces, responseEntity.getBody());

        verify(surfaceService).getAllSurfaces();
    }

    @Test
    public void testGetSurfaceById_Success() {
        Long id = 1L;
        Surface surface = new Surface(id, "Surface 1", 8.4);

        when(surfaceService.getSurfaceById(id)).thenReturn(surface);

        ResponseEntity<Surface> responseEntity = surfaceController.getSurfaceById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(surface, responseEntity.getBody());

        verify(surfaceService).getSurfaceById(id);
    }

    @Test
    public void testCreateSurface_Success() {
        Surface surface = new Surface(1L, "Surface 1", 5.15);

        when(surfaceService.createSurface(surface)).thenReturn(surface);

        ResponseEntity<Surface> responseEntity = surfaceController.createSurface(surface);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(surface, responseEntity.getBody());

        verify(surfaceService).createSurface(surface);
    }

    @Test
    public void testUpdateSurface_Success() {
        Long id = 1L;
        Surface updatedSurface = new Surface(id, "Updated Surface", 4.15);

        when(surfaceService.updateSurface(id, updatedSurface)).thenReturn(updatedSurface);

        ResponseEntity<Surface> responseEntity = surfaceController.updateSurface(id, updatedSurface);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedSurface, responseEntity.getBody());

        verify(surfaceService).updateSurface(id, updatedSurface);
    }

    @Test
    public void testDeleteSurface_Success() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = surfaceController.deleteSurface(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(surfaceService).deleteSurface(id);
    }

    @Test
    public void testGetAllCourts_Success() {
        List<Court> courts = new ArrayList<>();
        courts.add(new Court(1L, "Court 1", new Surface(1L, "surface1", 3.15)));
        courts.add(new Court(2L, "Court 2",  new Surface(2L, "surface2", 5.15)));

        when(courtService.getAllCourts()).thenReturn(courts);

        ResponseEntity<List<Court>> responseEntity = courtController.getAllCourts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(courts, responseEntity.getBody());

        verify(courtService).getAllCourts();
    }

    @Test
    public void testGetCourtById_Success() {
        Long id = 1L;
        Court court = new Court(id, "Court 1",  new Surface(1L, "surface", 3.15));

        when(courtService.getCourtById(id)).thenReturn(court);

        ResponseEntity<Court> responseEntity = courtController.getCourtById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(court, responseEntity.getBody());

        verify(courtService).getCourtById(id);
    }

    @Test
    public void testCreateCourt_Success() {
        Surface surface = new Surface();
        surface.setId(1L);
        surface.setName("Grass");
        Court court = new Court(1L, "Court 1", surface);

        when(courtService.createCourt(court)).thenReturn(court);

        ResponseEntity<Court> responseEntity = courtController.createCourt(court);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(court, responseEntity.getBody());

        verify(courtService).createCourt(court);
    }

    @Test
    public void testUpdateCourt_Success() {
        Surface surface = new Surface();
        surface.setId(1L);
        surface.setName("Grass");
        Long id = 1L;
        Court updatedCourt = new Court(id, "Updated Court", surface);

        when(courtService.updateCourt(id, updatedCourt)).thenReturn(updatedCourt);

        ResponseEntity<Court> responseEntity = courtController.updateCourt(id, updatedCourt);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCourt, responseEntity.getBody());

        verify(courtService).updateCourt(id, updatedCourt);
    }

    @Test
    public void testDeleteCourt_Success() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = courtController.deleteCourt(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(courtService).deleteCourt(id);
    }

    @Test
    public void testGetAllUsers_Success() {
        List<User> users = new ArrayList<>();
        users.add(new User("+421932029103", "Janko Pekný", "janicek00", "jankop", Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));
        users.add(new User("831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());

        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUserById_Success() {
        Long id = 1L;
        User user = new User("+421932029103", "Janko Pekný", "janicek00", "jankop", Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));


        when(userService.getUserById(id)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.getUserById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

        verify(userService).getUserById(id);
    }

    @Test
    public void testCreateUser_Success() {
        User user = new User("831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

        verify(userService).createUser(user);
    }

    @Test
    public void testUpdateUser_Success() {
        Long id = 1L;
        User updatedUser = new User("831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(userService.updateUser(id, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.updateUser(id, updatedUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());

        verify(userService).updateUser(id, updatedUser);
    }

    @Test
    public void testDeleteUser_Success() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = userController.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(userService).deleteUser(id);
    }

    @Test
    public void testCreateReservation_Success() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);

        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        ResponseEntity<?> responseEntity = reservationController.createReservation(reservation);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(reservation, responseEntity.getBody());

        verify(reservationService).createReservation(reservation);
    }

    @Test
    public void testGetAllReservations_Success() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(createReservation());
        reservations.add(createReservation());

        when(reservationService.getAllReservations()).thenReturn(reservations);

        ResponseEntity<List<Reservation>> responseEntity = reservationController.getAllReservations();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reservations, responseEntity.getBody());

        verify(reservationService).getAllReservations();
    }

    @Test
    public void testGetReservationById_Success() {
        Long id = 1L;
        Reservation reservation = createReservation();

        when(reservationService.getReservationById(id)).thenReturn(reservation);

        ResponseEntity<Reservation> responseEntity = reservationController.getReservationById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reservation, responseEntity.getBody());

        verify(reservationService).getReservationById(id);
    }

    @Test
    public void testUpdateReservation_Success() {
        Long id = 1L;
        Reservation updatedReservation = createReservation();

        when(reservationService.updateReservation(id, updatedReservation)).thenReturn(updatedReservation);

        ResponseEntity<Reservation> responseEntity = reservationController.updateReservation(id, updatedReservation);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedReservation, responseEntity.getBody());

        verify(reservationService).updateReservation(id, updatedReservation);
    }

    @Test
    public void testDeleteReservation_Success() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = reservationController.deleteReservation(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(reservationService).deleteReservation(id);
    }

    private Reservation createReservation() {
        Surface surface = new Surface(1L, "Hard court", 3.0);
        Court court = new Court(1L, "US Open", surface);
        User user = new User(1L, "831783313", "Sam Haha", "hahaXd", "samhah", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        return new Reservation(court, user, startTime, endTime, false);
    }

    @Test
    public void testLogin_ValidCredentials_Success() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String token = "mockedToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(createMockAuthentication());
        when(authService.login(username, password)).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk());
    }


    private Authentication createMockAuthentication() {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("testUser", "testPassword", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
