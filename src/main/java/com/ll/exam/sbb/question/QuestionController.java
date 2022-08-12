package com.ll.exam.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController
{
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/question/list")
    public String list() {
        return "question_list";
    }
}