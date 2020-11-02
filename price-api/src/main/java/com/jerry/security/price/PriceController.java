package com.jerry.security.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/2
 * Time: 22:16
 * Description:
 */
@RestController
@RequestMapping("/prices")
@Slf4j
public class PriceController {

    @GetMapping("/{id}")
    public PriceInfo get(@PathVariable Long id) {
        log.info("productId is {}", id);
        PriceInfo info = new PriceInfo();
        info.setId(id);
        info.setPrice(new BigDecimal(100));
        return info;
    }

}
