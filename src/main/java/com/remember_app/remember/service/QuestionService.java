package com.remember_app.remember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.remember_app.remember.entity.Question;
import java.util.List;

public interface QuestionService extends IService<Question> {
    /**
     * 根据题库 ID 查询所有题目
     * @param qbId 题库ID
     * @return 题目列表
     */
    List<Question> getQuestionsByQbId(Integer qbId);
}
