package com.example.livescore.models;

import com.example.livescore.web.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "block_flag")
    private Boolean blockFlag;

    public User toDto() {
        return new User(
                this.userId,
                this.username,
                this.userPassword,
                this.userRole,
                this.blockFlag
        );
    }
}
