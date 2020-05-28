package com.sopra.demo.DB;

import com.sopra.demo.DB.Entities.FormDB;
import com.sopra.demo.DB.Entities.FormQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRep extends JpaRepository<FormQuestions, Integer> {
}
