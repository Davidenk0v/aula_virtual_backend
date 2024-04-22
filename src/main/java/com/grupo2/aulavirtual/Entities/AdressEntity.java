package com.grupo2.aulavirtual.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AdressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdress;
    @Basic 
    @Column(nullable = false)
    private String country;

    @Basic 
    @Column(nullable = false)
    private String number;

    @Basic 
    @Column(nullable = false)
    private String street;

    @Basic 
    @Column(nullable = false)
    private String city;

    @Basic 
    @Column(nullable = false)
    private String postalCode;

    @OneToOne(
		mappedBy = "adress"
	)
    @JsonIgnore
	private UserEntity user;

}
