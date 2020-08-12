package com.isoft.controller;

import com.isoft.bean.ResponseData;
import com.isoft.entity.Users;
import com.isoft.service.UserService;
import com.isoft.util.FileUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService ;


    String path="userphoto/";

    @PostMapping
    public ResponseData register(String uname,String upass){
        Users users=new Users();
        users.setName(uname);
        users.setPassword(upass);
        boolean date=userService.register(users);
        return new ResponseData(
                date ? 0 : 1,
                date ? "注册成功":"注册失败",
                date
        );
    }

    @PutMapping("pws")
    public ResponseData updatePass(@RequestBody Map<String,Object> map){
        int r=-1;
        try {
            r=userService.updatePassword((Integer) map.get("id"),(String) map.get("oldpass"),(String)map.get("newpass"));

        }catch (Exception e){
            e.printStackTrace();
            r=2;
        }
        String msg="";
        switch (r){
            case 0:msg="修改成功";break;
            case 1:msg="原密码错误";break;
            case 2:msg="修改失败";break;
        }
        System.out.println("11111");
        return new ResponseData(r,msg,r==0?true:false);

    }


    @PostMapping("photo")
    public ResponseData upPhoto(Integer id, @RequestParam("userphoto") MultipartFile file, HttpServletRequest request) {
        if (null==file){
            return new ResponseData(-1,"上传文件为空",null);
        }
        String oriFilename =file.getOriginalFilename();
        String extName=oriFilename.substring(oriFilename.lastIndexOf("."));
        String newFileName= new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date())+"_"+id + extName;


        boolean isOk=FileUtil.saveUpFile(request,path,newFileName,file);

        String photoUrl=null;
        if (isOk) {
            photoUrl = FileUtil.url(request, path, newFileName);
            //更改数据库
            Users users=new Users();
            users.setId(id);
            users.setPhotoUrl(newFileName);
            isOk =userService.updatePhoto(users);

        }
        ResponseData responseData=new ResponseData();
        responseData.setErrCode(isOk?0:-1);
        responseData.setMsg(isOk?"上传成功":"上传失败");
        responseData.setData(photoUrl);
        return responseData;
    }

    @GetMapping("/{name}/{password}")
    public ResponseData login(@PathVariable("name")String name,@PathVariable("password")String password,HttpServletRequest request ) {
        Map<String, Object> map = userService.getLogin(name, password);
        int errCode = -1;
        Users users = null;
        if (null == map) {
            errCode = 2;
        } else {
            errCode = (Integer) map.get("errCode");
            if (null != map.get("data")) {
                try {
                    users = (Users) map.get("data");
                } catch (Exception e) {
                    users = null;
                }
            }
        }
        if (null != users && users.getPhotoUrl()!=null) {
            users.setPhotoUrl(FileUtil.url(request, path, users.getPhotoUrl()));
        }
        String msg = "";
        switch (errCode) {
            case 0:
                msg = "登陆成功";
                break;
            case 1:
                msg = "用户不存在";
                break;
            case 2:
                msg = "密码错误";
                break;
        }
        return new ResponseData(errCode, msg, users);
    }

    @GetMapping
    public Map<String, Boolean> namecheck(@Param("registerName") String name){
        boolean u=userService.getNameCount(name);
        Map<String,Boolean> map=new HashMap<>();
        map.put("valid",u);
        return map;
    }
}
