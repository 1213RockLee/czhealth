package com.service.health.dao;

import com.service.health.pojo.Member;

public interface MemberDao {
    Member findMemberByTelephone(String telephone);

    void addNewMember(Member member);
}
