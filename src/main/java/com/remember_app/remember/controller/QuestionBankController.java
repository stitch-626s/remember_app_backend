package com.remember_app.remember.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.remember_app.remember.common.Result;
import com.remember_app.remember.entity.Question;
import com.remember_app.remember.entity.QuestionBank;
import com.remember_app.remember.exception.QuestionBankException;
import com.remember_app.remember.service.QuestionBankService;
import com.remember_app.remember.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {
    @Resource
    private QuestionBankService questionBankService;
    @Resource
    private QuestionService questionService;

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
            QuestionBank bank = questionBankService.getById(id);
            if (bank != null) {
                bank.setQuestions(questionService.getQuestionsByQbId(id));
            }
            return Result.success(bank);
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
            List<QuestionBank> bankList = questionBankService.list();
            for (QuestionBank bank : bankList) {
                List<Question> questions = questionService.getQuestionsByQbId(bank.getQbId());
                bank.setQuestions(questions);
            }
            return Result.success(bankList);
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
