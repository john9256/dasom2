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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dasom2.service.KakaoService;
import com.dasom2.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class KakaoController {
    
    @Autowired
    private KakaoService kakaoService;
    
    @Value("${kakao.client-id}")
    private String clientId;
    
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    
    @Value("${kakao.logout.url}")
    private String kakaoLogoutUrl;
    
    @Value("${main.page.url}")
    private String mainPageUrl;
    
    @GetMapping("/login/kakao")
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = 
                "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId 
                + "&redirect_uri=" + redirectUri + "&response_type=code"
                ;
            
        return new RedirectView(kakaoLoginUrl);
    }
    
    @GetMapping("/login/callback")
    public RedirectView kakaoLoginCallback(@RequestParam String code, HttpSession session) {
        String accessToken = getAccessToken(code);
        
        UserVO user = getKakaoUser(accessToken);
        String userId = user.getKakaoId();
        session.setAttribute("userId", userId);
        session.setAttribute("accessToken", accessToken);
        
        if(kakaoService.checkUserExist(userId) > 0) {
        	return new RedirectView(mainPageUrl);
        }
        else {
        	kakaoService.saveUser(userId);
        }
        return new RedirectView(mainPageUrl);
     }
    
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
        System.out.println("아이디 값" + id);
        
        // 응답 데이터 로그 출력
        System.out.println("Kakao User Info Response: " + responseBody);

        UserVO user = new UserVO();
        user.setKakaoId(id.toString());
        return user;
    }
    
//    @GetMapping("/logout/kakao")
//    public RedirectView kakaoLogout(HttpSession session, HttpServletRequest request, RedirectAttributes attributes) {
//        String accessToken = (String) session.getAttribute("accessToken");
//        if (accessToken != null && logoutFromKakao(accessToken)) {
//            session.invalidate();
//            // 클라이언트 세션 명확히 종료
//            request.getSession(true).invalidate();
//            return new RedirectView("/login");
//        } else {
//            attributes.addFlashAttribute("errorMessage", "로그아웃 실패. 다시 시도해 주세요.");
//            return new RedirectView("/error");
//        }
//    }
//
//    private boolean logoutFromKakao(String accessToken) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//
//        HttpEntity<String> request = new HttpEntity<>(headers);
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(kakaoLogoutUrl, HttpMethod.POST, request, String.class);
//            return response.getStatusCode().is2xxSuccessful();
//        } catch (Exception e) {
//            System.err.println("Kakao Logout Error: " + e.getMessage());
//            return false;
//        }
//    }
    
    
    
}
