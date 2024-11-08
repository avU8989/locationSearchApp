package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


//represents palces of interest at a location, such as historical landmarks, museums, or parks
@Entity
@Table(name = "point_of_interests")
@NoArgsConstructor
@AllArgsConstructor
public class PointOfInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private String address;
    private String openingHours;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "pointOfInterest")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // Link to Category

    @OneToMany(mappedBy = "pointOfInterest")
    private List<NearbyService> nearbyServices; // Services around the POI

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location; // The location this POI belongs to

    @OneToMany(mappedBy = "pointOfInterest")
    private List<Photo> photos; // Photos associated with this POI


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
