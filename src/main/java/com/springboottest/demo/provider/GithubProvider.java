package com.springboottest.demo.provider;

import com.alibaba.fastjson.JSON;
import com.springboottest.demo.dto.AccessTokenDTO;
import com.springboottest.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();
                System.out.println(string);
                String[] split=string.split("&");//通过&把字符串拆分
                String tokenstr=split[0];
                String token=tokenstr.split("=")[1];//通过=把拆分字符串的第一个再拆分，拆分后的后面一个字符串就是需要的token
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        //获取通过github登录用户信息
        public GithubUser getUser(String accessToken){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
            try {
                Response response = client.newCall(request).execute();
                String string=response.body().string();
                //把string的一个json对象自动地转化解析为java的一个类对象
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

