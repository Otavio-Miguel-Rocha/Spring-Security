package net.weg.otavio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserDetailsAuth implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NonNull
    @JsonIgnore
    private final User user;

    @Email
    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Column(nullable = false)
    @Length(min = 6, max = 14)
    private String password;

    private boolean enabled;

    @OneToMany(mappedBy = "userDetailsAuth")
    private List<Authorization> authorities;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @Override
    public String getUsername() {
        return this.email;
    }
}
