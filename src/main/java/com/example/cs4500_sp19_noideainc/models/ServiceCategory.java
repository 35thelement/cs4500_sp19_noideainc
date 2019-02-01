package com.example.cs4500_sp19_noideainc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service_categories")
public class ServiceCategory {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String serviceCategoryName;
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getServiceCategoryName() {
    return serviceCategoryName;
  }
  public void setServiceCategoryName(String serviceCategoryName) {
    this.serviceCategoryName = serviceCategoryName;
  }
}