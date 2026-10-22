package com.krakedev.refugio_jwt.services;

import java.util.Optional;

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

        return repository.save(usuario);

    }


    public boolean autenticar(String username, String password) {

        Optional<Usuario> usuarioEncontrado = repository.findByUsername(username);

        if (usuarioEncontrado.isPresent()) {
            Usuario usuario = usuarioEncontrado.get();

            if (usuario.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }
}