package com.remember_app.remember.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember_app.remember.entity.QuestionBank;
import com.remember_app.remember.mapper.QuestionBankMapper;
import com.remember_app.remember.service.QuestionBankService;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {

}