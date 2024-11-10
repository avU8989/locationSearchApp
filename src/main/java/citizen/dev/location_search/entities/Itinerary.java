package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

//represents a travel plan for user, consisting of multiple points of interest
@Entity
@Table(name = "itneraries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "itnerary_poi",
            joinColumns = @JoinColumn(name = "itnerary_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id"))
    private List<PointOfInterest> pointOfInterests;
}
