package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.service.system.AdminService;
import com.qingcheng.service.system.ResourceService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailServiceImpl implements UserDetailsService {


    @Reference
    private AdminService adminService;

    @Reference
    private ResourceService resourceService;


    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("经过了UserDetailServiceImpl");

        Map map=new HashMap<>();
        map.put("loginName",s);
        map.put("status","1");
        List<Admin> list = adminService.findList(map);
        for (Admin admin : list) {
            System.out.println("用户"+admin);
        }
        if(list.size()==0){
            return null;
        }
        //添加权限
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> resKeys = resourceService.findResourceByName(s);
        for (String resKey : resKeys) {
            //把该用户的所有权限添加进去
            grantedAuthorities.add(new SimpleGrantedAuthority(resKey));
        }
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        //返回权限用户，用户名，密码，权限
        return new User(s,list.get(0).getPassword(),grantedAuthorities);
    }
}
