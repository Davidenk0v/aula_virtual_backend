package com.grupo2.aulavirtual.entities;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userEntity", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Basic
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private String urlImg;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress", referencedColumnName = "idAdress")
    AdressEntity adress;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"), inverseJoinColumns = @JoinColumn(name = "id_course", referencedColumnName = "idCourse"))
    private List<CourseEntity> courses;

    @ManyToOne
    @JoinColumn(name = "idRole", referencedColumnName = "idRole")
    @JsonIgnore
    private RoleEntity role;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole().toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
