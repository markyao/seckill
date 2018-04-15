package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledMapperTest {

    @Resource
    private SuccessKilledMapper mapper;

    @Test
    public void insertSuccessKilled() {
        long seckillId = 1000L;
        String userPhone = "13857165548";
        int insertCount = mapper.insertSuccessKilled(seckillId, userPhone);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {

        long seckillId = 1000L;
        String userPhone = "13857165548";

        SuccessKilled successKilled = mapper.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}