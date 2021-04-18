package com.temreserva.backend.temreserva_backend.web.controllers;

import java.util.List;

import javax.validation.Valid;

import com.temreserva.backend.temreserva_backend.business.CredentialBusiness;
import com.temreserva.backend.temreserva_backend.business.RestaurantBusiness;
import com.temreserva.backend.temreserva_backend.data.entity.Restaurant;
import com.temreserva.backend.temreserva_backend.data.repository.ICredentialRepository;
import com.temreserva.backend.temreserva_backend.data.repository.IRestaurantRepository;
import com.temreserva.backend.temreserva_backend.web.model.RestaurantDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantBusiness business;

    public RestaurantController(IRestaurantRepository restaurantRepository, ICredentialRepository credentialRepository) {
        business = new RestaurantBusiness(restaurantRepository, new CredentialBusiness(credentialRepository));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createNewRestaurant(@RequestBody @Valid RestaurantDTO dto) {
        return business.createNewRestaurant(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant restaurantLogin(@RequestHeader("Authorization") String authorization, @RequestHeader("Content-Type") String contentType, @RequestBody String parameters) {
        return business.restaurantLogin(parameters, authorization, contentType);
    }

    @GetMapping("/id={id}")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return business.getRestaurantById(id);
    }

    @PutMapping("/id={id}&idCredential={idCredential}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRestaurant(@PathVariable Long id, @PathVariable Long idUser, @RequestBody Restaurant restaurant) {
        business.updateRestaurant(id, idUser, restaurant);
    }

    @GetMapping("/name={name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> getRestaurantByName(@PathVariable String name) {
        return business.getRestaurantByName(name);
    }
}
