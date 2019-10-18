package life.majiang.springbootdemo.springbootdemo.controller;

import life.majiang.springbootdemo.springbootdemo.dto.AccessTokenDTO;
import life.majiang.springbootdemo.springbootdemo.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

//    自动导入
    @Autowired
    private GitHubProvider GitHubProvider;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state){
        GitHubProvider.getAccessToken(new AccessTokenDTO());
        return "index";
    }
}
