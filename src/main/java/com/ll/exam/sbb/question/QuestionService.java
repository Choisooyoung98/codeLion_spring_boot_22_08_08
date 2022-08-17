package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found".formatted(id))); // 만약에 없으면 예외를 발생시켜라

        /*
        기존 방식
        Optional<Question> oq = questionRepository.findById(id);

        if (oq.isPresent()) {
            return oq.get();
        }
        throw new DataNotFoundException("question not found"); // 실행이 되면 더 이상 진행하지 못함
        */
    }

    public void create(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }
}
