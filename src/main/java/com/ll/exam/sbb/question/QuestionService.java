package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import com.ll.exam.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(String kw, int page, String sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            default -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        if ( kw == null || kw.trim().length() == 0 ) {
            return questionRepository.findAll(pageable);
        }

        return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_author_username(kw, kw, kw, kw, kw, pageable);
    }

    public Question getQuestion(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    public void create(String subject, String content, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(author);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);

        questionRepository.save(question);
    }

    public void increaseHit(long id) {
        Question question = getQuestion(id);
        int hit = question.getHit();
        hit++;
        question.setHit(hit);
        questionRepository.save(question);
    }
}