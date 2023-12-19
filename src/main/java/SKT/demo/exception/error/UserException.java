package SKT.demo.exception.error;

import SKT.demo.exception.message.UserErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private final UserErrorMessage userErrorMessage;
}
