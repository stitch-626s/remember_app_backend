package com.remember_app.remember.controller;

import com.remember_app.remember.common.Result;
import com.remember_app.remember.entity.QuestionBank;
import com.remember_app.remember.exception.QuestionBankException;
import com.remember_app.remember.service.QuestionBankService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {
    @Resource
    private QuestionBankService questionBankService;

    /**
     * 新增题库
     */
    @PostMapping
    public Result save(@RequestBody QuestionBank questionBank) {
        try {
            questionBankService.save(questionBank);
        }catch (QuestionBankException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 更新题库
     */
    @PutMapping
    public Result update(@RequestBody QuestionBank questionBank) {
        try {
            questionBankService.updateById(questionBank);
        }catch (QuestionBankException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 查询指定 id 的题库
     */
    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id) {
        try {
            QuestionBank question = questionBankService.getById(id);
            return Result.success(question);
        }catch (QuestionBankException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有题库
     */
    @GetMapping
    public Result list() {
        try {
            List<QuestionBank> list = questionBankService.list();
            return Result.success(list);
        }catch (QuestionBankException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除题库数据
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            questionBankService.removeById(id);
        }catch (QuestionBankException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
