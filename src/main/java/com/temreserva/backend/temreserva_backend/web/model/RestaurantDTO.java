package com.temreserva.backend.temreserva_backend.web.model;

import java.sql.Time;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantDTO {    
    @NotNull(message = "Senha não pode ser nulo")
    @NotEmpty(message = "Senha não pode ser vazio")
    private String password;

    @Email
    @NotEmpty(message = "E-mail não pode ser nulo")
    @NotEmpty(message = "E-mail não pode ser vazio")
    private String email;

    @CNPJ(message = "Insira um CNPJ válido")
    @NotNull(message = "CNPJ não pode ser nulo")
    @NotEmpty(message = "CNPJ não pode ser vazio")
    private String cnpj;

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @NotNull(message = "Descrição não pode ser nula")
    @NotEmpty(message = "Descrição não pode ser vazia")
    private String description;

    @NotNull(message = "Dias abertos não pode ser nulo")
    @NotEmpty(message = "Dias abertos não pode ser vazia")
    private String openDaysOfWeek;

    @NotNull(message = "O horário de abertura não pode ser nulo")
    private Time openingTime;

    @NotNull(message = "O horário de fechamento não pode ser nulo")
    private Time closingTime;

    private Integer spacingOfTables;

    private Boolean handicappedAdapted;
    
    private Integer cleaningPeriodicity;

    private Integer maxNumberOfPeople;

    private Integer actualNumberOfPeople;

    private Integer averageStars;

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;
}
