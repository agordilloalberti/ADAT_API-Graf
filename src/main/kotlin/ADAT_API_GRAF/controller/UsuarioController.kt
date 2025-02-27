package ADAT_API_GRAF.controller

import ADAT_API_GRAF.dto.LoginUsuarioDTO
import ADAT_API_GRAF.dto.UsuarioDTO
import ADAT_API_GRAF.dto.UsuarioRegisterDTO
import ADAT_API_GRAF.error.exception.UnauthorizedException
import ADAT_API_GRAF.service.TokenService
import ADAT_API_GRAF.service.UsuarioService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/Usuarios")
class UsuarioController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var tokenService: TokenService
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @PostMapping("/register")
    fun insert(
        httpRequest: HttpServletRequest,
        @RequestBody usuarioRegisterDTO: UsuarioRegisterDTO
    ) : ResponseEntity<UsuarioDTO>?{

        if (usuarioRegisterDTO.password != usuarioRegisterDTO.passwordRepeat){
            throw UnauthorizedException("Las contraseñas no coinciden")
        }

        val user = usuarioService.insertUser(usuarioRegisterDTO)

        return ResponseEntity(user, HttpStatus.CREATED)

    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: LoginUsuarioDTO) : ResponseEntity<Any>? {

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            throw UnauthorizedException("Credenciales incorrectas")
        }

        var token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }
}