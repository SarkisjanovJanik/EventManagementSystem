package sia.eventmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "email",nullable = false)
    private String email;

    @ManyToMany(mappedBy = "attendees")
    private Set<Event> attendedEvents = new HashSet<>();

    @OneToMany(mappedBy = "organizer")
    private Set<Event> createdEvents = new HashSet<>();


}

