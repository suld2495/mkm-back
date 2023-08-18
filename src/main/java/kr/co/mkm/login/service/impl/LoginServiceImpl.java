package kr.co.mkm.login.service.impl;

import kr.co.mkm.login.service.LoginService;
import kr.co.mkm.login.service.UserVo;
import kr.co.mkm.mapper.UserMapper;
import kr.co.mkm.util.EncodingUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private UserMapper userMapper;

    public LoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void insertUser(UserVo userVo) throws Exception {
        userVo.setPassword(EncodingUtil.encode(userVo.getPassword()));
        userMapper.insertUser(userVo);
    }

    @Override
    public UserVo getUserWithProvider(Map param) throws Exception {
        return userMapper.getUserWithProvider(param);
    }

    @Override
    public String searchId(UserVo userVo) throws Exception {
        return userMapper.searchId(userVo);
    }

    @Override
    public String searchPassword(UserVo userVo) throws Exception {
        return userMapper.searchPassword(userVo);
    }

    @Override
    public void updatePassword(UserVo userVo) throws Exception {
        userMapper.updatePassword(userVo);
    }

    @Override
    public void insertRefreshToken(String refresh) throws Exception {
        userMapper.insertRefreshToken(refresh);
    }

    @Override
    public void deleteRefreshToken() {
        userMapper.deleteRefreshToken();
    }

    @Override
    public String getRefreshToken() {
        return userMapper.getRefreshToken();
    }
}
