package com.carsbooks.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.carsbooks.spring.jpa.h2.entities.CarsBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carsbooks.spring.jpa.h2.repository.CarsBookRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/cars")
public class CarsBookController {

  @Autowired
  CarsBookRepository carsRepository;

  @GetMapping("/carsbooks")
  public ResponseEntity<List<CarsBook>> getAllCarsBooks(@RequestParam(required = false) String titulo) {
    try {
      List<CarsBook> carsBooks = new ArrayList<CarsBook>();

      if (titulo == null)
        carsRepository.findAll().forEach(carsBooks::add);
      else{
        carsRepository.findByTituloContainingIgnoreCase(titulo).forEach(carsBooks::add);
      }
      if (carsBooks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(carsBooks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/carsbook/{id}")
  public ResponseEntity<CarsBook> getCarsBookById(@PathVariable("id") long id) {
    Optional<CarsBook> carsBooksData = carsRepository.findById(id);

    if (carsBooksData.isPresent()) {
      return new ResponseEntity<>(carsBooksData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/carsbooks")
  public ResponseEntity<CarsBook> createCarsBook(@RequestBody CarsBook carsBookBody) {
    try {
      CarsBook carsBook = carsRepository.save(new CarsBook(carsBookBody.getTitulo(), carsBookBody.getResumen(), false));
      return new ResponseEntity<>(carsBook, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/carsbooks/{id}")
  public ResponseEntity<CarsBook> updateCarsBook(@PathVariable("id") long id, @RequestBody CarsBook carsBookBody) {
    Optional<CarsBook> bookData = carsRepository.findById(id);

    if (bookData.isPresent()) {
      CarsBook foundedBookData = bookData.get();
      foundedBookData.setTitulo(carsBookBody.getTitulo());
      foundedBookData.setResumen(carsBookBody.getResumen());
      foundedBookData.setPublicado(carsBookBody.isPublicado());
      return new ResponseEntity<>(carsRepository.save(foundedBookData), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * EJEMPLI SIMPLE DE PATCH SIN CREAR UN MAPPER
   * @param id
   * @param carsBookBody
   * @return
   */
  @PatchMapping("/carsbooks/solo/titulo/{id}")
  public ResponseEntity<CarsBook> updatePartialCarsBook(@PathVariable("id") long id, @RequestBody CarsBook carsBookBody) {
    Optional<CarsBook> bookData = carsRepository.findById(id);

    if (bookData.isPresent()) {
      CarsBook foundedBookData = bookData.get();
      foundedBookData.setTitulo(carsBookBody.getTitulo());
      return new ResponseEntity<>(carsRepository.save(foundedBookData), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @DeleteMapping("/carsbooks/{id}")
  public ResponseEntity<HttpStatus> deleteCarsBook(@PathVariable("id") long id) {
    try {
      carsRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/carsbooks/delete/all/sinwhere")
  public ResponseEntity<HttpStatus> deleteAllBooksSinWhere() {
    try {
      carsRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/carsbooks/publicado")
  public ResponseEntity<List<CarsBook>> findByPublished() {
    try {
      List<CarsBook> carsBooks = carsRepository.findByPublicado(true);

      if (carsBooks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(carsBooks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
