package com.fh.service;

import com.fh.model.ServerResponse;

public interface RecipientService {
  ServerResponse findRecipientList(Long memberId);
}
