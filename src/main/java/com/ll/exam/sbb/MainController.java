package com.ll.exam.sbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {
    static int id = -1;
    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담는다.
    @ResponseBody
    public String index() {
        System.out.println("Hello");
        return "Hello World!!!";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String showGet() {
        return """
               <form method="POST" action="/hello">
                    <input type="text" name="name">
                    <input type="submit" value="확인">
               </form>
               """;
    }

    @PostMapping("/hello")
    @ResponseBody
    public String showPost(@RequestParam(defaultValue = "입력된 값이 없습니다.") String name) {
        return name;
    }

    @GetMapping("plus")
    @ResponseBody
    public int showplus(@RequestParam(defaultValue = "a, b값을 입력해주세요")int a, int b){
        return a + b;
    }

    @GetMapping("minus")
    @ResponseBody
    public int showminus(@RequestParam(defaultValue = "a, b값을 입력해주세요")int a, int b){
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showincrease() {
        ++id;
        return id;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showgugudan(Integer dan, Integer limit) {
        if (dan == null) {
            dan = 9;
        }
        if (limit == null) {
            limit = 9;
        }
        Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showmbti(@PathVariable String name) {
        if (name == null) {
            name = "최수용";
        }
        String mbti = switch (name) {
            case "홍길동" -> "INFJ";
            case "홍길순" -> "ENTJ";
            case "최수용" -> "INFP";
            case "임꺽정" -> "ENTJ";
            default -> "모름";
        };
        return mbti;
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();

        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name,value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSesseion(@PathVariable String name, HttpServletRequest session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값이 %s 입니다.".formatted(name, value);
    }

}

