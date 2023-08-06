package kr.co.mkm.login.web;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;
import kr.co.mkm.login.service.KakaoLoginBO;
import kr.co.mkm.login.service.LoginService;
import kr.co.mkm.login.service.NaverLoginBO;
import kr.co.mkm.login.service.UserVo;
import kr.co.mkm.util.EmailUtil;
import kr.co.mkm.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private NaverLoginBO naverLoginBO;
    private KakaoLoginBO kakaoLoginBO;
    private LoginService loginService;
    private Gson gson;

    @Autowired
    private EmailUtil emailUtil;

    private String apiResult = null;

    public LoginController(NaverLoginBO naverLoginBO, KakaoLoginBO kakaoLoginBO, LoginService loginService, Gson gson) {
        this.naverLoginBO = naverLoginBO;
        this.kakaoLoginBO = kakaoLoginBO;
        this.loginService = loginService;
        this.gson = gson;
    }

    @RequestMapping("/login")
    public String login(Model model, HttpSession session, HttpServletRequest request) throws Exception {
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);

        model.addAttribute("naverUrl", naverAuthUrl);
        model.addAttribute("kakaoUrl", kakaoLoginBO.getKakaoURL());
        model.addAttribute("category", 6);
        model.addAttribute("urlName", "회원로그인");
        return "/sub/login/login";
    }

    @RequestMapping("kakao-callback")
    public String kakaoCallback(Model model, @RequestParam String code) throws Exception {
        String access_Token = null;
        Map<String, Object> info = null;
        UserVo user = null;
        
        try {
            access_Token = kakaoLoginBO.getAccessToken(code);
            info = kakaoLoginBO.getUserInfo(access_Token);
        } catch (Exception e) {
            return "redirect:/login?error=kakao_error";
        }

        if (info.get("result").equals("success")) {
            Map param = new HashMap();
            param.put("provider", "kakao");
            param.put("id", info.get("id"));
            user = loginService.getUserWithProvider(param);

            if (user == null) {
                user = new UserVo();

                String email = info.get("email") != null &&  !"".equals(info.get("email")) ? (String) info.get("email") : "-";

                user.setEmail(email);
                user.setName((String) info.get("nickname"));
                user.setPassword("1");
                user.setServiceYN("Y");
                user.setPrivacyYN("Y");
                user.setProvider("kakao");
                user.setId((String) info.get("id"));
                user.setPhone("-");
                loginService.insertUser(user);
            }
        } else {
            return "redirect:/login?error=kakao_error";
        }

        authLogin(user);
        return "redirect:/";
    }

    @RequestMapping("naver-callback")
    public String naverCallback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws Exception {
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        apiResult = naverLoginBO.getUserProfile(oauthToken); //String형식의 json데이터
        Map result = gson.fromJson(apiResult, Map.class);
        UserVo user = null;

        if (result.get("message").equals("success")) {
            Map info = (Map) result.get("response");

            Map param = new HashMap();
            param.put("provider", "naver");
            param.put("id", info.get("id"));

            user = loginService.getUserWithProvider(param);

            if (user == null) {
                user = new UserVo();
                user.setUniqueId((String) info.get("id"));
                user.setEmail("-");
                user.setName((String) info.get("name"));
                user.setPassword("1");
                user.setServiceYN("Y");
                user.setPrivacyYN("Y");
                user.setProvider("naver");
                user.setId((String) info.get("id"));
                user.setPhone("-");
                loginService.insertUser(user);
            }
        } else {
            return "redirect:/login?error=naver_error";
        }

        authLogin(user);
        return "redirect:/";
    }

    private void authLogin(UserVo userVo) {
        List<GrantedAuthority> roles = new ArrayList<>(1);
        String roleStr = "ROLE_USER";
        roles.add(new SimpleGrantedAuthority(roleStr));

        Authentication auth = new UsernamePasswordAuthenticationToken(userVo, null, roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @RequestMapping("/member_agreement")
    public String memberAgreement(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "회원가입");
        return "/sub/member/memberAgreement";
    }

    @RequestMapping(value = "/member_input", method = RequestMethod.POST)
    public String memberInput(Model model, @RequestParam Map map, @ModelAttribute(value = "userVo") UserVo userVo) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "회원가입");
        model.addAttribute("agree", map);
        return "/sub/member/member";
    }

    @RequestMapping(value = "/member_insert", method = RequestMethod.POST)
    public String memberComplete(Model model, @ModelAttribute(value = "userVo") @Valid UserVo userVo, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "/sub/member/member";
        }

        loginService.insertUser(userVo);

        model.addAttribute("category", 7);
        model.addAttribute("urlName", "회원가입");
        return "redirect:/member_complete?id=" + userVo.getId();
    }

    @RequestMapping(value = "/member_complete", method = RequestMethod.GET)
    public String memberComplete(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "회원가입");
        return "/sub/member/memberComplete";
    }

    @RequestMapping(value = "/member_naver", method = RequestMethod.GET)
    public String memberNaver(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "회원가입");
        return "/sub/member/memberNaver";
    }

    @RequestMapping("/member_kakao")
    public String memberKakao(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "회원가입");
        return "/sub/member/memberKakao";
    }

    @ResponseBody
    @RequestMapping("id_check")
    public boolean checkId() {
        return true;
    }

    @RequestMapping(value = "id-search", method = RequestMethod.GET)
    public String idSearch(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "아이디/비밀번호 찾기");
        return "/sub/login/idSearch";
    }

    @RequestMapping(value = "id-complete", method = RequestMethod.GET)
    public String idComplete(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "아이디/비밀번호 찾기");
        return "/sub/login/idComplete";
    }

    @RequestMapping(value = "/id-search", method = RequestMethod.POST)
    public String idSearchPost(Model model, @ModelAttribute(value = "userVo") @Valid UserVo userVo, BindingResult result) throws Exception {
        String id = loginService.searchId(userVo);

        if (id == null || "".equals(id)) {
            return "redirect:/id-search?result=fail";
        }

        model.addAttribute("category", 7);
        model.addAttribute("urlName", "아이디/비밀번호 찾기");
        return "redirect:/id-complete?id=" + id;
    }

    @RequestMapping("password-search")
    public String passwordSearch(Model model) {
        model.addAttribute("category", 7);
        model.addAttribute("urlName", "아이디/비밀번호 찾기");
        return "/sub/login/passwordSearch";
    }

    @RequestMapping(value = "password-search", method = RequestMethod.POST)
    public String passwordSearchPost(Model model, @ModelAttribute(value = "userVo") UserVo userVo) throws Exception {
        String password = StringUtil.randomString();
        String id = loginService.searchPassword(userVo);

        if (id == null || "".equals(id)) {
            return "redirect:/password-search?result=fail";
        }

        Map map = new HashMap();
        map.put("email", userVo.getEmail());
        map.put("subject", "[미소원치과] 패스워드를 보내드립니다.");
        map.put("contents", "<div>미소원 치과에서 임시 패스워드를 보내드립니다.</div><br>" +
                "<div>임시 비밀번호 : " +  password + "</div>");

        emailUtil.emailSend(map);

        model.addAttribute("category", 7);
        model.addAttribute("urlName", "아이디/비밀번호 찾기");
        return "redirect:/login";
    }
}
