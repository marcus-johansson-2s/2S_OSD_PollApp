package com.sopra.demo.controllers.Service;

import com.sopra.demo.controllers.Answers.FormAnswer;
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

    @Override
    public List<FormAnswer> findAnswers() {
        return new ArrayList<>(answersDB.values());
    }

    @Override
    public void saveAnswers(FormAnswer answers) {
        long nextId = getNextId();
/*
        Map<Long, FormAnswer> formMap = answers.stream()
                .collect(Collectors.toMap(FormAnswer::getId, Function.identity()));


 */
        answersDB.put(answers.getId(),answers);
    }

    private Long getNextId() {
        return answersDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }
}
