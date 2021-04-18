package com.temreserva.backend.temreserva_backend.data.repository;

import com.temreserva.backend.temreserva_backend.data.entity.Segment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISegmentRepository extends JpaRepository<Segment, Long> {
    
}
