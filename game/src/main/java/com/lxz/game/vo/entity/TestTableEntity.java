package com.lxz.game.vo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName TestTable
 * @Description TODO
 * @Author 86184
 * @Date 2024/10/1 16:13
 */
@Data
@TableName("test_table")
public class TestTableEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.UPDATE)
    private Timestamp updateTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
