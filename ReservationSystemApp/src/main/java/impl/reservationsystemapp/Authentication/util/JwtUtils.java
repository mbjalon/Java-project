package impl.reservationsystemapp.Authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility class for working with JSON Web Tokens (JWTs) in the authentication system.
 *
 * @author Martin Bjaloň
 */

@Slf4j
public class JwtUtils {
    private JwtUtils() {}
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final String ISSUER = "haha";
    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    private static Optional<Claims> parseToken(String jwtToken) {
        var jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken).getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT Exception occured");
        }

        return Optional.empty();

    }

    public static Optional <String> getUsarnameFromToken(String jwtToken) {
        var claimsOptional = parseToken(jwtToken);

        return claimsOptional.map(Claims::getSubject);

    }

    public static String generateToken(String username) {
        var currentDate = new Date();
        var expiration = DateUtils.addMinutes(currentDate, 10);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(secretKey, SignatureAlgorithm.HS256) // Specify the signature algorithm
                .issuedAt(currentDate)
                .expiration(expiration)
                .compact();
    }
}
