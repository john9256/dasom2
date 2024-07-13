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
    
    @GetMapping("/login/kakao")
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = 
                "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId 
                + "&redirect_uri=" + redirectUri + "&response_type=code"
//                		+ "&scope=talk_message"
                ;
            
        return new RedirectView(kakaoLoginUrl);
    }
    
    @GetMapping("/login/callback")
    public String kakaoLoginCallback(@RequestParam String code, HttpSession session) {
        String accessToken = getAccessToken(code);
        System.out.println("accessTOKEN = " + accessToken);
        session.setAttribute("accessToken", accessToken);
        
        UserVO user = getKakaoUser(accessToken);

//        // 프로필 정보가 없으면 추가 권한 요청
//        if (user == null) {
//            return "redirect:/requestAdditionalPermissions";
//        }
//
//        System.out.println(user);
//        kakaoService.saveUser(user);
        return "mainPage";
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
 
    @GetMapping("/logout/kakao")
    public String kakaoLogout(HttpSession session) {
        boolean isLoggedOut = logoutFromKakao(session.getAttribute("accessToken").toString());
        if (isLoggedOut) {
            return "redirect:/login";
        } else {
            return "<script>alert('로그아웃 실패. 다시 시도해 주세요.'); history.back();</script>";
        }
    }
    
    private boolean logoutFromKakao(String accessToken) {
        String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.POST, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            System.out.println("Kakao Logout Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean checkMessagePermission(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
       
        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/scopes", HttpMethod.GET, request, String.class);
        // 권한 체크 로직
        return response.getBody().contains("talk_message");
    }
    
    private UserVO getKakaoUser(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, String.class);

        String responseBody = response.getBody();
        
        // 응답 데이터 로그 출력
        System.out.println("Kakao User Info Response: " + responseBody);

//        JSONObject jsonObject = new JSONObject(responseBody);
//        
//        // 카카오 계정 정보가 없을 경우를 대비한 예외 처리
//        if (!jsonObject.has("kakao_account")) {
//            return null;
//        }
//
//        Long id = jsonObject.getLong("id");
//        System.out.println("아이디 값" + id);
//        JSONObject kakaoAccount = jsonObject.getJSONObject("kakao_account");

//        // 프로필 정보가 없을 경우를 대비한 예외 처리
//        if (!kakaoAccount.has("profile")) {
//            return null;
//        }

//        JSONObject profile = kakaoAccount.getJSONObject("profile");
//        String uuid = profile.optString("uuid", null);
//        System.out.println("uuid 값" + uuid);
        
        UserVO user = new UserVO();
//        user.setKakaoId(id.toString());
//        user.setKakaoUuid(uuid);
        return user;
    }
    
    @GetMapping("/requestAdditionalPermissions")
    public RedirectView requestAdditionalPermissions() {
        String reAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId
                + "&redirect_uri=" + redirectUri + "&response_type=code&scope=profile_nickname";
        
        return new RedirectView(reAuthUrl);
    }
    
    @GetMapping("/sendMessage")
    public @ResponseBody String sendMessage(@RequestParam String idOrUuid) {
        String message = "Hello from Kakao API!";
        boolean result = sendKakaoMessage(idOrUuid, message);
        return result ? "메시지 전송 완료" : "메시지 전송 실패";
    }
    
    private boolean sendKakaoMessage(String idOrUuid, String message) {
        String messageUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "YOUR_ACCESS_TOKEN");

        JSONObject templateObject = new JSONObject();
        templateObject.put("object_type", "text");
        templateObject.put("text", message);
        templateObject.put("link", new JSONObject().put("web_url", "http://yourwebsite.com"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("template_object", templateObject);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(messageUrl, HttpMethod.POST, request, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }
}
