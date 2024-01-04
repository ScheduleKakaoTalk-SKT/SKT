package SKT.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
    private Long id;
    private String userId;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> favoriteList;

    public void addFavorite(User user) {
        this.favoriteList.add(user);
    }

    public void deleteFavorite(User user) {
        this.favoriteList.remove(user);
    }
}
