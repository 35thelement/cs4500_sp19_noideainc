package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.User;

public interface ServiceAnswerRepository extends CrudRepository<ServiceAnswer, Integer> {
	@Query(value="SELECT serviceAnswer FROM ServiceAnswer serviceAnswer")
	public List<ServiceAnswer> findAllServiceAnswer();
	@Query(value="SELECT serviceAnswer FROM ServiceAnswer serviceAnswer WHERE serviceAnswer.id=:id")
	public ServiceAnswer findServiceAnswerById(@Param("id") Integer id);
}
