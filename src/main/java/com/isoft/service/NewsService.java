package com.isoft.service;

import com.isoft.bean.Page;
import com.isoft.entity.News;

import java.util.List;

public interface NewsService {
    boolean add(News news);
    boolean delone(Integer id);
    boolean del(List<Integer> ids);
    boolean update(News news);
    Page getData(String title,Integer typeid,String pubdt,Integer page,Integer size,String orderby,String sort);
}
