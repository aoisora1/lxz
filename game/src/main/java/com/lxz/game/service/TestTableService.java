package com.lxz.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxz.common.exception.BusinessException;
import com.lxz.game.dao.TestTableDao;
import com.lxz.game.vo.entity.TestTableEntity;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestTableService
 * @Description TestTableService
 * @Author 86184
 * @Date 2024/10/1 16:28
 */
@Service
public class TestTableService extends ServiceImpl<TestTableDao, TestTableEntity> {

    public TestTableEntity queryById(int id) {
        return lambdaQuery()
                .eq(TestTableEntity::getId, id)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("1001", id));
    }

    // 测试流式查询用
    public void printAllName() {
        getBaseMapper().executeForAll(new ResultHandler<TestTableEntity>() {
            @Override
            public void handleResult(ResultContext<? extends TestTableEntity> resultContext) {
                TestTableEntity resultObject = resultContext.getResultObject();
                System.out.println(resultObject.getName());
            }
        });
    }
}
