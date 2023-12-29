package SKT.demo.service;

import SKT.demo.config.JwtTokenProvider;
import SKT.demo.exception.error.FriendException;
import SKT.demo.exception.error.UserException;
import SKT.demo.model.entity.Favorite;
import SKT.demo.model.entity.Friendship;
import SKT.demo.model.entity.User;
import SKT.demo.repository.FavoriteRepository;
import SKT.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static SKT.demo.exception.message.FriendErrorMessage.ALREADY_FAVORITE;
import static SKT.demo.exception.message.FriendErrorMessage.NOT_FRIEND;
import static SKT.demo.exception.message.UserErrorMessage.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Favorite addFavorite(HttpServletRequest request, String nickname) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = jwtTokenProvider.getPayload(token);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        User friend = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Friendship userFriends = user.getFriendship();

        boolean isFriend = false;
        for (User findUser : userFriends.getFriendList()) {
            if(findUser.getNickname().equals(friend.getNickname())) {
                isFriend = true;
                break;
            }
        }

        if(!isFriend) {
            throw new FriendException(NOT_FRIEND);
        }

        Favorite favorite = user.getFavorite();

        if(favorite.getFavoriteList().contains(friend)) { // 이미 추가된 유저면 즐겨찾기 해제로 로직 변경
            favorite.deleteFavorite(user);
            favoriteRepository.save(favorite);
        } else {
            favorite.addFavorite(friend);
            favoriteRepository.save(favorite);
        }
        return favorite;
    }

    public boolean checkFavorite(HttpServletRequest request, String nickname) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = jwtTokenProvider.getPayload(token);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        User friend = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Friendship userFriends = user.getFriendship();

        boolean isFriend = false;
        for (User findUser : userFriends.getFriendList()) {
            if(findUser.getNickname().equals(friend.getNickname())) {
                isFriend = true;
                break;
            }
        }

        if(!isFriend) {
            throw new FriendException(NOT_FRIEND);
        }

        Favorite favorite = user.getFavorite();

        if(favorite.getFavoriteList().contains(friend)) {
            return true;
        } else {
            return false;
        }
    }
}
