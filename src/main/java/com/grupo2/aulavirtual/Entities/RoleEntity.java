package com.grupo2.aulavirtual.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo2.aulavirtual.Enum.RoleEnum;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( uniqueConstraints = { @UniqueConstraint(columnNames = { "role" }) })
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @Basic
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<UserEntity> user;

}
