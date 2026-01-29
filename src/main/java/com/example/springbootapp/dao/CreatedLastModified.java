package com.example.springbootapp.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

//@Embeddable
public class CreatedLastModified {

//    @CreationTimestamp
//    @Column(name = "created", updatable = false, nullable = false, unique = false, secondPrecision = 3)
//    private Instant created;
//    @UpdateTimestamp
//    @Column(name = "last_modified", updatable = true, nullable = false, unique = false, secondPrecision = 3)
//    private Instant lastModified;
}
