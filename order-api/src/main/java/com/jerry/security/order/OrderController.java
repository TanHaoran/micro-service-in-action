package com.jerry.security.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/2
 * Time: 22:13
 * Description:
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info) {
        PriceInfo priceInfo = restTemplate.getForObject("http://localhost:9060/prices/" + info.getProductId(),
                PriceInfo.class);
        log.info("price is {}", priceInfo.getPrice());
        return info;
    }

}
