package com.isoft.service.impl;

import com.isoft.dao.NewsTypeDao;
import com.isoft.entity.NewsType;
import com.isoft.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NewsTypeServiceImpl implements NewsTypeService {
    @Autowired
    NewsTypeDao newsTypeDao;
    @Override
    public List<NewsType> getAll() {
        return newsTypeDao.getAll();
    }
}
