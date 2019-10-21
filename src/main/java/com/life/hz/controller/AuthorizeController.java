package com.life.hz.controller;

import com.life.hz.dto.AccessTokenDTO;
import com.life.hz.dto.GithubUser;
import com.life.hz.mapper.UserMapper;
import com.life.hz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

//    自动导入
    @Autowired
    private com.life.hz.provider.GitHubProvider GitHubProvider;

    @Autowired
    private UserMapper userMapper;


    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


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
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);

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
}
