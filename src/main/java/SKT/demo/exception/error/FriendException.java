package SKT.demo.exception.error;

import SKT.demo.exception.message.FriendErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendException extends RuntimeException{
    private final FriendErrorMessage friendErrorMessage;
}
