package com.sopra.demo.controllers;

import com.sopra.demo.controllers.Answers.FormAnswer;
import com.sopra.demo.controllers.Answers.QuestionAnswer;
import com.sopra.demo.Service.AnswerService;
import com.sopra.demo.Service.FormService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.apache.poi.util.IOUtils.toByteArray;

@Controller
public class Controlindex {


    @Qualifier("formImpDB")
    @Autowired
    private FormService formService;
    @Qualifier("answerDB")
    @Autowired
    private AnswerService answerService;

    private String adminPass = "admin";

    public static List<Member> memberList = new ArrayList<Member>();


    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/admin")
    public String signUp(Model model, HttpSession session) throws Exception {

        try {
            if (session.getAttribute("sessionPass") == "hasRights")
                return "loginSuccess";


        } catch (Exception e) {
        }

        model.addAttribute("admin", new smallDto());
        return "admin";
    }

    @PostMapping("/admin")
    public String Submit(@ModelAttribute("admin") smallDto admin, HttpSession session,RedirectAttributes redirectAttributes) throws IOException {

        if (admin.getAdminPass().equals(adminPass)) {
            session.setAttribute("sessionPass", "hasRights");


            redirectAttributes.addFlashAttribute("successDto","You are now logged in as admin");
            return "redirect:/loggedIn";

        }

        return "adminDeny";
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    @ModelAttribute("test")

    public List<Member> member() {
        return memberList;
    }

    //////////////////////////////LOGIN
    @RequestMapping("/loggedIn")
    public String loggedIn(Model model) {

        String some = (String) model.asMap().get("successDto");

        if(some==null)
            some="Action was successful";
        model.addAttribute("success", new SuccessDto(some));

        return "loggedIn";
    }


    //////////////////////////////Create Form
    @GetMapping("/createForm")
    public String Fcreate(Model model, HttpSession session) throws Exception {
        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }

        model.addAttribute("createObj", new DTO());
        return "createForm";
    }

    @PostMapping("/createForm")
    public String Fsubmit(@RequestParam("tmpInt") boolean anon, @RequestParam("question") String description, RedirectAttributes redirectAttrs) {

        long indexCount = 1;


        while (formService.existsDoubles(indexCount)) {
            indexCount++;
        }




           // System.out.println("Index count = " +indexCount);
        formService.saveForm(new Form(indexCount, description, anon, false));

        redirectAttrs.addAttribute("id", indexCount);
        return "redirect:/createQuestions";
    }

    //////////////////Create Question
    @GetMapping("/createQuestions")
    public String Qcreate(@ModelAttribute("id") int id, Model model, HttpSession session) {

        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }


