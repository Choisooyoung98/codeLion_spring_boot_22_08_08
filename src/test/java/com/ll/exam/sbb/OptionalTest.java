package com.ll.exam.sbb;

import com.ll.exam.sbb.answer.AnswerRepository;
import com.ll.exam.sbb.question.QuestionRepository;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserRepository;
import com.ll.exam.sbb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class OptionalTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    public static void createSampleData(UserService userService) {
        userService.create("admin", "admin@test.com", "1234");
        userService.create("user1", "user1@test.com", "1234");
    }

    private void createSampleData() {
        createSampleData(userService);
    }

    public static void clearData(UserRepository userRepository, AnswerRepository answerRepository, QuestionRepository questionRepository) {
        answerRepository.deleteAll();
        answerRepository.truncateTable();

        questionRepository.deleteAll();
        questionRepository.truncateTable();

        userRepository.deleteAll();
        userRepository.truncateTable();
    }

    private void clearData() {
        clearData(userRepository, answerRepository, questionRepository);
    }

    /* testcase 시작 */
    @Test
    @DisplayName("NullPointerException Test")
    public void v1() {
        SiteUser s1 = userRepository.findById(3L);
        if (s1 == null) {
            System.out.println("null입니다." + s1); // s1.getId() 를 사용하면 NullPointerException 오류 발생
            assertThat(s1).isEqualTo(null);
        }
        SiteUser s2 = userRepository.findById(2L);
        if (s2 == null) {
            System.out.println("null입니다." + s2);
        }
        else if (s2 != null) {
            System.out.println("s2는 null이 아닙니다." + s2.getId());
            assertThat(s2.getUsername()).isEqualTo("user1");
        }

    }
    @Test
    @DisplayName("Optional.empty() 사용")
    public void v2() {
        Optional<SiteUser> os1 = Optional.empty();
        System.out.println(os1);
        System.out.println(os1.isPresent());
    }

    @Test
    @DisplayName("Optional.of() 사용")
    public void v3() {
        Optional<SiteUser> os1 = Optional.of(userRepository.findByEmail("admin@test.com")); // 없는 값을 입력하면 에러
        if (os1.isPresent()){
            System.out.println("null이 아닙니다.");
        }
        else System.out.println("null 입니다.");
    }

    @Test
    @DisplayName("Optional.ofNullable() 사용")
    public void v4() {
        Optional<SiteUser> os1 = Optional.ofNullable(userRepository.findByEmail("admin"));
        Optional<SiteUser> os2 = Optional.ofNullable(userRepository.findByEmail("admin@test.com"));

        if (os1.isPresent()){
            System.out.println("os1은 null이 아닙니다.");
        }
        else System.out.println("os1은 null 입니다.");

        if (os2.isPresent()){
            System.out.println("os2는 null이 아닙니다.");
        }
        else System.out.println("os2는 null 입니다.");
    }

    @Test
    @DisplayName("Optional에 ifPresent()")
    public void v5() {
        Optional<SiteUser> os1 = Optional.ofNullable(userRepository.findByEmail("admin"));
        Optional<SiteUser> os2 = Optional.ofNullable(userRepository.findByEmail("admin@test.com"));

        os1.ifPresent(a -> {
            System.out.println("os1은 null이 아닙니다.");
        });
        os2.ifPresent(b -> {
            System.out.println("os2는 null이 아닙니다.");
        });
    }

    @Test
    @DisplayName("Optional.ofNullable에 null인경우 orElse와 orElseGet")
    public void v6() {
        SiteUser s1 = Optional.ofNullable(userRepository.findByEmail("admin")).orElse(orElse구문());
        SiteUser s2 = Optional.ofNullable(userRepository.findByEmail("admin")).orElseGet(this::orElse구문);
        System.out.println("s1 : " + s1.getId());
        System.out.println("s2 : " + s2.getId());   // orElse구문 함수의 반환값만 받는다.

    }

    @Test
    @DisplayName("Optional.ofNullable에 null이 아닌경우 orElse와 orElseGet")
    public void v7() {
        SiteUser s1 = Optional.ofNullable(userRepository.findByEmail("admin@test.com")).orElse(orElse구문());
        SiteUser s2 = Optional.ofNullable(userRepository.findByEmail("admin@test.com")).orElseGet(this::orElse구문);
        System.out.println("s1 : " + s1.getId());   // orElse구문 함수가 실행된다.
        System.out.println("s2 : " + s2.getId());

    }

    private SiteUser orElse구문(){
        SiteUser s1 = userRepository.findById(2L);
        System.out.println("orElse구문 실행");
        return s1;
    }
}
