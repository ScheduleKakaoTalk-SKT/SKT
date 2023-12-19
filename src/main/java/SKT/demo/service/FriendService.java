package SKT.demo.service;

import SKT.demo.config.JwtTokenProvider;
import SKT.demo.exception.error.FriendException;
import SKT.demo.exception.error.UserException;
import SKT.demo.model.entity.Friend;
import SKT.demo.model.entity.Friendship;
import SKT.demo.model.entity.User;
import SKT.demo.repository.FriendRepository;
import SKT.demo.repository.FriendshipRepository;
import SKT.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SKT.demo.exception.message.FriendErrorMessage.*;
import static SKT.demo.exception.message.UserErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendshipRepository friendshipRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Friend requestFriend (HttpServletRequest request, String nickname) { // 친구 추가 신청
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = jwtTokenProvider.getPayload(token);

        User fromUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        User toUser = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if(friendRepository.findByFromNickname(fromUser.getNickname()).isPresent()) {
            throw new FriendException(DUPLICATED_REQUEST);
        }

        if(friendRepository.findByToNickname(toUser.getNickname()).isPresent()) {
            throw new FriendException(DUPLICATED_REQUEST);
        }

        Friend friend = Friend.builder()
                .fromNickname(fromUser.getNickname())
                .toNickname(toUser.getNickname())
                .build();

        friendRepository.save(friend);

        return friend;
    }

    public String acceptFriend(HttpServletRequest request, Long friendId) { // 친구 요청 수락
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = jwtTokenProvider.getPayload(token);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new FriendException(REQUEST_NOT_FOUND));

        if(!friend.getToNickname().equals(user.getNickname())) {
            throw new FriendException(FORBIDDEN_REQUEST);
        }

        User fromUser = userRepository.findByNickname(friend.getFromNickname())
                        .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Friendship toUserFriendship = user.getFriendship();
        Friendship fromUserFriendship = fromUser.getFriendship();
        toUserFriendship.addFriend(fromUser);
        fromUserFriendship.addFriend(user);

        friendshipRepository.save(toUserFriendship);
        friendshipRepository.save(fromUserFriendship);

        friendRepository.delete(friend);

        return "친구 추가 완료";
    }

    public String denyFriend(HttpServletRequest request, Long friendId) { // 친구 요청 거절
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = jwtTokenProvider.getPayload(token);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new FriendException(REQUEST_NOT_FOUND));

        if(!friend.getToNickname().equals(user.getNickname())) {
            throw new FriendException(FORBIDDEN_REQUEST);
        }

        if (userRepository.findByNickname(friend.getFromNickname()).isEmpty()) {
            throw new UserException(USER_NOT_FOUND);
        }

        friendRepository.delete(friend);

        return "친구 요청 거절";
    }
}
