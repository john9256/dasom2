package com.dasom2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.dasom2.vo.LoginLogVO;

@Mapper
public interface LoginLogMapper {

    @Insert("INSERT INTO login_log(user_id, ip, createtime, successflag, count) VALUES(#{userId}, #{ip}, #{createtime}, #{successflag}, #{count})")
    void insertLog(LoginLogVO log);
    
    @Select("SELECT count FROM login_log WHERE username = #{userId} ORDER BY createtime DESC LIMIT 1")
    Integer getLatestCount(String userId);

    @Update("UPDATE login_log SET count = count + 1 WHERE username = #{userId}")
    void updateCount(String userId);
    
    @Select("SELECT count FROM login_log WHERE username = #{userId} ORDER BY createtime DESC LIMIT 1")
    Integer getLatestCountByUsername(String userId);
    
    @Select("SELECT count FROM login_log WHERE ip = #{ip} ORDER BY createtime DESC LIMIT 1")
    Integer getLatestCountByIp(String ip);
}
