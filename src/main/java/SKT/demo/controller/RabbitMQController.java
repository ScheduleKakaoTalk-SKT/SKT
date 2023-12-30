package SKT.demo.controller;

import SKT.demo.model.dto.MessageDto;
import SKT.demo.service.RabbitMQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class RabbitMQController {
    private final RabbitMQService rabbitMQService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody @Valid MessageDto messageDto) {
        rabbitMQService.sendMessage(messageDto);

        return ResponseEntity.ok("메시지 전송 성공 !");
    }
}
