package impl.reservationsystemapp.Controllers;

/**
 * Data transfer object (DTO) representing the authentication response.
 * Contains fields for the authentication token and the authentication status.
 *
 * @author martin Bjaloň
 */
public record AuthResponseDto(String token, AuthStatus authStatus) {}
