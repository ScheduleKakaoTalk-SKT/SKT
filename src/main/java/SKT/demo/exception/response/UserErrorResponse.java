package SKT.demo.exception.response;

import SKT.demo.exception.message.UserErrorMessage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class UserErrorResponse {
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<UserErrorResponse> toResponseEntity(UserErrorMessage errorMessage) {
        return ResponseEntity.status(errorMessage.getStatus())
                .body(UserErrorResponse.builder()
                        .status(errorMessage.getStatus().value())
                        .error(errorMessage.getStatus().name())
                        .code(errorMessage.name())
                        .message(errorMessage.getErrorMessage())
                        .build());
    }
}
