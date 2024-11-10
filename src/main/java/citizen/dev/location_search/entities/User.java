package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column
    private LocalDate birthday;

    @OneToOne
    private UserPreferences userPreferences;

    @OneToMany(mappedBy = "user")
    private List<Itinerary> itineraryList;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
