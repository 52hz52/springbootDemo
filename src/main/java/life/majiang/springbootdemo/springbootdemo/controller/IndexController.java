package life.majiang.springbootdemo.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
//   一个斜杠  表示根目录  就是默认访问页面
    @GetMapping("/")
    public String index() {
        return "index";

    }

}
