package com.grupo2.aulavirtual.entities;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "user_img")
public class UserImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idUser;

    private String urlImg;
}
