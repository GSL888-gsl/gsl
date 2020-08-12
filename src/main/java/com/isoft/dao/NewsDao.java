package com.isoft.dao;

import com.isoft.entity.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao {
    //查询个数
    @Select("<script>"+
            "select count(*) from tb_news" +
            "        <where>" +
            "            <if test=\"null!=typeid\">" +
            "                and typeid=#{typeid}" +
            "            </if>" +
            "            <if test=\"null!=title\">" +
            "            <bind name=\"titleKey\" value=\"'%'+title+'%'\"/>" +
            "                and title like #{titleKey}" +
            "            </if>" +
            "            <if test=\"null!=pubdt\">" +
            "                and DATE_FORMAT(pubdatetime,'%Y-%m-%d')=#{pubdt}" +
            "            </if>" +
            "        </where>"
            +"</script>"
    )
    int getCount(@Param("title") String title,@Param("pubdt") String pubdt,@Param("typeid") Integer typeid);
    //查询指定个数
    @Select("<script>"+
             "SELECT tb_news.*,tb_type.typename" +
            "        FROM tb_news INNER JOIN tb_type ON tb_news.typeid=tb_type.id" +
            "        <where>" +
            "            <if test=\"null!=typeid\">" +
            "                and typeid=#{typeid}" +
            "            </if>" +
            "            <if test=\"null!=title\">" +
            "            <bind name=\"titleKey\" value=\"'%'+title+'%'\"/>" +
            "                and title like #{titleKey}" +
            "            </if>" +
            "            <if test=\"null!=pubdt\">" +
            "                and DATE_FORMAT(pubdatetime,'%Y-%m-%d')=#{pubdt}" +
            "            </if>" +
            "        </where>" +
            "        <if test=\"null !=orderby\">" +
            "            ORDER BY ${orderby}" +
            "            <if test=\"null !=sort\">" +
            "                ${sort}" +
            "            </if>" +
            "        </if>" +
            "        limit #{offset},#{rows}"
            +"</script>"
    )
    List<News> get(@Param("title") String title,@Param("pubdt") String pubdt,
                   @Param("typeid") Integer typeid,@Param("offset") Integer offset,
                   @Param("rows") Integer rows,@Param("orderby") String orderby,@Param("sort")String sort);
@Delete("<script>"+
        " delete from tb_news" +
        "         where id in" +
        "         <foreach collection=\"list\" item=\"id\" open=\"(\" close=\")\" separator=\",\">" +
        "             #{id}" +
        "         </foreach>"
        +"</script>"
)
    int delMore(List<Integer> ids);

@Delete("delete from tb_news where id=#{id}")
    int del(Integer id);

@Insert("insert into tb_news values(null,#{typeid},#{title},#{content},now(),#{comefrom})")
      int addNews(News news);

@Update("<script>"+
        "  update  tb_news" +
        "        <set>" +
        "            <if test=\"null!=comefrom\">" +
        "                comefrom=#{comefrom}," +
        "            </if>" +
        "            <if test=\"null!=content\">" +
        "                content=#{content}," +
        "            </if>" +
        "        </set>" +
        "        where id=#{id}"
         +"</script>"
)
    int update(News news);
}
