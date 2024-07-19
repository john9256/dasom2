package com.dasom2.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.dasom2.service.KakaoService;
import com.dasom2.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class KakaoController {
    
    @Autowired
    private KakaoService kakaoService;
    
    @Value("${kakao.client-id}")
    private String clientId;
    
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    
    @Value("${main.page.url}")
    private String mainPageUrl;
    
    // 카카오로 api 호출
    @GetMapping("/login/kakao")
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = 
                "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId 
                + "&redirect_uri=" + redirectUri + "&response_type=code"
                ;
            
        return new RedirectView(kakaoLoginUrl);
    }
    
    // api 에서 받은 data insert
    @GetMapping("/login/callback")
    public RedirectView kakaoLoginCallback(@RequestParam String code, HttpSession session) {
        String accessToken = getAccessToken(code);
        
        UserVO user = getKakaoUser(accessToken);
        String userId = user.getKakaoId();
        String profileImageUrl = user.getProfileImageUrl();
        
        session.setAttribute("userId", userId);
        session.setAttribute("accessToken", accessToken);
        
        if(kakaoService.checkUserExist(userId) > 0) {
        	kakaoService.updateUser(profileImageUrl);
        	return new RedirectView(mainPageUrl);
        }
        else {
        	kakaoService.saveUser(userId, profileImageUrl);
        }
        return new RedirectView(mainPageUrl);
     }
    
    // 토큰 발급
    private String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        String requestBody = "grant_type=authorization_code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&code=" + code;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("access_token");
    }
    
    // 카카오로 부터 data get
    private UserVO getKakaoUser(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, String.class);

        String responseBody = response.getBody();
        
        JSONObject jsonObject = new JSONObject(responseBody);
        
        Long id = jsonObject.getLong("id");
        
        // Extract profile image URL
        JSONObject kakaoAccount = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakaoAccount.getJSONObject("profile");
        String profileImageUrl = profile.getString("profile_image_url");
        
        UserVO user = new UserVO();
        user.setKakaoId(id.toString());
        user.setProfileImageUrl(profileImageUrl);
        return user;
    }
    
}
