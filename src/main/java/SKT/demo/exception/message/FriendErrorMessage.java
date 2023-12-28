package SKT.demo.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FriendErrorMessage {
    ALREADY_FRIEND(HttpStatus.CONFLICT, "이미 친구인 유저입니다."),
    DUPLICATED_REQUEST(HttpStatus.BAD_REQUEST, "등록된 친구 요청입니다."),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, "일치하지 않는 유저입니다."),
    REQUEST_NOT_FOUND(HttpStatus.BAD_REQUEST, "찾을 수 없는 요청입니다."),
    NOT_FRIEND(HttpStatus.BAD_REQUEST, "친구가 아닙니다."),
    ALREADY_FAVORITE(HttpStatus.CONFLICT, "이미 즐겨찾기 유저입니다"),
    ;

    private final HttpStatus status;
    private final String errorMessage;

    FriendErrorMessage(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
