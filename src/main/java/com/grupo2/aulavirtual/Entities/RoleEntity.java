package com.grupo2.aulavirtual.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo2.aulavirtual.Enum.Role;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idRole;
    @Basic
    @Enumerated(EnumType.STRING) 
    Role role;
    

    @OneToMany(
    		cascade = CascadeType.ALL,
    		fetch = FetchType.EAGER)
    @JsonIgnore
    private List<UserEntity> user; 

    
}
