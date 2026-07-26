package com.example.nto.entity;

import com.example.nto.dto.PlaceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.nto.entity.Authority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookingList;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_authority",
            joinColumns =
            @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    Set<Authority> authorities;

    @Column(name = "failed_logins")
    Integer failedLogins;
}
