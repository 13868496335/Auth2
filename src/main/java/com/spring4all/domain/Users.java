package com.spring4all.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * 用户信息表
 * @Author by double.
 * @Date: 2018/10/9
 * @remarks:
 */

@Data
public class Users {

    private  Integer id ;
    private  String  name;
    @Length(min=6,message="账号不能少于6位")
    private  String  account;
    @Length(min=6,message="账号不能少于6位")
    private  String  password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
