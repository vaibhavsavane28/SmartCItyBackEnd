package com.smartcity.hospital.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.smartcity.hospital.model.Citizen;

@Repository
@Component
public interface CitizenDao extends CrudRepository<Citizen, String> {
    
    public Citizen getCitizenByemail(String email);
}
