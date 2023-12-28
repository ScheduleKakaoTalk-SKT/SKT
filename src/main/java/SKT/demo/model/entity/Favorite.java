package SKT.demo.model.entity;

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
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> favoriteList;

    public void addFriend(User user) {
        this.favoriteList.add(user);
    }
}
