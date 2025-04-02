package com.devsuperior.dsmeta.dto;


import com.devsuperior.dsmeta.entities.Sale;

public class SaleDTO {

    private String sellerName;
	private Double total;

    public SaleDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SaleDTO(Sale entity){
        sellerName = entity.getSeller().getName();
        total = entity.getAmount();
    }

    public Double getTotal() {
        return total;
    }

    public String getSellerName() {
        return sellerName;
    }
}
