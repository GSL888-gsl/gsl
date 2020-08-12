package com.isoft.dao;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    @Delete("delete from tb_comment where newsid=#{newsId}")
    int delByNewsid(Integer newsId);


    @Delete("<script>"+
            " delete from tb_comment" +
            "         where newsid in" +
            "         <foreach collection=\"list\" item=\"id\" open=\"(\" close=\")\" separator=\",\">" +
            "             #{id}" +
            "         </foreach>"
            +"</script>"
    )

    int delByNewsids(List<Integer> ids);

}
