package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.repositories.SellerRepository;

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
    public Page<SaleMinDTO> findByDateBetweenAndSellerNameContaining(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
        Page<Sale> sales = repository.findByDateBetweenAndSellerNameContaining(minDate, maxDate, name, pageable);
        return sales.map(SaleMinDTO::new);
    }

    public Page<SaleMinDTO> findSalesReport(Pageable pageable) {
        LocalDate maxDate = LocalDate.now();
        LocalDate minDate = maxDate.minusYears(1);
        return findByDateBetweenAndSellerNameContaining(minDate, maxDate, "", pageable);
    }
}
