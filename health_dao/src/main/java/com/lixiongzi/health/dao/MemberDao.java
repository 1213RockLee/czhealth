package com.lixiongzi.health.dao;

import com.lixiongzi.health.pojo.Member;

public interface MemberDao {
    Member findMemberByTelephone(String telephone);

    void addNewMember(Member member);
}
