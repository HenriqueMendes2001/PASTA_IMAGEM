package com.temreserva.backend.temreserva_backend.data.repository;

import com.temreserva.backend.temreserva_backend.data.entity.RestaurantSegment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantSegmentRepository extends JpaRepository<RestaurantSegment, Long> {
    
}
