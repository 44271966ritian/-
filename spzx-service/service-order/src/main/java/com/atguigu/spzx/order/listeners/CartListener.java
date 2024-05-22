package com.atguigu.spzx.order.listeners;

import com.atguigu.spzx.feign.cart.CartFeignClient;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CartListener {

    @Autowired
    CartFeignClient cartFeignClient;

    //监听购物队清空列，通过的交换机是订单主题交换机，然后过滤的消息是cart.clean
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "cart.clean",durable = "true"),
            exchange = @Exchange(name = "order.topic",type = ExchangeTypes.TOPIC),
            key = "cart.clean"
    ))
    public void listenCartClean(String token){
        ServletRequestAttributes attributes = new ServletRequestAttributes(new MockHttpServletRequest() {
            @Override
            public String getHeader(String name) {
                if ("token".equals(name)) {
                    return token;
                }
                return super.getHeader(name);
            }
        });
        RequestContextHolder.setRequestAttributes(attributes);
        cartFeignClient.deleteChecked();
        RequestContextHolder.resetRequestAttributes();

    }


}
