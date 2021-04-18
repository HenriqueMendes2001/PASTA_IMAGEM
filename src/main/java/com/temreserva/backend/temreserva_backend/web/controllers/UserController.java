package com.temreserva.backend.temreserva_backend.web.controllers;

import javax.validation.Valid;

import com.temreserva.backend.temreserva_backend.business.CredentialBusiness;
import com.temreserva.backend.temreserva_backend.business.UserBusiness;
import com.temreserva.backend.temreserva_backend.data.entity.User;
import com.temreserva.backend.temreserva_backend.data.repository.ICredentialRepository;
import com.temreserva.backend.temreserva_backend.data.repository.IUserRepository;
import com.temreserva.backend.temreserva_backend.web.model.UserDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/user")
public class UserController {
    public final UserBusiness business;

    public UserController(IUserRepository userRepository,ICredentialRepository credentialRepository) {
        business = new UserBusiness(userRepository, new CredentialBusiness(credentialRepository));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User createNewUser(@RequestBody @Valid UserDTO dto) {
        User user = business.createNewUser(dto);
        return user;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User userLogin(@RequestHeader("Authorization") String authorization, @RequestHeader("Content-Type") String contentType, @RequestBody String parameters) {
        return business.userLogin(parameters, authorization, contentType);
    }
}
