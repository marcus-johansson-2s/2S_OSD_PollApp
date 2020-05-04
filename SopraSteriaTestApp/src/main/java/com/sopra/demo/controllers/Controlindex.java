package com.sopra.demo.controllers;
import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;
import com.sopra.demo.controllers.Service.AnswerService;
import com.sopra.demo.controllers.Service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
@Controller
public class Controlindex {

    @Autowired
    private FormService formService;
    @Autowired
    private AnswerService answerService;



    public static List<Member>memberList = new ArrayList<Member>();


    @RequestMapping("/")
    public String index(){
            return "index";
        }
    //////////////////////////////Create User
        @GetMapping("/createUser")
    public String signUp(Model model) {
        model.addAttribute("createUser", new Member());
        return "createUser";
    }
    @PostMapping("/createUser")
    public String Submit(@ModelAttribute Member member) {
        for(Member mems:memberList){
            if(mems.getName().equals(member.getName()))
                    return "error";
        }
        memberList.add(member);
        return "result";
    }

    @RequestMapping(value = "/members" , method = RequestMethod.GET)
    @ModelAttribute("test")
    public  List<Member> member(){
        return memberList;
    }
    //////////////////////////////LOGIN
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new Member());
        return "login";
    }

    @PostMapping("/login")
    public String loginCheck(@ModelAttribute Member member) {

        for(Member mems:memberList){
            if(mems.getName().equals(member.getName()) && mems.getPassword().equals(member.getPassword()))
                return "loggedIn";
        }
        return "error";
    }


    @RequestMapping("/loggedIn")
    public String loggedIn(){
        return "loggedIn";
    }


    //////////////////////////////Create Form
    @GetMapping("/createForm")
    public String Fcreate(Model model) {
        model.addAttribute("createObj", new DTO());
        return "createForm";
    }
    @PostMapping("/createForm")
    public String Fsubmit(@RequestParam ("question")String description,RedirectAttributes redirectAttrs) {

        int indexCount=formService.findAll().size();
        List<Form> tmpForm = new ArrayList<>();
        tmpForm.add(new Form((long)indexCount,description));
        formService.saveAll(tmpForm);

        redirectAttrs.addAttribute("id",indexCount);
        return "redirect:/createQuestions";
    }

    //////////////////Create Question
    @GetMapping("/createQuestions")
    public String Qcreate(@ModelAttribute ("id")int id,Model model) {
        DTO dto = new DTO();
        int indexCount=formService.findAll().get(id).getQuestionList().size();

        dto.setAnswerId(1);
        dto.setAnotherQuestion(2);
        dto.setFormId(id);
        dto.setId(indexCount);
        model.addAttribute("dto", dto);
        return "createQuestions";
    }
    @PostMapping("/createQuestions")
    public String Qsubmit(@RequestParam String checkBoxText,@RequestParam int anotherQuestion,@RequestParam int id,@RequestParam int answerId,@RequestParam String question,@RequestParam int formId,RedirectAttributes redirectAttrs) {
        Question q=new Question();
//        String[] x = checkBoxText.split("\n");
        int counter=0;
        checkBoxText+="/";
        StringBuilder S= new StringBuilder();
        for (char c:checkBoxText.toCharArray()){
            if(c=='\r' || c=='/') {
                q.setCheckBoxAnswer(counter++,S.toString());
                S.setLength(0);
            }
            S.append(c);

        }
        q.setQuestion(question);

        q.setId(id);

        if(answerId==1) {
            if(anotherQuestion==1) {
                for(Form f:formService.findAll()){
                    if(f.getFormId()==formId){
                        q.setTypeQuestion(1);
                        formService.findAll().get(formId).setQuestionList(q);

                    }}
                redirectAttrs.addAttribute("id",formId);
                return "redirect:/createQuestions";
            }if(anotherQuestion==2){
                for(Form f:formService.findAll()) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(1);
                        formService.findAll().get(formId).setQuestionList(q);

                    }
                }
                return "loggedIn";
            }}
        if(answerId==2) {
            if(anotherQuestion==1) {
                for(Form f:formService.findAll()) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(2);
                        formService.findAll().get(formId).setQuestionList(q);
                    }
                }
                redirectAttrs.addAttribute("id",formId);
                return "redirect:/createQuestions";
            }if(anotherQuestion==2){
                for(Form f:formService.findAll()) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(2);
                        formService.findAll().get(formId).setQuestionList(q);
                    }
                }
                return "loggedIn";
            }}
        if(answerId==3) {
            if(anotherQuestion==1) {
                for(Form f:formService.findAll()) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(3);
                        formService.findAll().get(formId).setQuestionList(q);
                    }
                }
                redirectAttrs.addAttribute("id",formId);
                return "redirect:/createQuestions";

            }if(anotherQuestion==2){
                for(Form f:formService.findAll()) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(3);
                        formService.findAll().get(formId).setQuestionList(q);
                    }
                }
                return "loggedIn";
            }}



        return "error";
    }


    ///////////////////////////////////////// POSTING QUESTIONS
    @RequestMapping(value = "/questions" , method = RequestMethod.GET)
    @ModelAttribute("question")
    public  List<Form> postForms(){
        return formService.findAll();
    }

    ///////////////////////////7


    @GetMapping("/answerQuestion")
    public String answerInput(Model model) {

        model.addAttribute("answerQuestion", new DTO());
        return "answerQuestion";
    }

    @PostMapping("/answerQuestion")
    public String answerFinish(@RequestParam int id, Model model, RedirectAttributes redirectAttrs) {

                redirectAttrs.addAttribute("id", id);
                return "redirect:/answerSpecQuestion";

    }

    ///////////////////////////////////FormAnswer SPecific Question
   // @RequestMapping(value = "/answerSpecQuestion" , method = RequestMethod.GET)
    @GetMapping("/answerSpecQuestion")
    public String answerSpec(@ModelAttribute ("id") int id, Model model) {

        model.addAttribute("dto",formService.findAll().get(id));
        return "answerSpecQuestion";


        /*
        for(Form f:formService.findAll()){
            System.out.println(f.getFormId());
            if((long)id==f.getFormId()) {
                model.addAttribute("dto", formService.findAll().get(id));


            }
        }
                return "error";
    */



    }

    @PostMapping("/answerSpecQuestion")
    public String answerSpecFinish(@ModelAttribute (value="dto")Form form)throws Exception {

        FormAnswer fa = new FormAnswer();

                fa.setFormId(form.getFormId());
                fa.setId(answerService.findAnswers().size());
                fa.setUser("ANON");



        for(Question q:form.getQuestionList())
        {
            QuestionAnswer qa = new QuestionAnswer();
            qa.setId(form.questionList.size());
            qa.setQuestionId(q.getId());
            qa.setType(q.getTypeQuestion());

            if(qa.getType()==1)
            qa.setTextAnswer(form.questionList.get(q.getId()).getTmpString());

            if(qa.getType()==2)
            qa.setRadioAnswer(form.questionList.get(q.getId()).getTmpInt());

            if(qa.getType()==3)
            qa.setCheckBoxAnswer(form.questionList.get(q.getId()).getCheckBoxAnswerList());

            fa.addAnswers(qa);


        }

        answerService.saveAnswers(fa);
/*        answerService.saveAnswers(fa);
        int o= (int) form.getFormId();

        for( Question q:formService.findAll().get(o).getQuestionList()) {
            System.out.println("_________HASHMAP");
            for (Map.Entry<Integer, String> entry : q.getCheckBoxAnswer().entrySet()) {
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            }
            System.out.println("____________________________--");
        }

        for(Question q:form.getQuestionList())
        {
            System.out.println("Question "+ q.getId());
            System.out.println("CheckBoxAnswers");
            for (Integer t:q.getCheckBoxAnswerList()){
                System.out.println(t);
                }
            System.out.println("RadioButton");
            System.out.println(q.getTmpInt());

            System.out.println("TextAnswers");
            System.out.println(q.getTmpString());

            System.out.println("____________________________--");

        }

 */
        return "loggedIn";

    }
    /////////////////////////////Chooosing form

    @GetMapping("/chooseFormAndAnswers")
    public String chooseForm(Model model) {

        model.addAttribute("chooseForm", new DTO());
        return "chooseFormAndAnswers";
    }

    @PostMapping("/chooseFormAndAnswers")
    public String chooseFormPost(@RequestParam int id,RedirectAttributes redirectAttrs) {

                redirectAttrs.addAttribute("id", id);
                return "redirect:/showingFormAnswers";


    }

    /////////////////////////////Seeing form Answers

    @RequestMapping(value = "/showingFormAnswers" , method = RequestMethod.GET)
    public String showingForm(@ModelAttribute ("id") int id,Model model) {
     DtoFormAnswers test= new DtoFormAnswers();


         for (Form q : formService.findAll()) {
             if (id == q.getFormId()) {
                 test.setForm(q);
                 for(FormAnswer fa:answerService.findAnswers()) {
                     if(fa.getFormId()==q.getFormId())
                     test.addFormAnswer(fa);
                 }
/*
                for(FormAnswer b:answerService.findAnswers()){
                for(QuestionAnswer c:b.getAnswers()){
                    System.out.println(c.getTextAnswer());

                } } */
                System.out.println(test.SuperOut());
                 model.addAttribute("dto", test);
                 return "showingFormAnswers";
             }
         }

        return "error";

    }

}