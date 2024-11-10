package citizen.dev.location_search.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String history;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "location")
    private List<Photo> photos;

    @OneToMany(mappedBy = "location")
    private List<NearbyService> nearbyServices;

    @OneToMany(mappedBy = "location")
    private List<PointOfInterest> pointsOfInterest;
}
