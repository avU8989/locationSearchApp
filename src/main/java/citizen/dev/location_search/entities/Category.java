package citizen.dev.location_search.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<PointOfInterest> pointsOfInterest;

    @OneToMany(mappedBy = "category")
    private List<NearbyService> nearbyServices;
}
