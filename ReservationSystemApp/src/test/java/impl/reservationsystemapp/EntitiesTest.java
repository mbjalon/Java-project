package impl.reservationsystemapp;

import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Entities.Reservation;
import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class EntitiesTest {

    @Test
    public void testCreateUser() {
        User user = new User("+4201233929", "Robinko Novák", "robko321", "robinko123", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        assertNotNull(user);
        assertEquals("+4201233929", user.getPhoneNumber());
        assertEquals("Robinko Novák", user.getName());
        assertEquals("robko321", user.getPassword());
        assertEquals("robinko123", user.getUsername());
        assertTrue(user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testEqualsAndHashCodeUser() {
        User user1 = new User("+4201233929", "Robinko Novák", "robko321", "robinko123", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        User user2 = new User("+4201233929", "Robinko Novák", "robko321", "robinko123", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        User user3 = new User("+421932029103", "Janko Pekný", "janicek00", "jankop", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        assertTrue(user1.equals(user2) && user2.equals(user1));
        assertEquals(user1.hashCode(), user2.hashCode());

        assertFalse(user1.equals(user3) || user3.equals(user1));
    }

    @Test
    public void testToStringUser() {
        User user = new User("+421932029103", "Janko Pekný", "janicek00", "jankop", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        String expectedToString = "User(id=null, phoneNumber=+421932029103, name=Janko Pekný, password=janicek00, username=jankop, authorities=[ROLE_ADMIN])";
        assertEquals(expectedToString, user.toString());
    }

    @Test
    public void testCreateSurface() {
        Surface surface = new Surface("Grass", 2.15);
        assertNotNull(surface);
        assertEquals("Grass", surface.getName());
        assertEquals(2.15, surface.getRentalPrice());
    }

    @Test
    public void testEqualsAndHashCodeSurface() {
        Surface surface1 = new Surface("Grass", 2.15);
        Surface surface2 = new Surface("Grass", 2.15);
        Surface surface3 = new Surface("Clay", 3.50);

        assertTrue(surface1.equals(surface2) && surface2.equals(surface1));
        assertEquals(surface1.hashCode(), surface2.hashCode());

        assertFalse(surface1.equals(surface3) || surface3.equals(surface1));
    }

    @Test
    public void testToStringSurface() {
        Surface surface = new Surface("Clay", 3.50);
        String expectedToString = "Surface(id=null, name=Clay, rentalPrice=3.5)";
        assertEquals(expectedToString, surface.toString());
    }

    @Test
    public void testCreateCourt() {
        Surface surface = new Surface("Clay", 3.35);
        Court court = new Court();
        court.setName("US Open");
        court.setSurface(surface);
        assertNotNull(court);
        assertEquals("US Open", court.getName());
        assertEquals(surface, court.getSurface());
    }

    @Test
    public void testEqualsAndHashCodeCourt() {
        Surface surface = new Surface("Grass", 2.35);
        Court court1 = new Court();
        court1.setName("Wimbledon1");
        court1.setSurface(surface);

        Court court2 = new Court();
        court2.setName("Wimbledon1");
        court2.setSurface(surface);

        Court court3 = new Court();
        court3.setName("Wimbledon2");
        court3.setSurface(surface);

        assertTrue(court1.equals(court2) && court2.equals(court1));
        assertEquals(court1.hashCode(), court2.hashCode());

        assertFalse(court1.equals(court3) || court3.equals(court1));
    }

    @Test
    public void testToStringCourt() {
        Surface surface = new Surface("Hard court", 4.30);
        Court court = new Court();
        court.setName("Australia");
        court.setSurface(surface);

        String expectedToString = "Court(id=null, name=Australia, surface=Surface(id=null, name=Hard court, rentalPrice=4.3))";
        assertEquals(expectedToString, court.toString());
    }

    @Test
    public void testCalculatePrice_Singles() {
        Surface surface = new Surface("Grass", 10.0);
        Court court = new Court();
        court.setId(1L);
        court.setName("Melbourne");
        court.setSurface(surface);
        User user = new User();
        user.setId(1L);
        user.setName("Robinko Veľký");
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 10, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 10, 12, 0);
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);
        reservation.calculatePrice();
        assertEquals(1200.0, reservation.getPrice());
    }

    @Test
    public void testCalculatePrice_Doubles() {
        Surface surface = new Surface("Clay", 10.0);
        Court court = new Court();
        court.setId(1L);
        court.setName("Clay Court");
        court.setSurface(surface);
        User user = new User();
        user.setId(1L);
        user.setName("Peter Škoda");
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 10, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 10, 12, 0);
        Reservation reservation = new Reservation(court, user, startTime, endTime, true);
        reservation.calculatePrice();
        assertEquals(1800.0, reservation.getPrice());
    }

    @Test
    public void testCalculatePrice_ZeroDuration() {
        Surface surface = new Surface("Grass", 10.0);
        Court court = new Court();
        court.setId(1L);
        court.setName("Court North");
        court.setSurface(surface);
        User user = new User();
        user.setId(1L);
        user.setName("Samo Jasný");
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 10, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 10, 10, 0); // Same start and end time
        Reservation reservation = new Reservation(court, user, startTime, endTime, false);
        reservation.calculatePrice();
        assertEquals(0.0, reservation.getPrice());
    }

    @Test
    public void testCalculatePrice_NullCourt() {
        User user = new User();
        user.setId(1L);
        user.setName("Jirka Žochár");
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 10, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 3, 10, 12, 0);
        assertThrows(NullPointerException.class, () -> new Reservation(null, user, startTime, endTime, false));
    }
}
