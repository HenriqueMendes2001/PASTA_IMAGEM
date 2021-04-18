package com.temreserva.backend.temreserva_backend.business;

import java.time.LocalDateTime;
import java.util.List;

import com.temreserva.backend.temreserva_backend.data.entity.*;
import com.temreserva.backend.temreserva_backend.data.repository.IRestaurantRepository;
import com.temreserva.backend.temreserva_backend.web.model.RestaurantDTO;
import com.temreserva.backend.temreserva_backend.web.utils.Enumerators;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RestaurantBusiness {
    private final IRestaurantRepository restaurantRepository;
    private final CredentialBusiness credentialBusiness;
    private final OAuthBusiness oauthBusiness;

    @Autowired
    public RestaurantBusiness(IRestaurantRepository restaurantRepository, CredentialBusiness credentialBusiness) {
        this.restaurantRepository = restaurantRepository;
        this.credentialBusiness = credentialBusiness;
        this.oauthBusiness = new OAuthBusiness();
    }

    public Restaurant createNewRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = validateNewRestaurantDto(dto);
        restaurant.setCredential(credentialBusiness.createNewCredential(dto.getEmail(), dto.getPassword()));
        if (restaurant.getCredential() != null) {
            restaurantRepository.save(restaurant);
            restaurant.setAccessToken(oauthBusiness.getAcessToken(dto.getEmail(), dto.getPassword()));
            restaurant.getCredential().setPassword(null);
            return restaurant;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                Enumerators.apiExceptionCodeEnum.CREATE_CREDENTIAL_ERROR.getEnumValue());
    }

    public Restaurant restaurantLogin(String parameters, String authorization, String contentType) {
        String username = parameters.substring(parameters.indexOf("=") + 1, parameters.indexOf("&")).replace("%40",
                "@");
        String password = parameters.substring(parameters.indexOf("&") + 10, parameters.indexOf("&grant_type"));
        String accessToken = oauthBusiness.getAcessToken(username, password, authorization, contentType);

        if (accessToken != null) {
            Credential restaurantCredentials = credentialBusiness.getCredentialByEmail(username);
            Restaurant restaurant = restaurantRepository.findByCredential(restaurantCredentials);
            restaurant.setAccessToken(accessToken);
            restaurant.getCredential().setPassword(null);
            return restaurant;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                Enumerators.apiExceptionCodeEnum.USERNAME_OR_PASSWORD_INVALID.getEnumValue());
    }

    public Restaurant getRestaurantById(long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                Enumerators.apiExceptionCodeEnum.RESTAURANT_NOT_FOUND.getEnumValue()));
    }

    public List<Restaurant> getRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public void updateRestaurant(Long id, Long idCredential, Restaurant restaurant) {
        restaurantRepository.findById(id).map(r -> {
            r.setOpenDaysOfWeek(restaurant.getOpenDaysOfWeek());
            r.setOpeningTime(restaurant.getOpeningTime());
            r.setClosingTime(restaurant.getClosingTime());
            r.setSpacingOfTables(restaurant.getSpacingOfTables());
            r.setHandicappedAdapted(restaurant.getHandicappedAdapted());
            r.setCleaningPeriodicity(restaurant.getCleaningPeriodicity());
            r.setMaxNumberOfPeople(restaurant.getMaxNumberOfPeople());
            r.setActualNumberOfPeople(restaurant.getMaxNumberOfPeople());
            r.setAverageStars(restaurant.getAverageStars());
            r.setUpdateDate(LocalDateTime.now());
            if (r.getCredential().getId() == idCredential)
                return restaurantRepository.save(r);
            else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        Enumerators.apiExceptionCodeEnum.UPDATE_RESTAURANT_UNAUTHORIZED.getEnumValue());
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                Enumerators.apiExceptionCodeEnum.RESTAURANT_NOT_FOUND.getEnumValue()));
    }

    private Restaurant validateNewRestaurantDto(RestaurantDTO dto) {
        if (credentialBusiness.validateNewCredential(dto.getEmail(), dto.getPassword()))
            return new Restaurant(dto);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    Enumerators.apiExceptionCodeEnum.CREATE_EXISTING_USER.getEnumValue());
    }
}
