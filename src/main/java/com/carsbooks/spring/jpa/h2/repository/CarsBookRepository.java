package com.carsbooks.spring.jpa.h2.repository;

import java.util.List;

import com.carsbooks.spring.jpa.h2.entities.CarsBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsBookRepository extends JpaRepository<CarsBook, Long> {
  List<CarsBook> findByPublicado(boolean published);

  List<CarsBook> findByTituloContainingIgnoreCase(String title);
}
