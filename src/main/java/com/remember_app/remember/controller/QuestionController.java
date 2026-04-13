package com.remember_app.remember.controller;

import com.remember_app.remember.common.Result;
import com.remember_app.remember.common.SecurityUtils;
import com.remember_app.remember.entity.Question;
import com.remember_app.remember.exception.QuestionException;
import com.remember_app.remember.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * 新增题目
     */
    @PostMapping
    public Result save(@RequestBody Question question) {
        try {
            questionService.save(question);
        }catch (QuestionException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 更新题目
     */
    @PutMapping
    public Result update(@RequestBody Question question) {
        try {
            Question existing = questionService.getById(question.getQuestionId());
            Integer currentUserId = securityUtils.getCurrentUserId();
            if (existing == null) {
                return Result.error("题目不存在");
            }

            if (!existing.getUserId().equals(currentUserId)) {
                return Result.error("无权操作此题目");
            }

            questionService.updateById(question);
        }catch (QuestionException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 查询指定 id 的题目
     */
    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id) {
        try {
            Question question = questionService.getById(id);
            return Result.success(question);
        }catch (QuestionException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有题目
     */
    @GetMapping
    public Result list() {
        try {
            List<Question> list = questionService.list();
            return Result.success(list);
        }catch (QuestionException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除题目数据
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        try {
            Question question = questionService.getById(id);
            Integer currentUserId = securityUtils.getCurrentUserId();
            if (question == null) {
                return Result.error("题目不存在");
            }
            if (!question.getUserId().equals(currentUserId)) {
                return Result.error("无权操作此题目");
            }
            questionService.removeById(id);
        }catch (QuestionException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
