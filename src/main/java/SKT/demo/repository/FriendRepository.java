package SKT.demo.repository;

import SKT.demo.model.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFromNickname(String nickname);
    Optional<Friend> findByToNickname(String nickname);
}
