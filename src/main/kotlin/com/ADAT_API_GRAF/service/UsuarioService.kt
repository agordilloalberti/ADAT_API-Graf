package com.AGS.ADAT_API.Graf.service

import com.AGS.ADAT_API.Graf.dto.UsuarioDTO
import com.AGS.ADAT_API.Graf.dto.UsuarioRegisterDTO
import com.AGS.ADAT_API.Graf.error.exception.InvalidInputException
import com.AGS.ADAT_API.Graf.error.exception.NotFoundException
import com.AGS.ADAT_API.Graf.error.exception.UnauthorizedException
import com.AGS.ADAT_API.Graf.externalApi.ExternalApiService
import com.AGS.ADAT_API.Graf.model.Usuario
import com.AGS.ADAT_API.Graf.repository.UsuarioRepository
import com.AGS.ADAT_API.Graf.util.DTOMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var apiService: ExternalApiService

    override fun loadUserByUsername(username: String?): UserDetails {
        var usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
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
        var user : UsuarioDTO? = null

        val datosProvincias = apiService.obtenerDatosDesdeApi()

        var cpro = ""

        if (usuarioInsertadoDTO.username.isBlank()||
            usuarioInsertadoDTO.name.isBlank()||
            usuarioInsertadoDTO.surname.isBlank()||
            usuarioInsertadoDTO.password.isBlank()||
            usuarioInsertadoDTO.passwordRepeat.isBlank()||
            usuarioInsertadoDTO.direccion.isEmpty()){
            throw InvalidInputException("")
        }

        user = UsuarioDTO(
            usuarioInsertadoDTO.username,
            usuarioInsertadoDTO.name,
            usuarioInsertadoDTO.surname,
            passwordEncoder.encode(usuarioInsertadoDTO.password),
            usuarioInsertadoDTO.direccion)

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
                it.PRO == user.direccion.municipio
            }.findFirst().orElseThrow {
                NotFoundException("Municipio ${user.direccion.municipio.uppercase()} no valido")
            }
        }

        usuarioRepository.insert(DTOMapper.userDTOToEntity(usuarioInsertadoDTO))

        return user
    }
}