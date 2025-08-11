package com.lxz.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxz.game.vo.entity.TestTableEntity;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.ResultHandler;

/**
 * @ClassName TestTableDao
 * @Description TODO
 * @Author 86184
 * @Date 2024/10/1 16:26
 */
public interface TestTableDao extends BaseMapper<TestTableEntity> {

    @Select(value = "select * from test_table")
    @Options(fetchSize = 10)
    @ResultType(TestTableEntity.class)
    void executeForAll(ResultHandler<TestTableEntity> resultHandler);
}
