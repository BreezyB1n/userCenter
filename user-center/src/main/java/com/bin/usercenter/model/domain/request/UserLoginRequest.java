package com.bin.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author B1n_
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -4253063728374818031L;
    private String userAccount;
    private String userPassword;
}
