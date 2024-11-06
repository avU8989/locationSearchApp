package citizen.dev.location_search.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String preferredLanguage;
    private boolean prefersNotification;
    private String preferredTheme;
    @ElementCollection
    private List<String> favoriteCategories;
    //newNearbyBars = True, newNearbyStores = true
    @ElementCollection
    private Map<String, Boolean> notificationSettings;

    @OneToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public boolean isPrefersNotification() {
        return prefersNotification;
    }

    public void setPrefersNotification(boolean prefersNotification) {
        this.prefersNotification = prefersNotification;
    }

    public String getPreferredTheme() {
        return preferredTheme;
    }

    public void setPreferredTheme(String preferredTheme) {
        this.preferredTheme = preferredTheme;
    }

    public List<String> getFavoriteCategories() {
        return favoriteCategories;
    }

    public void setFavoriteCategories(List<String> favoriteCategories) {
        this.favoriteCategories = favoriteCategories;
    }

    public Map<String, Boolean> getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(Map<String, Boolean> notificationSettings) {
        this.notificationSettings = notificationSettings;
    }
}
