package SKT.demo.controller;

import SKT.demo.model.entity.Friend;
import SKT.demo.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/add")
    public ResponseEntity<Friend> requestFriend(HttpServletRequest request, @RequestParam @Valid String nickname) {
        return ResponseEntity.ok(friendService.requestFriend(request, nickname));
    }

    @DeleteMapping("/yes")
    public String acceptFriend(HttpServletRequest request, @RequestParam @Valid Long friendId) {
        return friendService.acceptFriend(request, friendId);
    }

    @DeleteMapping("/no")
    public String denyFriend(HttpServletRequest request, @RequestParam @Valid Long friendId) {
        return friendService.denyFriend(request, friendId);
    }
}
