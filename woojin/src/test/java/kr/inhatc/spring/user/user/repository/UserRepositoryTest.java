package kr.inhatc.spring.user.user.repository;

import static org.junit.jupiter.api.Assertions.*; 

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
 
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
//	@Test
//	public void insertTest() {
//		Users user = Users.builder() // jpa 3,4 안해서 그런듯 
//				.id("abc")
//				.pw("111")
//				.email("abc@abaab.com")
//				.role("ROLE_ADMIN")
//				.build();
//		
//		userRepository.save(user);
//	}
	
	
	@Test
	void findAllByOrderByIdDesc() {
		List<Users> list = userRepository.findAllByOrderByIdDesc();
		System.out.println("=====================ang===================");
		for (Users users : list) {
			System.out.println(users);
		}
	}

}
