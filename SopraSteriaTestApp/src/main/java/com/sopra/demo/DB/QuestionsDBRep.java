
package com.sopra.demo.DB;

import com.sopra.demo.DB.Entities.FormDB;
import com.sopra.demo.DB.Entities.FormQuestions;
import com.sopra.demo.DB.Entities.QuestionsDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsDBRep extends JpaRepository<QuestionsDB, Integer> {
}
