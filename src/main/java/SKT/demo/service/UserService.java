package SKT.demo.service;

import SKT.demo.config.JwtTokenProvider;
import SKT.demo.exception.error.UserException;
import SKT.demo.model.dto.SignInDto;
import SKT.demo.model.dto.SignUpDto;
import SKT.demo.model.entity.Friendship;
import SKT.demo.model.entity.User;
import SKT.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static SKT.demo.exception.message.UserErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User signUp (SignUpDto signUpDto) {
        if (userRepository.findByUserId(signUpDto.getUserId()).isPresent()) {
            throw new UserException(ALREADY_REGISTERED);
        }

        if (userRepository.findByNickname(signUpDto.getNickname()).isPresent()) {
            throw new UserException(DUPLICATED_NICKNAME);
        }

        Friendship friendship = Friendship.builder()
                .userId(signUpDto.getUserId())
                .friendList(new ArrayList<User>())
                .build();

        User user = User.builder()
                .userId(signUpDto.getUserId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .friendship(friendship)
                .build();

        userRepository.save(user);

        return user;
    }

    public String signIn (SignInDto signInDto) {
        User user = userRepository.findByUserId(signInDto.getUserId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new UserException(WRONG_PASSWORD);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getUserId(), signInDto.getPassword()));

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
//TODO 쿠키 추가시 적용
//      String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        return accessToken;
    }
}
