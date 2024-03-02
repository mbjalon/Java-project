package impl.reservationsystemapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import impl.reservationsystemapp.Controllers.ReservationController;
import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Repositories.CourtRepository;
import impl.reservationsystemapp.Repositories.SurfaceRepository;
import impl.reservationsystemapp.Services.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurfaceRepository surfaceRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testCourtController() throws Exception {
        Surface surface = new Surface();
        surface.setName("Asphalt");
        surface.setRentalPrice(3.35);

        surfaceRepository.save(surface);

        Court court = new Court();
        court.setName("Court Brno");
        court.setSurface(surface);

        courtRepository.save(court);

        Long courtId = court.getId();

        mockMvc.perform(get("/api/courts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Court Brno"));

        mockMvc.perform(get("/api/courts/{id}", courtId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Court Brno"));

        mockMvc.perform(put("/api/courts/{id}", courtId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MessageFormat.format("'{'\"name\": \"Updated Court Brno\", \"surface\": '{'\"id\": {0}'}' '}'", surface.getId())))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/courts/{id}", courtId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSurfaceController() throws Exception {
        Surface surface = new Surface();
        surface.setName("Carpet");
        surface.setRentalPrice(3.30);

        surface = surfaceRepository.save(surface);

        Long surfaceId = surface.getId();

        mockMvc.perform(get("/api/surfaces/{id}", surfaceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carpet"));

        mockMvc.perform(put("/api/surfaces/{id}", surfaceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Carpet\", \"rentalPrice\": 13.0 }"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/surfaces/{id}", surfaceId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUserController() throws Exception {
        String createUserJson = "{ \"name\": \"Phill Doo\", \"phoneNumber\": \"+987654321\" }";
        MvcResult createUserResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = createUserResult.getResponse().getContentAsString();
        Long userId = Long.parseLong(JsonPath.read(responseBody, "$.id").toString());

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phill Doo"));

        String updateUserJson = "{ \"name\": \"Roman Lama\", \"phoneNumber\": \"+123456789\" }";
        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllReservations() throws Exception {
        reservationService = mock(ReservationService.class);
        ReservationController reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        List<Reservation> reservations = new ArrayList<>();
        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testGetReservationById() throws Exception {
        reservationService = mock(ReservationService.class);
        ReservationController reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        Long id = 5L;
        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(reservationService.getReservationById(id)).thenReturn(reservation);

        mockMvc.perform(get("/api/reservations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(reservationService, times(1)).getReservationById(id);
    }

    @Test
    void testCreateReservation() throws Exception {
        reservationService = mock(ReservationService.class);
        ReservationController reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        Reservation reservation = new Reservation();
        reservation.setId(3L);

        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L));

        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    void testUpdateReservation() throws Exception {
        reservationService = mock(ReservationService.class);
        ReservationController reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        Long id = 9L;
        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(reservationService.updateReservation(eq(id), any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/api/reservations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(reservationService, times(1)).updateReservation(eq(id), any(Reservation.class));
    }

    @Test
    void testDeleteReservation() throws Exception {
        reservationService = mock(ReservationService.class);
        ReservationController reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        Long id = 12L;

        mockMvc.perform(delete("/api/reservations/{id}", id))
                .andExpect(status().isNoContent());

        verify(reservationService, times(1)).deleteReservation(id);
    }
}
