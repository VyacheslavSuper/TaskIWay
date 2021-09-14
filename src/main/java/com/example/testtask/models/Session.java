package com.example.testtask.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @Column(name = "cookie", nullable = false, unique = true)
    private String cookie;

    public Session(final User user, final String cookie) {
        this.user = user;
        this.cookie = cookie;
    }

}
