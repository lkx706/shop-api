package com.fh.service;

import com.fh.model.DataTableResult;
import com.fh.model.Member;
import com.fh.model.ServerResponse;

import java.util.List;

public interface MemberService {
  ServerResponse addMember(Member member) throws Exception;


  ServerResponse queryMemberName(String memberName);

  ServerResponse login(String memberName, String memberPwd);
}
