package com.example.CodeJavaApp.service;

import com.example.CodeJavaApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;


import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();

    List<User> findAll();

    void save(User user);

    void deleteUser(Long id);

    Optional<User> findUserById(Long id);

    User findByEmail(String email);

    List<User> findByNameContaining(String name);

    public Page<User> listAll(int pagaNumber, String sortField,String sortDir,String keyword);

    public void sendEmail(SimpleMailMessage email);
}
