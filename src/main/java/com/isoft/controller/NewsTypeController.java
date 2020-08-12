package com.isoft.controller;

import com.isoft.bean.ResponseData;
import com.isoft.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/sysnewstype")
public class NewsTypeController {
    @Autowired
    NewsTypeService newsTypeService;
    @GetMapping
    public ResponseData getAll(){
        return new ResponseData(
           0,
           "请求成功",
           newsTypeService.getAll()
        );
    }
}
