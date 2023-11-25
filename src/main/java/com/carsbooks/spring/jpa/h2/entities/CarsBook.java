package com.carsbooks.spring.jpa.h2.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "carsbook")
public class CarsBook {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "resumen")
  private String resumen;

  @Column(name = "publicado")
  private boolean publicado;

  public CarsBook() {

  }

  public CarsBook(String titulo, String resumen, boolean publicado) {
    this.titulo = titulo;
    this.resumen = resumen;
    this.publicado = publicado;
  }

  public long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String title) {
    this.titulo = title;
  }

  public String getResumen() {
    return resumen;
  }

  public void setResumen(String description) {
    this.resumen = description;
  }

  public boolean isPublicado() {
    return publicado;
  }

  public void setPublicado(boolean isPublished) {
    this.publicado = isPublished;
  }

  @Override
  public String toString() {
    return "CarBook [id=" + id + ", title=" + titulo + ", desc=" + resumen + ", published=" + publicado + "]";
  }

}
