package com.sopra.demo.DB;

import com.sopra.demo.DB.Entities.FormAnswerDB;
import com.sopra.demo.DB.Entities.FormDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRep extends JpaRepository<FormAnswerDB, Integer> {
}