        DTO dto = new DTO();
        int indexCount = formService.findingOne(id).questionList.size();
        dto.setAnswerId(1);
        dto.setAnotherQuestion(2);
      // System.out.println("Form id= "+id);
        dto.setFormId(id);
        dto.setId(indexCount);
        model.addAttribute("dto", dto);
        return "createQuestions";
    }

    @PostMapping("/createQuestions")
    public String Qsubmit(@RequestParam String checkBoxText, @RequestParam int anotherQuestion, @RequestParam int id, @RequestParam int answerId, @RequestParam String question, @RequestParam int formId, RedirectAttributes redirectAttrs) {
        Question q = new Question();

//        String[] x = checkBoxText.split("\n");
        int counter = 0;
        checkBoxText += "/";
        StringBuilder S = new StringBuilder();
        for (char c : checkBoxText.toCharArray()) {
            if (c == '\r' || c == '/') {
                q.setCheckBoxAnswer(counter++, S.toString());
                S.setLength(0);
            }
            S.append(c);

        }
        q.setQuestion(question);

        int tmp=id;
       while(formService.existsDoublesQuestions(formId,tmp)) {
           tmp++;
       }
           q.setId(tmp);


        if (answerId == 1) {
            if (anotherQuestion == 1) {

                        q.setTypeQuestion(1);
                      // formService.findingOne(formId).setQuestionList(q);
                        formService.savingQuestion(q,formId);
                        //formService.findAll().get(formId).setQuestionList(q);



                redirectAttrs.addAttribute("id", formId);
                return "redirect:/createQuestions";
            }
            if (anotherQuestion == 2) {

                        q.setTypeQuestion(1);
                      //  formService.findingOne(formId).setQuestionList(q);

                        formService.savingQuestion(q,formId);
                        // formService.findAll().get(formId).setQuestionList(q);



                redirectAttrs.addFlashAttribute("successDto","Action was successful");
                return "redirect:/loggedIn";
            }
        }
        if (answerId == 2) {
            if (anotherQuestion == 1) {

                        q.setTypeQuestion(2);
                formService.savingQuestion(q,formId);
                       // formService.findingOne(formId).setQuestionList(q);
                        //formService.findAll().get(formId).setQuestionList(q);


                redirectAttrs.addAttribute("id", formId);
                return "redirect:/createQuestions";
            }

            if (anotherQuestion == 2) {

                        q.setTypeQuestion(2);
                        formService.savingQuestion(q,formId);
                        //formService.findAll().get(formId).setQuestionList(q);
                        //formService.findingOne(formId).setQuestionList(q);

                redirectAttrs.addFlashAttribute("successDto","Action was successful");
                return "redirect:/loggedIn";

        }
        }
        if (answerId == 3) {
            if (anotherQuestion == 1) {

                        q.setTypeQuestion(3);
                formService.savingQuestion(q,formId);
                        //formService.findingOne(formId).setQuestionList(q);
                        // formService.findAll().get(formId).setQuestionList(q);


                redirectAttrs.addAttribute("id", formId);
                return "redirect:/createQuestions";

            }
            if (anotherQuestion == 2) {

                        q.setTypeQuestion(3);
                        formService.savingQuestion(q,formId);
                       // formService.findingOne(formId).setQuestionList(q);
                        // formService.findAll().get(formId).setQuestionList(q);

                redirectAttrs.addFlashAttribute("successDto","Action was successful");
                return "redirect:/loggedIn";
            }
        }


        return "error";
    }


    ///////////////////////////////////////// POSTING QUESTIONS
    @RequestMapping(value = "/adminDeny", method = RequestMethod.GET)
    public String adminDeny(Model model) {
        return "adminDeny";
    }

    ///////////////////////////7


    @GetMapping("/answerQuestion")
    public String answerInput(Model model) {

        List<Integer> lista = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        for (Form i : formService.findAll()) {
            strings.add(i.getDescription());
            int e = (int) i.getFormId();
            lista.add(e);
        }

        model.addAttribute("strings", strings);

        model.addAttribute("Qid", formService.findAll());
        return "answerQuestion";
    }

    ///////////////////////////////////FormAnswer SPecific Question
    // @RequestMapping(value = "/answerSpecQuestion" , method = RequestMethod.GET)
    // @GetMapping("/answerSpecQuestion"
    @RequestMapping(value = "/answerSpecQuestion", params = "id", method = RequestMethod.GET)
    public String answerSpec(@RequestParam("id") long id, Model model, OAuth2AuthenticationToken authentication,RedirectAttributes redirectAttributes) throws IOException {



        if (!formService.findingOne(id).getActive()) {
            redirectAttributes.addFlashAttribute("errorDto","Form is not activated");
            return "redirect:/errorMessage";
        }


        for (FormAnswer faa : answerService.findAnswers()) {
            if (faa.getFormId() == id) {
               //if (faa.getUser().equals(authentication.getPrincipal().getAttributes().get("name").toString()) ||authentication.getPrincipal().getAttributes().get("name").toString().equals(encryption.decryptOrNull(faa.getUser())) )
                if (faa.getUser().equals(authentication.getPrincipal().getAttributes().get("name").toString()) ||Integer.toString(authentication.getPrincipal().getAttributes().get("name").toString().hashCode()).equals(faa.getUser()) ) {

                    redirectAttributes.addFlashAttribute("errorDto","You can only answer once per form");
                    return "redirect:/errorMessage";

                }
            }
        }



        model.addAttribute("dto", formService.findAll().stream().filter(f -> f.getFormId() == id).findFirst().orElseGet(null));
        return "answerSpecQuestion";

    }

    @PostMapping("/answerSpecQuestion")
    public String answerSpecFinish(@ModelAttribute(value = "dto") Form form, OAuth2AuthenticationToken authentication,RedirectAttributes redirectAttributes) throws Exception {


        FormAnswer fa = new FormAnswer();


        fa.setFormId(form.getFormId());
        fa.setId((int) form.getFormId());

        if (form.getAnon())
            fa.setUser(authentication.getPrincipal().getAttributes().get("name").toString());
        else {

            fa.setUser(Integer.toString(authentication.getPrincipal().getAttributes().get("name").toString().hashCode()));
            fa.setAnon(true);

        }

        int matcher=0;

        for (Question q : form.getQuestionList()) {
            QuestionAnswer qa = new QuestionAnswer();

            qa.setId(form.questionList.size());
            qa.setQuestionId(q.getId());
            qa.setType(q.getTypeQuestion());

            //qa.setQuestion(formService.findingOne((int)form.getFormId()).questionList.get(q.getId()).getQuestion());
            qa.setQuestion(formService.getQuestion((int)form.getFormId(),q.getId()));

            ///////////////Correct index fault
            for(int i=0;i<form.getQuestionList().size();i++){
                if(form.getQuestionList().get(i).getId()==q.getId())
                {
                    matcher=i;
                }
            }



            Question tmp= form.questionList.get(matcher);
            if (qa.getType() == 1) {
                qa.setTextAnswer(tmp.getTmpString());
            }

            if (qa.getType() == 2) {
                qa.setRadioAnswer(tmp.getTmpInt());
            }
            if (qa.getType() == 3) {
                tmp.getCheckBoxAnswerList().removeIf(Objects::isNull);
                qa.setCheckBoxAnswer(tmp.getCheckBoxAnswerList());

            }
            fa.addAnswers(qa);


        }




        answerService.saveAnswers(fa);
return "thankyou";
        /*
        redirectAttributes.addFlashAttribute("successDto","Your answers has been registered");
        return "redirect:/loggedIn";


         */
    }
    /////////////////////////////Chooosing form

    @GetMapping("/chooseFormAndAnswers")
    public String chooseForm(Model model, HttpSession session) {

        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }


        List<Integer> lista = new ArrayList<>();
        for (Form i : formService.findAll()) {
            int e = (int) i.getFormId();
            lista.add(e);
        }

        model.addAttribute("chooseForm", lista);
        return "chooseFormAndAnswers";
    }

    /////////////////////////////Seeing form Answers

    @RequestMapping(value = "/showingFormAnswers", params = "id", method = RequestMethod.GET)
    public String showingForm(@RequestParam("id") long id, Model model, HttpSession session) {

        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }


        DtoFormAnswers tmpClassForPrint = new DtoFormAnswers();

        for (Form q : formService.findAll()) {
            if (id == q.getFormId()) {
                tmpClassForPrint.setForm(q);
                for (FormAnswer fa : answerService.findAnswers()) {
                    if (fa.getFormId() == q.getFormId()) {
                        tmpClassForPrint.addFormAnswer(fa);
                    }
                }

                model.addAttribute("dto", tmpClassForPrint.functionUltra4000());
                return "showingFormAnswers";
            }
        }

        return "error";

    }


    @GetMapping("/modify")
    public String chooseFormModify(Model model, HttpSession session) {


        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }


        smallDto dto = new smallDto();

        for (Form i : formService.findAll()) {
            int e = (int) i.getFormId();

            if (i.getActive()) {
                dto.addStrings(e, "Active");

            } else {
                dto.addStrings(e, "Inactive");
            }
            dto.addInt(e);
            //System.out.println("Form ID ="+e);
        }

        model.addAttribute("chooseForm", dto);
        return "modify";
    }


    @RequestMapping(value = "/modifyForm", params = "id", method = RequestMethod.GET)
    public String modifySpec(@RequestParam("activate") long activate, @RequestParam("id") long id, Model model, HttpSession session,RedirectAttributes redirectAttributes) throws Exception {

        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }

        //System.out.println("Form ID match ="+id);

        if (activate == 1) {

            if(formService.findingOne(id).getQuestionList().size()==0){
                redirectAttributes.addFlashAttribute("errorDto","You cannot activate a form without questions");
                return "redirect:/errorMessage";
            }


            formService.activate(id);
           // formService.findingOne(id).setActive(true);
        }

        if (activate == 0) {
            try {
                formService.delForm(id);
                answerService.delForm(id);
                return "modifyForm";
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        model.addAttribute("dto", id);
        redirectAttributes.addFlashAttribute("successDto","Form has been activated");
        return "redirect:/loggedIn";


    }

    @RequestMapping(value = "/modifyQuestion", params = "id", method = RequestMethod.GET)
    public String modifyQuestion(@RequestParam(value = "question") long question, @RequestParam("id") long id, Model model, HttpSession session,RedirectAttributes redirectAttributes) throws Exception {

        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }

        if (formService.findingOne(id).getActive()) {
            redirectAttributes.addFlashAttribute("errorDto","You can not edit an activated form");
            return "redirect:/errorMessage";
        }

        if(question!=404){

            formService.deletingQuestion(question,id);
           // formService.findingOne(id).getQuestionList().remove(formService.findingOne(id).indexCorrector((int)question));
        }
        model.addAttribute("dto", formService.findingOne(id));
        return "modifyQuestion";
    }

    @PostMapping("/modifyQuestion")
    public String modifyPost(@ModelAttribute ("dto")Form DTO, Model model,RedirectAttributes redirectAttributes) {


        formService.UpdatingQuestion(DTO);
        /*
        for(Question q:DTO.getQuestionList()){
            if(!q.getQuestion().equals(formService.findingOne(DTO.getFormId()).questionList.get(q.getId()).getQuestion())) {
                formService.findingOne(DTO.getFormId()).listSetter(DTO.questionList);

            }


        }

         */
            redirectAttributes.addAttribute("question", 404);
            redirectAttributes.addAttribute("id", DTO.getFormId());
            return "redirect:/modifyQuestion";

    }

    @GetMapping("review")
    public String review(@RequestParam ("id") long formId,Model model,HttpSession session,RedirectAttributes redirectAttributes) {


        try {
            if (!session.getAttribute("sessionPass").equals("hasRights"))
                return "adminDeny";
        } catch (Exception e) {
            return "adminDeny";
        }

        if (formService.findingOne(formId).getActive()) {
            redirectAttributes.addFlashAttribute("errorDto","You can not review an activated form");
            return "redirect:/errorMessage";
        }

        model.addAttribute("dto",  formService.findingOne(formId));
        return "review";
    }



    @GetMapping("errorMessage")
    public String error(Model model) {
        String some = (String) model.asMap().get("errorDto");
        model.addAttribute("error", new ErrorDto(some));
        return "errorMessage";
    }


    @GetMapping(value = "/get-file", params = "id", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody byte[] getFile(@RequestParam ("id") long id) throws IOException {


        DtoFormAnswers tmpClassForPrint = new DtoFormAnswers();

        for (Form q : formService.findAll()) {
            if (id == q.getFormId()) {
                tmpClassForPrint.setForm(q);
                for (FormAnswer fa : answerService.findAnswers()) {
                    if (fa.getFormId() == q.getFormId()) {
                        tmpClassForPrint.addFormAnswer(fa);
                    }
                }
            }
        }
        FinalDTO finalDTO = tmpClassForPrint.functionUltra4000();
        Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Form "+finalDTO.getForm().getFormId());
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);


            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            headerStyle.setFont(font);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Question");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Answer");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(2);
            headerCell.setCellValue("User");
            headerCell.setCellStyle(headerStyle);

            ///////////////////////////////////////////////////
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            int i=1;
            for (Map.Entry<String,Integer> entry : finalDTO.getQuestion().entrySet()) {

                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(finalDTO.getForm().getQuestionList().get(finalDTO.getForm().indexCorrector(entry.getValue())).getQuestion());
                cell.setCellStyle(style);
                i++;
                for (Map.Entry<String, String> entry2 : finalDTO.getQuestionAndUser().entrySet()) {
                    if (entry.getKey().equals(entry2.getKey())) {
                        cell = row.createCell(1);
                        cell.setCellValue(entry2.getValue());
                        cell.setCellStyle(style);
                        // i++;

                        cell = row.createCell(2);
                        cell.setCellValue(entry2.getKey().replaceAll("\\d",""));
                        cell.setCellStyle(style);
                        //i++;

                    }
                }

            }

            //////////////////////////////////////////////////
            //File currDir = new File(".");
            //String path = currDir.getAbsolutePath();
            //String temporaryDir = System.getProperty("java.io.tmpdir");
            //String fileLocation = temporaryDir+"temp.xlsx";
            // File currDir = new File( "../"+ "temp.xlsx");
           // File  file = new File(fileLocation);

            File file = File.createTempFile("temp", null);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            InputStream targetStream = new FileInputStream(file);
            byte[] test = toByteArray(targetStream) ;
            file.deleteOnExit();
            return test;

    }

    @GetMapping(value = "/copy", params = "id")
    public String copy(@RequestParam ("id") long id,RedirectAttributes redirectAttributes) throws IOException {

        long indexCount = 1;

        while (formService.existsDoubles(indexCount)) {
            indexCount++;
        }
        Form tmpForm = formService.findingOne(id);
        formService.saveForm(new Form(indexCount,tmpForm.getDescription(), tmpForm.getAnon(), false));

        for(Question q:tmpForm.questionList){
            formService.savingQuestion(q,indexCount);

        }
        redirectAttributes.addFlashAttribute("successDto","Form has been copied as 'Form "+indexCount+"'!");
        return "redirect:/loggedIn";
    }




    @RequestMapping("/thankyou")
    public String thanks() {
        return "thankyou";
    }

}


