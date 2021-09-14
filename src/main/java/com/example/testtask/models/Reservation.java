package com.example.testtask.models;

import com.example.testtask.models.base.StatusReservation;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusReservation status;

    @Column(name = "from_where")
    private String fromWhere;

    @ElementCollection
    @CollectionTable(name = "reservation_to_where", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "to_where")
    private List<String> toWhereList;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User user;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
