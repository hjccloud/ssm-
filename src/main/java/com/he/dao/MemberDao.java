package com.he.dao;

import com.he.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface MemberDao {

    @Select("select * from member where id =#{id} ")
    public Member findById(Integer id) throws Exception;
}
