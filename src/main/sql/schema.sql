-- 数据库脚本初始化

-- 创建数据库

CREATE DATABASE seckill;

-- 使用数据库
use seckill;

drop table seckill;

set @@session.explicit_defaults_for_timestamp=on;
-- 创建秒杀库存表
create table seckill (
  `seckill_id`  bigint       not null AUTO_INCREMENT comment '商品库存id',
  `name`        varchar(120) not null comment '商品名称',
  `number`      int          not null comment '库存数量',
  `start_time`  timestamp    not null comment '秒杀开启时间',
  `end_time`    timestamp    not null comment '秒杀结束时间',
  `create_time` timestamp    not null default current_timestamp comment '创建时间',
  primary key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)

)ENGINE = InnoDB auto_increment = 1000 default charset = utf8 comment ='秒杀库存表';

-- 初始化数据

insert INTO seckill (name, number, start_time, end_time)
VALUES
  ('1000元秒杀iPhone6', 100, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('500元秒杀iPad2', 200, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('300元秒杀小米4', 300, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('200元秒杀红米note', 400, '2015-11-01 00:00:00', '2015-11-02 00:00:00');

-- 秒杀成功明细
-- 用户登陆认证相关的信息

create table success_killed (
  `seckill_id`  bigint      not null
  comment '商品id',
  `user_phone`  varchar(11) not null
  comment '用户手机号',
  `state`       tinyint     not null default -1
  comment '状态标示： -1：无效 0：成功 1：已付款',
  `create_time` timestamp   not null default current_timestamp
  comment '创建时间',
  primary key (seckill_id, user_phone), /* 联合主键 */
  key idx_creat_time(create_time)

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  comment '秒杀成功明细表'


