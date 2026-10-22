package com.krakedev.refugio_jwt.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.refugio_jwt.entidades.Usuario;
import com.krakedev.refugio_jwt.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    
    public Usuario guardar(Usuario usuario) {

        String contrasenaEncriptada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());

        usuario.setPassword(contrasenaEncriptada);

        return repository.save(usuario);

    }

    
    public boolean autenticar(String username, String password) {

        Optional<Usuario> usuarioEncontrado = repository.findByUsername(username);

        if (usuarioEncontrado.isPresent()) {
            Usuario usuario = usuarioEncontrado.get();

            try {

                if (BCrypt.checkpw(password, usuario.getPassword())) {
                    return true;
                }

            } catch (IllegalArgumentException e) {
            }
        }

        return false;
    }
}