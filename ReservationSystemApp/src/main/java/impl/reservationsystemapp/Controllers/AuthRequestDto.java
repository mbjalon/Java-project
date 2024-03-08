package impl.reservationsystemapp.Controllers;

/**
 * Data transfer object (DTO) representing the authentication request.
 * Contains fields for user authentication information such as phone number, name, username, and password.
 *
 * @author Martin Bjaloň
 */
public record AuthRequestDto(String phoneNumber, String name, String username, String password) {
}
