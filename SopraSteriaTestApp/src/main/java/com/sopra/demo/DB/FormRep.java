package com.sopra.demo.DB;

import com.sopra.demo.DB.Entities.FormDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRep extends JpaRepository<FormDB, Integer> {
}
