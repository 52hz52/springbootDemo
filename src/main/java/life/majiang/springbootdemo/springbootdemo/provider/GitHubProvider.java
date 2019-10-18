package life.majiang.springbootdemo.springbootdemo.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.springbootdemo.springbootdemo.dto.AccessTokenDTO;
import life.majiang.springbootdemo.springbootdemo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 注解    对象自动实例化
@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaTypeJSON, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

            String[] splits = string.split("&");
            String tokenStr = splits[0];
            String token = tokenStr.split("=")[1];

            System.out.println(string);
            System.out.println("token ====> "+token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;

        }catch (IOException io){
            return null;
        }
    }




}
