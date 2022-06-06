package com.example.CodeJavaApp.service.impl;


import com.example.CodeJavaApp.entity.User;
import com.example.CodeJavaApp.repository.UserPageRepository;
import com.example.CodeJavaApp.repository.UserRepository;
import com.example.CodeJavaApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
     UserRepository userRepository;
    @Autowired
    UserPageRepository repo;
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user)  {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public List<User> findByNameContaining(String name) {
        if(name != null){
            return userRepository.findByNameContaining(name);
        }
        return userRepository.findAll();
    }

    @Override
    public Page<User> listAll(int pageNumber, String sortField, String sortDir,String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1,6,sort);

        if (keyword != null){
            return repo.findAll(keyword,pageable);
        }
        return repo.findAll(pageable);
    }

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    //page User
    public Page<User> listAllUserPage(int pageNumber,String keyword){
        Pageable pageable = PageRequest.of(pageNumber -1,5);
        if (keyword != null){
            return repo.findAll(keyword,pageable);
        }
        return repo.findAll(pageable);
    }


    //Quen mat khau
    public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException {
        User customer = userRepository.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            userRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);
        userRepository.save(customer);
    }
}
