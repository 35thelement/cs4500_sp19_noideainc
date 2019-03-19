package com.example.cs4500_sp19_noideainc.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cs4500_sp19_noideainc.models.FrequentlyAskedQuestion;;

public interface PagedFAQRepository extends JpaRepository<FrequentlyAskedQuestion, Integer> {
	
}
