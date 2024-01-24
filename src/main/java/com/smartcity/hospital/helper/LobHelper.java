package com.smartcity.hospital.helper;

import java.io.InputStream;
import java.sql.Blob;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class LobHelper {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Blob createBlob(InputStream content, long size) {
        return ((Session)entityManager).getLobHelper().createBlob(content, size);
    }


}