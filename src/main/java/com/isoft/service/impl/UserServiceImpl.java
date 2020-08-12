package com.isoft.service.impl;

import com.isoft.dao.UserDao;
import com.isoft.entity.Users;
import com.isoft.service.UserService;
import com.isoft.util.MD5Util;
import com.isoft.util.StringUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao ;
   @Override
    public Users getById(Integer id) {
        if(null == id || id < 1) {
            return null;
        }
        return userDao.getById(id) ;
    }

    @Override
    public boolean register(Users users) {
       if (null==users|| StringUtils.isEmptyOrWhitespaceOnly(users.getName()) || StringUtils.isEmptyOrWhitespaceOnly(users.getPassword())){
        return false;
       }
      if (getNameCount(users.getName())){
          return false;
      }
      users.setPassword(MD5Util.MD5(users.getPassword()));
      return userDao.add(users)>0;
    }

    @Override
    public Map<String, Object> getLogin(String name, String password) {
        if (StringUtils.isEmptyOrWhitespaceOnly(name) || StringUtils.isEmptyOrWhitespaceOnly(password)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        if (!getNameCount(name)){
            map.put("errCode",1);
            return map;
        }
        Users users=userDao.getuser(name, MD5Util.MD5(password));
        map.put("errCode",users==null? 2 : 0);
        map.put("data",users);
        return map;
    }

    @Override
    public boolean updatePhoto(Users users) {
       if (null==users|| users.getId()==null || users.getId()<1 || StringUtil.isEmpty(users.getPhotoUrl())) {
           return false;
       }

       return userDao.update(users)>0;
    }

    @Override
    public int updatePassword(Integer id,String oldpass,String newpass) {
        if (null==id || id < 1 || StringUtil.isEmpty(oldpass) || StringUtil.isEmpty(newpass)) {
            return 2;
        }
        if (userDao.getPassCounts(id,MD5Util.MD5(oldpass))<1){
            return 1;
        }
        Users users=new Users();
        users.setId(id);
        users.setPassword(MD5Util.MD5(newpass));
        return userDao.update(users) >0 ? 0 : 2;
    }

    @Override
    public boolean getNameCount(String name) {
        int a=userDao.getNameCounts(name);
        return a>0 ? true : false;
    }
}
