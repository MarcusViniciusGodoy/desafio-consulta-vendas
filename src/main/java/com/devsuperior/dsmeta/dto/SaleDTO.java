package com.devsuperior.dsmeta.dto;


import com.devsuperior.dsmeta.entities.Sale;

public class SaleDTO {

    private String sellerName;
	private Double amount;

    public SaleDTO(String sellerName, Double amount) {
        this.sellerName = sellerName;
        this.amount = amount;
    }

    public SaleDTO(Sale entity){
        sellerName = entity.getSeller().getName();
        amount = entity.getAmount();
    }

    public Double getAmount() {
        return amount;
    }

    public String getSellerName() {
        return sellerName;
    }
}
