package com.remember_app.remember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@TableName("question_bank_table")
public class QuestionBank {
    /**
     * 题库ID 主键
     */
    @TableId(value = "qb_id", type = IdType.AUTO)
    private Integer qbId;

    /**
     * 用户ID 外键
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 题库名称
     */
    private String qbName;

    /**
     * 题库描述
     */
    private String qbDescription;

    /**
     * 题库中的题目数量
     */
    private Integer questionNumber;

    /**
     * 题库创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime qbCreatedAt;

    /**
     * 题库修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime qbUpdatedAt;

}
