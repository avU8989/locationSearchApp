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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column
    private LocalDate birthday;

    @OneToOne
    private UserPreferences userPreferences;

    @OneToMany(mappedBy = "account")
    private List<Itinerary> itineraryList;

    @OneToMany(mappedBy = "account")
    private List<Review> reviews;

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
