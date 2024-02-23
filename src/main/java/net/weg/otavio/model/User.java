package net.weg.otavio.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetailsAuth userDetailsAuth;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private File photo;
}
