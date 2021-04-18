package com.temreserva.backend.temreserva_backend.business;

import javax.validation.Valid;

import com.temreserva.backend.temreserva_backend.data.entity.Reserve;
import com.temreserva.backend.temreserva_backend.data.entity.Restaurant;
import com.temreserva.backend.temreserva_backend.data.entity.User;
import com.temreserva.backend.temreserva_backend.data.repository.IReserveRepository;
import com.temreserva.backend.temreserva_backend.data.repository.IRestaurantRepository;
import com.temreserva.backend.temreserva_backend.data.repository.IUserRepository;
import com.temreserva.backend.temreserva_backend.web.model.ReserveDTO;
import com.temreserva.backend.temreserva_backend.web.utils.Enumerators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReserveBusiness {
    private final IReserveRepository reserveRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ReserveBusiness(IReserveRepository reserveRepository, IRestaurantRepository restaurantRepository,
            IUserRepository userRepository) {
        this.reserveRepository = reserveRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Reserve createNewReserve(@Valid ReserveDTO dto) {
        Reserve reserve = validateNewReserve(dto);

        if(reserve != null) 
            return reserveRepository.save(reserve);        

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                Enumerators.apiExceptionCodeEnum.BAD_RESERVE.getEnumValue());
    }

    public void deleteReserve(Long id) {
        reserveRepository.deleteById(id);
    }

    private Reserve validateNewReserve(ReserveDTO dto) {
        // Validar limite de pessoas do restaurante (Definir como será o limite, se será
        // por hora)
        // Validar se o usuário possui reserva no mesmo horário em outro restaurante
        User user = userRepository.findById(dto.getIdUser()).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(dto.getIdRestaurant()).orElse(null);
        if(user != null && restaurant != null) {
            return Reserve.builder()
            .user(user)
            .restaurant(restaurant)
            .reserveDate(dto.getReserveDate())
            .build();
        }
        
        return null;
    }
}
