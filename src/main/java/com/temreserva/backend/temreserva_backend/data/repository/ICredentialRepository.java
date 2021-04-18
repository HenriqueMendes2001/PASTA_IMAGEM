package com.temreserva.backend.temreserva_backend.data.repository;

import java.util.Optional;

import com.temreserva.backend.temreserva_backend.data.entity.Credential;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICredentialRepository extends JpaRepository<Credential, Long> {
    public Optional<Credential> findByEmail(String email);
}
