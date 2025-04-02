package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
    
    @Transactional(readOnly = true)
    public Page<SaleMinDTO> getReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
        Page<Sale> sales = repository.findByDateBetweenAndSellerNameContaining(minDate, maxDate, name, pageable);
        return sales.map(SaleMinDTO::new);
    }

    @Transactional(readOnly = true)
    public List<SaleDTO> getSalesSummary(String name, LocalDate minDate, LocalDate maxDate) {
        List<Object[]> results = repository.findSalesBySellerGrouped(name, minDate, maxDate);
        return results.stream()
        .map(result -> {
            String sellerName = (String) result[0]; 
            Double amount = (Double) result[1];     
            return new SaleDTO(sellerName, amount);
        })
        .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SaleDTO> getSalesSummaryMonths(String name) {
        LocalDate lastYearDate = LocalDate.now().minusMonths(12);
        List<Object[]> results = repository.findSalesBySellerGroupedLast12Months(name, lastYearDate);
    
        return results.stream()
            .map(result -> {
                String sellerName = (String) result[0];
                Double amount = (Double) result[1];
    
                return new SaleDTO(sellerName, amount);
            })
            .collect(Collectors.toList());
    }
}
