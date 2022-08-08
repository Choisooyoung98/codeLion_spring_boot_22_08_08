package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public int plus(@RequestParam(defaultValue = "a, b값을 입력해주세요")int a, int b){
        return a + b;
    }

    @GetMapping("minus")
    @ResponseBody
    public int minus(@RequestParam(defaultValue = "a, b값을 입력해주세요")int a, int b){
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int increase() {
        ++id;
        return id;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String gugudan(Integer dan, Integer limit) {
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

    //@GetMapping("/mbti")

}
