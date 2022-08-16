package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor //생성자 주입
public class AnswerController {
    //@Autowired 필드 주입
    private final QuestionService questionService;
    private final AnswerService answerService;

    @RequestMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable int id, String content) {
        Question question = this.questionService.getQuestion(id);

        answerService.create(question, content);

        return "redirect:/question/detail/%d".formatted(id);
    }

}