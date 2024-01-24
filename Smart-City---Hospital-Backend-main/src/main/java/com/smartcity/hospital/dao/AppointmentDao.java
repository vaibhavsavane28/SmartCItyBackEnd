package com.smartcity.hospital.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.smartcity.hospital.model.Appointment;
import com.smartcity.hospital.model.Hospital;

@Repository
@Component
public interface AppointmentDao extends CrudRepository<Appointment, Integer>{

    public Appointment getAppointmentById(int id);

    Optional<Hospital> findByName(String name);
    
}
