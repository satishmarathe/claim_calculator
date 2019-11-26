package com.profectus.product.claim.calculator.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


//@DataJpaTest
//@Sql({"/users_schema.sql", "/import_users.sql"})
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;	
	
	@Disabled
	@Test
	public void testUserDataIsInserted() {
		
		// when
  //      User user = userRepository.findById(200).orElse(null);
        // then
    //    assertThat(user.getEmail()).isEqualTo("abc@abc.com");
	}
}
