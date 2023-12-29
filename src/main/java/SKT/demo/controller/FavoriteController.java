package SKT.demo.controller;

import SKT.demo.model.entity.Favorite;
import SKT.demo.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/friend")
    public ResponseEntity<Favorite> addFavorite(HttpServletRequest request, @RequestParam @Valid String nickname) {
        return ResponseEntity.ok(favoriteService.addFavorite(request, nickname));
    }

    @GetMapping("/check")
    public boolean checkFavorite(HttpServletRequest request, @RequestParam @Valid String nickname) {
        return favoriteService.checkFavorite(request, nickname);
    }
}
