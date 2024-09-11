package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.model.Contact;

public interface ContactRepository  extends JpaRepository<Contact, Long> {

}
