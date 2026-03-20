package com.remember_app.remember.controller;

import com.remember_app.remember.common.Result;
import com.remember_app.remember.entity.Question;
import com.remember_app.remember.exception.QuestionException;
import com.remember_app.remember.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;

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
            questionService.removeById(id);
        }catch (QuestionException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
