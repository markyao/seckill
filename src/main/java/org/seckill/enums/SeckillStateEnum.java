package org.seckill.enums;

/**
 * 使用枚举表示常量数据字段
 */
public enum SeckillStateEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    RPEAT_KILL(-1, "重复秒杀"),
    CLOSE_KILL(-2, "秒杀结束"),
    INNER_ERROR(-3, "内部错误");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum stateOf(int index) {
        for (SeckillStateEnum seckillStateEnum : values()) {
            if (seckillStateEnum.getState() == index) {
                return seckillStateEnum;
            }
        }
        return null;
    }
}
