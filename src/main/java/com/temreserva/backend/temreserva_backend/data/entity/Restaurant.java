package com.temreserva.backend.temreserva_backend.data.entity;

import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.temreserva.backend.temreserva_backend.web.model.RestaurantDTO;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_RESTAURANTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESTAURANTE")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_CREDENCIAL",nullable = false, updatable = false)
    private Credential credential;

    @Column(name = "CNPJ", nullable = false, length = 14, updatable = false)
    @NotEmpty(message = "CNPJ não pode ser vazio")
    @CNPJ(message = "Insira um CNPJ válido")
    private String cnpj;

    @Column(name = "NOME", nullable = false, length = 1000, updatable = false)
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @Column(name = "DESCRICAO", nullable = false, length = 1000, updatable = false)
    @NotEmpty(message = "Descrição não pode ser vazia")
    private String description;

    @Column(name = "DIAS_SEMANAIS_ABERTOS", nullable = false, length = 500, updatable = true)
    @NotEmpty(message = "Dias semanais não pode ser nulo")
    private String openDaysOfWeek;

    @Column(name = "HORA_INICIO", nullable = false, updatable = true)
    @JsonFormat(pattern = "hh:mm:ss")
    @NotNull(message = "O horário de abertura não pode ser vazio")
    private Time openingTime;

    @Column(name = "HORA_FIM", nullable = false, updatable = true)
    @JsonFormat(pattern = "hh:mm:ss")
    @NotNull(message = "O horário de fechamento não pode ser vazio")
    private Time closingTime;

    @Column(name = "DISTANCIAMENTO_MESAS", nullable = false, updatable = true)
    @NotNull(message = "A sinalização de espaçamento entre as mesas não pode ser nula")
    private Integer spacingOfTables;

    @Column(name = "ADAPTACAO_DEFICIENTE", nullable = false, updatable = true)
    private Boolean handicappedAdapted;

    @Column(name = "PERIODICIDADE_HIGIENIZACAO", nullable = false, updatable = true)
    private Integer cleaningPeriodicity;

    @Column(name = "QTD_PESSOAS_MAXIMA", nullable = false, updatable = true)
    @NotNull(message = "A quantidade de pessoas máxima não pode ser nula")
    private Integer maxNumberOfPeople;

    @Column(name = "QTD_PESSOAS_ATUAL", nullable = false, updatable = true)
    private Integer actualNumberOfPeople;

    @Column(name = "NOTA_AVALIACAO_MEDIA", nullable = false, updatable = true)
    private Integer averageStars;

    public String accessToken;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime registerDate;

    @Column(name = "DATA_ATUALIZACAO", nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        setHandicappedAdapted(false);
        setActualNumberOfPeople(0);
        setAverageStars(0);
        setRegisterDate(LocalDateTime.now());
        setUpdateDate(LocalDateTime.now());
    }

    public Restaurant(RestaurantDTO dto) {
        cnpj = dto.getCnpj();
        name = dto.getName();
        description = dto.getDescription();
        openDaysOfWeek = dto.getOpenDaysOfWeek();
        openingTime = dto.getOpeningTime();
        closingTime = dto.getClosingTime();
        spacingOfTables = dto.getSpacingOfTables();
        cleaningPeriodicity = dto.getCleaningPeriodicity();   
        maxNumberOfPeople = dto.getMaxNumberOfPeople(); 
        handicappedAdapted = false;
        actualNumberOfPeople = 0;
        averageStars = 0;
        registerDate = LocalDateTime.now();
        updateDate = LocalDateTime.now(); 
    }
}
