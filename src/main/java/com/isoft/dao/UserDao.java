package com.isoft.dao;

import com.isoft.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    @Select("select * from tb_users where id=#{id}")
    Users getById(@Param("id") Integer id);

    @Select( "select count(*) from tb_users where name=#{name}")
    int getNameCounts(@Param("name")String name) ;

    @Insert(" insert into tb_users  values (null,#{name},#{password},now(),null,null)")
    int add(Users users);

    @Select("select count(*) from tb_users where id=#{id} and password=#{password}")
    int getPassCounts(@Param("id")Integer id,@Param("password")String password);

    @Update(
            "<script>"+
                    "  update  tb_users" +
                    "        <set>" +
                    "            <if test=\"null!=password\">" +
                    "                password=#{password}," +
                    "            </if>" +
                    "            <if test=\"null!=photoUrl\">" +
                    "                photoUrl=#{photoUrl}," +
                    "            </if>" +
                    "        </set>" +
                    "        where id=#{id}"
                    +"</script>"
    )
    int update(Users users);

    @Select("select * from tb_users where name=#{name} and password=#{password}")
    Users getuser(@Param("name")String name, @Param("password") String password);

}
