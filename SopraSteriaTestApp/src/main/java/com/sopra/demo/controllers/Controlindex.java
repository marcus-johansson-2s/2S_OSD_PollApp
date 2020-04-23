package com.sopra.demo.controllers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.stream.Stream;

import java.util.ArrayList;
import java.util.List;
@Controller
public class Controlindex {
    public static List<Member>memberList = new ArrayList<Member>();
    public static List<Question>questionList = new ArrayList<Question>();
    public static Question tmpQuestion= new Question();
    public static List<Form> tmpForm = new ArrayList<>();

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

        int indexCount=0;
        for(int i=0;i<tmpForm.size()+1;i++)
        {
            indexCount=i;
        }
        tmpForm.add(new Form(indexCount,description));
        redirectAttrs.addAttribute("id",indexCount);
        return "redirect:/createQuestions";
    }

    //////////////////Create Question
    @GetMapping("/createQuestions")
    public String Qcreate(@ModelAttribute ("id")int id,Model model) {
        DTO dto = new DTO();
        int indexCount=0;
        for(int i=0;i<tmpForm.get(id).getQuestionList().size()+1;i++)
        {

            indexCount=i;
        }
        System.out.print(indexCount);
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
            counter++;
            if(c=='\r' || c=='/') {
                q.setCheckBoxAnswer(counter,S.toString());
                S.setLength(0);
            }
            S.append(c);

        }

        System.out.println("answerId"+answerId+")  ");
        q.setQuestion(question);
        q.setId(id);
        if(answerId==1) {
            if(anotherQuestion==1) {
                for(Form f:tmpForm){
                    if(f.getFormId()==formId){
                        q.setTypeQuestion(1);
                        f.setQuestionList(q);

                    }}
                redirectAttrs.addAttribute("id",formId);
                return "redirect:/createQuestions";
            }if(anotherQuestion==2){
                for(Form f:tmpForm) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(1);
                        f.setQuestionList(q);

                    }
                }
                return "loggedIn";
            }}
        if(answerId==2) {
            if(anotherQuestion==1) {
                for(Form f:tmpForm) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(2);
                        f.setQuestionList(q);
                    }
                }
                redirectAttrs.addAttribute("id",formId);
                return "redirect:/createQuestions";
            }if(anotherQuestion==2){
                for(Form f:tmpForm) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(2);
                        f.setQuestionList(q);
                    }
                }
                return "loggedIn";
            }}
        if(answerId==3) {
            if(anotherQuestion==1) {
                for(Form f:tmpForm) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(3);
                        f.setQuestionList(q);
                    }
                }
                redirectAttrs.addAttribute("id",formId);
                return "redirect:/createQuestions";

            }if(anotherQuestion==2){
                for(Form f:tmpForm) {
                    if (f.getFormId() == formId) {
                        q.setTypeQuestion(3);

                        f.setQuestionList(q);
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
        return tmpForm;
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

    ///////////////////////////////////Answer SPecific Question
   // @RequestMapping(value = "/answerSpecQuestion" , method = RequestMethod.GET)
    @GetMapping("/answerSpecQuestion")
    public String answerSpec(@ModelAttribute ("id") int id, Model model) {
        for(Form f:tmpForm){
            if(id==f.getFormId()) {
                model.addAttribute("dto", f);
                return "answerSpecQuestion";
            }
        }
        return "error";

    }

    @PostMapping("/answerSpecQuestion")
    public String answerSpecFinish(Model model) {
        //@RequestParam("lines") String input,
/*
        @RequestMapping(value="/", method=RequestMethod.POST, params="action=submitlines")
        public String handleForm(
                @RequestParam("lines") String input,
                RedirectAttributes redirectAttributes) {

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
        for(Form q:tmpForm)
        {
            if(id==q.getFormId()) {
                model.addAttribute("form", q);
                return "showingFormAnswers";
            }
        }

        return "error";

    }

}