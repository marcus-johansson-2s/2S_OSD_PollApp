package com.sopra.demo.controllers.Service;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Form;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AnswerMemory implements AnswerService {

    static Map<Long, FormAnswer> answersDB = new HashMap<>();

    public void delForm(long id) {

        long del=9999;
        for (Map.Entry<Long, FormAnswer> entry : answersDB.entrySet()) {
            if(entry.getValue().getFormId() == id) {
                del=entry.getKey();
            }

        }
        if(del!=9999) {
            answersDB.remove(del);
        }

    }

    @Override
    public List<FormAnswer> findAnswers() {
        return new ArrayList<>(answersDB.values());
    }

    @Override
    public void saveAnswers(FormAnswer answers) {

        answersDB.put(getNextId(),answers);
    }

    private Long getNextId() {
        return answersDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }
}
