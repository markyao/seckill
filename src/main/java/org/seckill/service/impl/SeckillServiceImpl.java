package org.seckill.service.impl;

import org.seckill.dao.SeckillMapper;
import org.seckill.dao.SuccessKilledMapper;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {


    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    private static final String salt = "x@324feawWwefweo~";


    @Override
    public List<Seckill> getSeckillLIst() {
        return seckillMapper.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = getById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        //系统当前日期时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, startTime.getTime(), endTime.getTime());
        }

        //转化特定字符串过程，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * MD5加密
     *
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Override
    @Transactional
    /**
     * 使用注解控制事物方法的优点
     * 1：开发团队达成一致约定，明确标注事物方法的编程风格
     * 2：保证事物方法的执行时间尽可能短，不要穿插其他的网络操作，RPC/HTTP请求/REDIS或者剥离到事物方法外部
     * 3：不是所有的方法都需要事物，如只有一条修改操作，只读操作不需要事物控制
     */
    public SeckillExecution executeSeckill(long seckillId, String userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀地址被修改");
        }
        //执行秒杀逻辑： 减库存 + 记录购买行为
        Date nowTime = new Date();

        try {
            //减库存
            int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                throw new SeckillCloseException("秒杀已结束");
            }
            //记录购买行为
            int insertCount = successKilledMapper.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatKillException("重复秒杀");
            }
            //秒杀成功
            SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("秒杀异常" + e.getMessage());
        }

    }
}
