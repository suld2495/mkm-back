package kr.co.mkm.security;

import kr.co.mkm.login.service.UserVo;
import kr.co.mkm.util.PasswordEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDeSer;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserVo user = (UserVo) userDeSer.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        if(!matchPassword(password, user.getPassword())) {
            throw new BadCredentialsException(username);
        }

        if(!user.isEnabled()) {
            throw new BadCredentialsException(username);
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private boolean matchPassword(String loginPwd, String password) {
        PasswordEncoding encoding = new PasswordEncoding();
        return encoding.matches(loginPwd, password);
    }
}
