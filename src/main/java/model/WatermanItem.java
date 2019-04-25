package model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WatermanItem {
    private String name;
    private String address;
    private Long code;
    private BigDecimal price;
    private String groupe;
}
