package com.example.CodeJavaApp;

import com.example.CodeJavaApp.entity.User;
import com.example.CodeJavaApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;
    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("tientv34.jvb@gmail.com");
        user.setPassword("123456");
        user.setFirstname("Tien");
        user.setLastname("Tran");

        User saveUser = repo.save(user);

    }
}
