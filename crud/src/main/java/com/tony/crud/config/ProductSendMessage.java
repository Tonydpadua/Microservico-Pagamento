package com.tony.crud.config;

import com.tony.crud.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSendMessage {


    @Value("${crud.rabbitmq.exchange}")
    String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    String routingkey;
    
    public final RabbitTemplate rabbitTemplate;

    public void sendMessage(ProductDTO productDTO) {
        this.rabbitTemplate.convertAndSend(this.exchange, this.routingkey, productDTO);
    }

}
