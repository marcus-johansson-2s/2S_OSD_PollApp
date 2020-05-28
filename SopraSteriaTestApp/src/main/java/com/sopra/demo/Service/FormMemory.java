package com.sopra.demo.Service;


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

    long del=9999;

    public void delForm(long id) {
        for (Map.Entry<Long,Form> entry : formDB.entrySet()) {
            if(entry.getValue().getFormId() == id)
                del=entry.getKey();
        }
        if(del!=9999) {
            formDB.remove(del);
        }
    }

    public boolean existsDoubles(long id) {
        for (Map.Entry<Long,Form> entry : formDB.entrySet()) {
            if(entry.getValue().getFormId() == id)
                return true;
        }
        return false;
    }

    public boolean existsDoublesQuestions(long id,int doppler) {
        for (Map.Entry<Long,Form> entry : formDB.entrySet()) {
            if (entry.getValue().getFormId() == id) {
                for(int i=0;i<entry.getValue().questionList.size();i++) {
                    if (entry.getValue().questionList.get(i).getId()==doppler)
                            return true;

                }
            }

        }
            return false;
    }

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

    @Override
    public void save(Form form) {

        formDB.put(getNextId(),form);
    }

    @Override
    public Form findingOne(long theOne) {
        for(Form e:formDB.values()){
            if(e.getFormId()==theOne)
                return e;

        }
        return null;
}


    private Long getNextId() {
        return formDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }




}
