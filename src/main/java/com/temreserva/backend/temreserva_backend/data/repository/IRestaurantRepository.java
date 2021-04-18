package com.temreserva.backend.temreserva_backend.data.repository;

import java.util.List;

import com.temreserva.backend.temreserva_backend.data.entity.Credential;
import com.temreserva.backend.temreserva_backend.data.entity.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT * FROM TB_RESTAURANTE R WHERE R.NOME LIKE %:name%", nativeQuery = true)
    public List<Restaurant> findByName(@Param("name") String name);

    public Restaurant findByCredential(Credential restaurantCredentials);
}
