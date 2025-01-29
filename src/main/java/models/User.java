package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "tbUsers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "nome")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "idade")
    private int age;

    @Column(name = "altura")
    private BigDecimal height;

    public User(Long userId, String name, String email, BigDecimal height) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.height = height;
    }
}
