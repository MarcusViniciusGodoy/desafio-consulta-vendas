package com.devsuperior.dsmeta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;

public interface SellerRepository extends JpaRepository<Sale, Long>{

    @Query("SELECT obj FROM Seller obj " + 
           "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name , '%'))")
    Page<Seller> searchByName(String name, Pageable pageable);
}
