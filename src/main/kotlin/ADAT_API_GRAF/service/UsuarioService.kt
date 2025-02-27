package ADAT_API_GRAF.service

import ADAT_API_GRAF.dto.UsuarioDTO
import ADAT_API_GRAF.dto.UsuarioRegisterDTO
import ADAT_API_GRAF.error.exception.InvalidInputException
import ADAT_API_GRAF.error.exception.NotFoundException
import ADAT_API_GRAF.error.exception.UnauthorizedException
import ADAT_API_GRAF.externalApi.ExternalApiService
import ADAT_API_GRAF.model.Usuario
import ADAT_API_GRAF.repository.UsuarioRepository
import ADAT_API_GRAF.util.DTOMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var apiService: ExternalApiService

    override fun loadUserByUsername(username: String): UserDetails {
        var usuario: Usuario = usuarioRepository
            .findByUsername(username)
            .orElseThrow {
                UnauthorizedException("$username no existente")
            }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun insertUser(usuarioInsertadoDTO: UsuarioRegisterDTO): UsuarioDTO? {
        val userDTB = usuarioRepository.findByUsername(usuarioInsertadoDTO.username).getOrNull()

        if (userDTB!=null){
            throw InvalidInputException("El usuario ${usuarioInsertadoDTO.username} ya existe")
        }

        var user : UsuarioDTO? = null

        val datosProvincias = apiService.obtenerDatosDesdeApi()

        var cpro = ""

        if (usuarioInsertadoDTO.username.isBlank()||
            usuarioInsertadoDTO.name.isBlank()||
            usuarioInsertadoDTO.surname.isBlank()||
            usuarioInsertadoDTO.password.isBlank()||
            usuarioInsertadoDTO.passwordRepeat.isBlank()||
            usuarioInsertadoDTO.direccion.isEmpty()){
            throw InvalidInputException("Ningun campo puede estar vacio")
        }

        user = UsuarioDTO(
            usuarioInsertadoDTO.username,
            passwordEncoder.encode(usuarioInsertadoDTO.password),
            usuarioInsertadoDTO.name,
            usuarioInsertadoDTO.surname,
            usuarioInsertadoDTO.direccion,
            usuarioInsertadoDTO.rol)

        if (datosProvincias?.data != null){
            val dato = datosProvincias.data.stream().filter {
                it.PRO == user.direccion.provincia.uppercase()
            }.findFirst().orElseThrow {
                NotFoundException("Provincia ${user.direccion.provincia.uppercase()} no válida")
            }
            cpro = dato.CPRO
        }

        val datosMunucipios = apiService.obtenerMunicipios(cpro)

        if (datosMunucipios?.data != null){
            datosMunucipios.data.stream().filter {
                it.DMUN50 == user.direccion.municipio
            }.findFirst().orElseThrow {
                NotFoundException("Municipio ${user.direccion.municipio.uppercase()} no valido")
            }
        }

        usuarioRepository.insert(DTOMapper.userDTOToEntity(user))

        return user
    }
}