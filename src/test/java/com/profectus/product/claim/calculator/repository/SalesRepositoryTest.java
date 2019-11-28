package com.profectus.product.claim.calculator.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class SalesRepositoryTest {
	
	@Autowired
    private DataSource dataSource;

    @Autowired
    private SalesRepository repository;

    public ConnectionHolder getConnectionHolder() {
        // Return a function that retrieves a connection from our data source
        return () -> dataSource.getConnection();
    }
    
    @Disabled
    @Test
    //@DataSet("products.yml")
    void testFindAll() {
    	
    	List<Sales> salesAggregatedList = repository.getDetails(LocalDateTime.of(2019,07,02,00,00,00),LocalDateTime.of(2019,07,02,23,59,59));
        Assertions.assertEquals(1, salesAggregatedList.size(), "We should have 2 products in our database");
    }
}
