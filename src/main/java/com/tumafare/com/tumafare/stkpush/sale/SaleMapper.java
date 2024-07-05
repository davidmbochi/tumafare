package com.tumafare.com.tumafare.stkpush.sale;

import org.springframework.stereotype.Service;

@Service
public class SaleMapper {
    public Sale toSale(String sale){
        return Sale.builder()
                .sale(sale)
                .build();
    }
}
