package kr.co.mkm.login.service;

import java.util.Map;

public interface LoginService {
    void insertUser(UserVo userVo) throws Exception;

    UserVo getUserWithProvider(Map param) throws Exception;

    String searchId(UserVo userVo) throws Exception;

    String searchPassword(UserVo userVo) throws Exception;

    void updatePassword(UserVo userVo) throws Exception;

    void insertRefreshToken(String refresh) throws Exception;

    void deleteRefreshToken();

    String getRefreshToken();
}
