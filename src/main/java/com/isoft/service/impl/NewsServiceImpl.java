package com.isoft.service.impl;

import com.isoft.bean.Page;
import com.isoft.dao.CommentDao;
import com.isoft.dao.NewsDao;
import com.isoft.entity.News;
import com.isoft.service.NewsService;
import com.isoft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsDao newsDao;

    @Autowired
    CommentDao commentDao;

    @Override
    public boolean add(News news) {
        if (null == news || StringUtil.isEmpty(news.getTitle()) || StringUtil.isEmpty(news.getContent())){
            return false;
         }
        return newsDao.addNews(news)>0;
    }

    @Override
    public boolean delone(Integer id) {
        if (null==id) {
            return false;
        }
        int resultComment=commentDao.delByNewsid(id);
        int  resultNews=newsDao.del(id);
        return resultNews > 0 ;
    }

    @Override
    public boolean del(List<Integer> ids) {
        if (null==ids||ids.size()==0) {
            return false;
        }
        int resultComment=commentDao.delByNewsids(ids);
        int resultNews=newsDao.delMore(ids);
        return resultNews > 0 ;
    }

    @Override
    public boolean update(@RequestBody News news) {
        if (null == news ){
            System.out.println(news);
            return false;
        }
        return newsDao.update(news)>0;
    }

    @Override
    public Page getData(String title, Integer typeid, String pubdt, Integer page, Integer size, String orderby, String sort) {
        if (null == typeid || typeid < 1) {
            typeid = null;
        }
        if (StringUtil.isEmpty(title)) {
            title = null;
        }
        if (StringUtil.isEmpty(pubdt)) {
            pubdt = null;
        }
        int count = newsDao.getCount(title, pubdt, typeid);
        List<News> list = null;
        if (count > 0) {
            if (null == page || page < 1) {
                page = 1;
            }
            if (null == size || size < 1) {
                size = 10;
            }
            if (StringUtil.isEmpty(orderby)) {
                orderby = null;
            }
            if (null != sort && (
                    !sort.trim().equalsIgnoreCase("asc") &&
                    !sort.trim().equalsIgnoreCase("desc"))) {
                sort = "asc";
            }
            list = newsDao.get(title, pubdt, typeid, (page - 1) * size, size, orderby, sort);
        }
            return new Page(
                    count, (int) (Math.ceil(count * 1.0 / size)), page, size, list
            );

    }
}
