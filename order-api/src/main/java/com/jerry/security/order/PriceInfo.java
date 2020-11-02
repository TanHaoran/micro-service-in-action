package com.jerry.security.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/2
 * Time: 22:16
 * Description:
 */
@Data
public class PriceInfo {

    private Long id;

    private BigDecimal price;

}
