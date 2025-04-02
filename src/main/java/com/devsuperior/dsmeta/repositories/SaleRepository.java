package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT obj FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate " +
               "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))",
       countQuery = "SELECT COUNT(obj) FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate " +
                    "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Sale> findByDateBetweenAndSellerNameContaining(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name, Pageable pageable);

    @Query("SELECT obj.seller.name, SUM(obj.amount) " +
    "FROM Sale obj " +
    "WHERE LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
    "AND obj.date BETWEEN :minDate AND :maxDate " +
    "GROUP BY obj.seller.name")
    List<Object[]> findSalesBySellerGrouped(@Param("name") String name, @Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

    @Query("SELECT obj.seller.name, SUM(obj.amount) " +
       "FROM Sale obj " +
       "WHERE LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
       "AND obj.date BETWEEN :lastYearDate AND CURRENT_DATE " +
       "GROUP BY obj.seller.name")
    List<Object[]> findSalesBySellerGroupedLast12Months(@Param("name") String name, @Param("lastYearDate") LocalDate lastYearDate);

}
    
