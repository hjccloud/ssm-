package com.he.servicce.impl;

import com.he.dao.UserDao;
import com.he.domain.Role;

import com.he.domain.UserInfo;
import com.he.servicce.UserService;
import com.he.utils.BCryptPasswordEncoderUtils;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
//@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addRoleToUser(Integer userId, Integer[] roleIds) throws Exception {

        for(Integer roleId:roleIds){
            userDao.addRoleToUser(userId,roleId);
        }
    }

    @Override
    public List<Role> findOtherRoles(Integer userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(Integer id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        User user =new User(userInfo.getUsername(),userInfo.getPassword(),getAuthority(userInfo.getRoles()));
        User user =new User(userInfo.getUsername(), userInfo.getPassword(),userInfo.getStatus()==0 ? false : true,true,true,true,getAuthority(userInfo.getRoles()));
        return user;

    }

    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list =new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }

        return list;

    }

    public static void main(String[] args) {
        String s = BCryptPasswordEncoderUtils.encodePassword("123");
        System.out.println(s);
    }


}
