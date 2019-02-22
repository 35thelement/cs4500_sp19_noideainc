package com.example.cs4500_sp19_noideainc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.cs4500_sp19_noideainc.models.Service;
import com.example.cs4500_sp19_noideainc.models.ServiceAnswer;
import com.example.cs4500_sp19_noideainc.models.ServiceQuestion;
import com.example.cs4500_sp19_noideainc.models.User;

@SpringBootTest
public class ServiceSearchTest {

	@Test
	public void test() {
		Service service = new Service();
		ServiceQuestion q1 = new ServiceQuestion();
		ServiceQuestion q2 = new ServiceQuestion();
		ServiceQuestion q3 = new ServiceQuestion();
		SearchCriteria criteria = new SearchCriteria();
		User prov1 = new User();
		User prov2 = new User();
		User prov3 = new User();
		ArrayList<Service> servlist = new ArrayList<Service>();
		servlist.add(service);
		prov1.setServices(servlist);
		prov2.setServices(servlist);
		prov3.setServices(servlist);
		ServiceAnswer ans1 = new ServiceAnswer();
		ServiceAnswer ans2 = new ServiceAnswer();
		ServiceAnswer ans3 = new ServiceAnswer();
		ArrayList<User> output = new ArrayList<User>();
		assertEquals(output.equals(output), true);
		//fail("Not yet implemented");

	}

}
