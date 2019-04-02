package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;

public interface ServiceQuestionRepository
        extends CrudRepository<ServiceQuestion, Integer> {
  @Query(value="SELECT entity FROM ServiceQuestion entity")
  public List<ServiceQuestion> findAllServiceQuestions();
  @Query(value="SELECT entity FROM ServiceQuestion entity WHERE id=:id")
  public ServiceQuestion findServiceQuestionById(
          @Param("id") Integer id);
  @Query(value="SELECT entity FROM ServiceQuestion entity WHERE service_id=:service_id")
  public List<ServiceQuestion> findServiceQuestionsByServiceId(
		  @Param("service_id") Integer service_id);
}