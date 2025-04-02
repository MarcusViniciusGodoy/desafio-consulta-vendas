package com.devsuperior.dsmeta.controllers;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
    public ResponseEntity<Page<SaleMinDTO>> getReport(
            @RequestParam(name = "minDate", required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate minDate,
            
            @RequestParam(name = "maxDate", required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate maxDate,
            
            @RequestParam(name = "name", defaultValue = "") String name,
            Pageable pageable) {

        if (minDate == null) {
            maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            minDate = maxDate.minusYears(1L);
        }

        if (maxDate == null) {
            maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }

        Page<SaleMinDTO> dto = service.getReport(minDate, maxDate, name, pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<SaleDTO>> getSummary(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "minDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate minDate,
            @RequestParam(name = "maxDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate maxDate) {
        
                
        List<SaleDTO> summary;
        if (minDate != null && maxDate != null) {
            summary = service.getSalesSummary(name, minDate, maxDate);
        } else {
            summary = service.getSalesSummaryMonths(name);
        }
        return ResponseEntity.ok(summary);
    }
}
