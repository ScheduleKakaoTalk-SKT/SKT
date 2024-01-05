package SKT.demo.service;

import SKT.demo.model.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(MessageDto messageDto) {
        log.info("message send : {}", messageDto.toString());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(MessageDto messageDto) {
        log.info("Receive message : {}", messageDto.toString());
    }
}
