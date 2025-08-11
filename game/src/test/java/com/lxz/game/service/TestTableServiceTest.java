package com.lxz.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxz.common.IntegrationTest;
import com.lxz.game.vo.entity.TestTableEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@IntegrationTest
public class TestTableServiceTest {
    @Autowired
    private TestTableService testTableService;

    @Test
    public void test() {
        TestTableEntity testTableEntity = testTableService.getBaseMapper().selectById(1);
        System.out.println(testTableEntity);

        if (testTableEntity == null) {
            testTableEntity = new TestTableEntity();
            testTableEntity.setName("xx");
        }
        testTableService.getBaseMapper().insert(testTableEntity);
        List<TestTableEntity> testTableEntities = testTableService.getBaseMapper().selectList(new QueryWrapper<TestTableEntity>());
        testTableEntity = testTableService.getBaseMapper().selectById(testTableEntity.getId());
        System.out.println(testTableEntity);
    }
}
