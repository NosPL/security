package com.example.security.users;

import com.example.security.commons.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_DETAILS")
public class User extends BaseEntity implements UserDetails {
    @NotBlank
    @Length(min = 2, max = 20)
    private String firstName;
    @NotBlank
    @Length(min = 2, max = 20)
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;
    @Length(min = 6, max = 80)
    private String password;
    @ElementCollection(fetch = EAGER, targetClass = UserAuthority.class)
    @NotNull
    @Enumerated(STRING)
    @Size(min = 1)
    @UniqueElements
    private List<UserAuthority> authorities;

    @Override
    public List<UserAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
