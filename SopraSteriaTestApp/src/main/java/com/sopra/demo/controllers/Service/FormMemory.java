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
public class FormMemory implements FormService {

    static Map<Long, Form> formDB = new HashMap<>();



    @Override
    public List<Form> findAll() {
        return new ArrayList<>(formDB.values());
    }

    @Override
    public void saveAll(List<Form> forms) {
        long nextId = getNextId();

        Map<Long, Form> formMap = forms.stream()
                .collect(Collectors.toMap(Form::getFormId, Function.identity()));

        formDB.putAll(formMap);
    }

    private Long getNextId() {
        return formDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }




}
