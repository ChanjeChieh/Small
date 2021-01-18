package com.small.service;

import com.small.common.ServerResponse;
import com.small.pojo.User;

/**
 * Created by skdwj on 2020/3/2.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkVaild(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    //backend
    ServerResponse checkAdminRole(User user);
}
