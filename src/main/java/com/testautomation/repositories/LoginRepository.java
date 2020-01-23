package com.testautomation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testautomation.model.Login;


public interface LoginRepository extends JpaRepository<Login, String>{

}
