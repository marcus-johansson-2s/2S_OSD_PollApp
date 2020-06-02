package com.sopra.demo.DB;

import com.sopra.demo.DB.Entities.CheckboxQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckboxQuestionsRep extends JpaRepository<CheckboxQuestions, Integer> {
}
