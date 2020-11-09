package com.jerry.security.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public OrderInfo create(@RequestBody OrderInfo info, @RequestHeader String username) {
//        PriceInfo priceInfo = restTemplate.getForObject("http://localhost:9060/prices/" + info.getProductId(),
//                PriceInfo.class);
//        log.info("price is {}", priceInfo.getPrice());
        log.info("user is {}", username);
        return info;
    }

    @GetMapping("/{id}")
    public OrderInfo get(@PathVariable Long id) {
        log.info("orderId is {}", id);
        return new OrderInfo();
    }

}
