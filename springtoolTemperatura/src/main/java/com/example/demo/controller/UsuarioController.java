package com.example.demo.controller;

import com.example.demo.bean.Configuracion;
import com.example.demo.bean.Usuario;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ConfiguracionRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioService;

    private final ConfiguracionRepository configuracionRepository;

    public UsuarioController(ConfiguracionRepository configuracionRepository, UsuarioRepository usuarioService) {
        this.configuracionRepository = configuracionRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        // Guarda el usuario en la base de datos
        Usuario savedUsuario = usuarioService.save(usuario);

        // Crea una nueva configuración y la asocia con el usuario
        Configuracion configuracion = new Configuracion();
        configuracion.setUsuario(savedUsuario);
        configuracionRepository.save(configuracion);

        // Devuelve el usuario guardado
        return savedUsuario;
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        // Aquí puedes actualizar los campos del usuario
        usuario.setUsername(usuarioDetails.getUsername());
        usuario.setPassword(usuarioDetails.getPassword());
        // Asegúrate de actualizar todos los campos que quieras cambiar

        return usuarioService.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuarioByIdUser(id);
    }
}