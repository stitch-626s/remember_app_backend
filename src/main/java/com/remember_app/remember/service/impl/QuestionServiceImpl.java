package com.remember_app.remember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember_app.remember.entity.Question;
import com.remember_app.remember.mapper.QuestionMapper;
import com.remember_app.remember.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Override
    public List<Question> getQuestionsByQbId(Integer qbId) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQbId, qbId);
        return this.list(queryWrapper);
    }
}
