package ADAT_API_GRAF.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.Date

@Service
class TokenService {

    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    fun generarToken(authentication: Authentication) : String {

        val principalName = when (authentication) {
            is JwtAuthenticationToken -> authentication.token.claims["sub"] as String? ?: "unknown"
            else -> authentication.name ?: "unknown"
        }

        val roles: String = authentication.authorities.joinToString(" ") { it.authority } // Contiene los roles del usuario

        val payload: JwtClaimsSet = JwtClaimsSet.builder()
            .subject(principalName)
            .issuer("self")
            .issuedAt(Instant.now())
            .expiresAt(Date().toInstant().plus(Duration.ofHours(1)))
            .claim("roles", roles)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(payload)).tokenValue;
    }
}