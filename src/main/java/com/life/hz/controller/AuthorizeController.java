package com.life.hz.controller;

import com.life.hz.dto.AccessTokenDTO;
import com.life.hz.dto.GithubUser;
import com.life.hz.model.User;
import com.life.hz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

//    自动导入
    @Autowired
    private com.life.hz.provider.GitHubProvider GitHubProvider;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUri;

    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state,
                            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = GitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = GitHubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        if(null!=githubUser && githubUser.getId()!=null){
            //登录成功  写 session 和 cookie
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setBio(githubUser.getBio());
            System.out.println("====================== bio : "+githubUser.getBio());
            userService.createOrUpdate(user);
            Cookie tokenCookies = new Cookie("token", token);
            response.addCookie(tokenCookies);
            System.out.println("tokenCookies  === "+tokenCookies);
            //从定向到 index
            return "redirect:/";
        }else {
            //登录失败  重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
//      清除 session 和  cokkie
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
