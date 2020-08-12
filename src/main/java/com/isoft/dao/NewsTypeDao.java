package com.isoft.dao;


import com.isoft.entity.NewsType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsTypeDao {
    @Select("select * from tb_type")
    List<NewsType> getAll();
}
