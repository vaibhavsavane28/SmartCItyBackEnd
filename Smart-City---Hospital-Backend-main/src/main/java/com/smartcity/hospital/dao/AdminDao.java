package com.smartcity.hospital.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.smartcity.hospital.model.Admin;

@Repository
@Component
public interface AdminDao extends CrudRepository<Admin, String> {
    
    public Admin getAdminByemail(String email);
}
