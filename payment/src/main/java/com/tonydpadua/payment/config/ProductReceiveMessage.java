package com.tonydpadua.payment.config;

import com.tonydpadua.payment.product.Product;
import com.tonydpadua.payment.product.ProductRepository;
import com.tonydpadua.payment.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReceiveMessage {


    private final ProductRepository productRepository;

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProductDTO productDTO) {
        this.productRepository.save(Product.from(productDTO));
    }
}
