package SKT.demo.controller;

import SKT.demo.model.dto.SignInDto;
import SKT.demo.model.dto.SignUpDto;
import SKT.demo.model.entity.User;
import SKT.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody @Valid SignUpDto dto) {
        return ResponseEntity.ok(userService.signUp(dto));
    }

    @GetMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInDto dto) {
        return ResponseEntity.ok(userService.signIn(dto));
    }
}
