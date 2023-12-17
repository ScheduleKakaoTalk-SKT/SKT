package SKT.demo.exception.error;

import SKT.demo.exception.message.UserErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private final UserErrorMessage userErrorMessage;
}
