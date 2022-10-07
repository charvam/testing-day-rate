package com.shipmonk.testingday.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shipmonk.testingday.model.domain.Base;


public interface BaseRepository extends JpaRepository<Base, Long> {

    @Query("select b from Base b join fetch b.rates where b.currency = :currency and b.date = :date")
    Optional<Base> findBaseByDateAndCurrency(LocalDate date, String currency);

}
