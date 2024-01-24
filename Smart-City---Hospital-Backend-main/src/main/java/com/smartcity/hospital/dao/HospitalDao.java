package com.smartcity.hospital.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.smartcity.hospital.model.Hospital;

@Repository
@Component
public interface HospitalDao extends CrudRepository<Hospital, Integer>{

    public Hospital getHospitalById(int id);
    
}
