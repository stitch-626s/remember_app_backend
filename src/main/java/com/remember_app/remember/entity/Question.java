package com.remember_app.remember.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@TableName("`question_table`")
public class Question {
    /**
     * 题目ID 主键
     */
    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    /**
     * 题库ID 外键
     */
    @TableField("qb_id")
    private Integer qbId;

    /**
     * 用户ID 外键
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 题目
     */
    private String questionName;

    /**
     * 题目答案
     */
    private String questionAnswer;

    /**
     * 题目创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime questionCreatedAt;

    /**
     * 题目修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime questionUpdatedAt;

}
