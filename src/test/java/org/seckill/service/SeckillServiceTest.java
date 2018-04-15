package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml",
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillLIst() {
        List<Seckill> list = seckillService.getSeckillLIst();
        logger.info("list={}", list);
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("Seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() {
        long seckillId = 1000L;
        String userPhone = "13857165548";
        String md5 = "47fd023b089f29307c21291482aad856";
        SeckillExecution execution = null;
        try {
            execution = seckillService.executeSeckill(seckillId, userPhone, md5);
        } catch (SeckillException e) {
            logger.error(e.getMessage());
        }
        logger.info("result={}", execution);
    }

    @Test
    public void seckill() {
        long seckillId = 1000L;
        String userPhone = "13857165548";
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            try {
                SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, exposer.getMd5());
                logger.info("result={}", execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            } catch (SeckillException e) {
                logger.error(e.getMessage());
            }

        } else {
            logger.warn("exposer={}", exposer);
        }

    }
}