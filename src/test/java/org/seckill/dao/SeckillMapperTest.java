package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合，为junit启动时加载SpringIOC容器
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillMapperTest {

    //注入mapper实现的依赖
    @Resource
    private SeckillMapper mapper;

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = mapper.reduceNumber(1000L, killTime);
        System.out.println("updateCount = " + updateCount);
    }

    @Test
    public void queryById() {
        Seckill seckill = mapper.queryById(1000L);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = mapper.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }
}