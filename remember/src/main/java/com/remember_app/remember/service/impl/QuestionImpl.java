package com.remember_app.remember.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember_app.remember.entity.Question;
import com.remember_app.remember.mapper.QuestionMapper;
import com.remember_app.remember.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
