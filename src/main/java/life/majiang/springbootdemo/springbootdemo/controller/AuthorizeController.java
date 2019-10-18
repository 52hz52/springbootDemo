package life.majiang.springbootdemo.springbootdemo.controller;

import life.majiang.springbootdemo.springbootdemo.dto.AccessTokenDTO;
import life.majiang.springbootdemo.springbootdemo.dto.GithubUser;
import life.majiang.springbootdemo.springbootdemo.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

//    自动导入
    @Autowired
    private GitHubProvider GitHubProvider;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = GitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = GitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
