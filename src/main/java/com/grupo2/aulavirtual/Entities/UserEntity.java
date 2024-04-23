package com.grupo2.aulavirtual.Entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;
    @Basic
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String lastname;
    @Column(nullable = false)
    String firstname;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress", referencedColumnName = "idAdress")
    AdressEntity address;

    @ManyToMany
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
