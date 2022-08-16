package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Optional<Question> oq = questionRepository.findById(id);

        // return oq.orElseThrow(DataNotFoundException); // 만약에 없으면 예외를 발생시켜라

        if (oq.isPresent()) {
            return oq.get();
        }
        throw new DataNotFoundException("question not found"); // 실행이 되면 더 이상 진행하지 못함
    }
}
