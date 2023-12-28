package SKT.demo.controller;

import SKT.demo.model.entity.Favorite;
import SKT.demo.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<Favorite> addFavorite(HttpServletRequest request, @RequestParam @Valid String nickname) {
        return ResponseEntity.ok(favoriteService.addFavorite(request, nickname));
    }
}
