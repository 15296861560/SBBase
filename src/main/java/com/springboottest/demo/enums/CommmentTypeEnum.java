package com.springboottest.demo.enums;

public enum  CommmentTypeEnum {

    QUESTION(1),
    COMMENT(2);

    public static boolean isExist(Integer type) {
        for (CommmentTypeEnum commmentTypeEnum:CommmentTypeEnum.values()){
            if (commmentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    private Integer type;

    CommmentTypeEnum(Integer type) {
        this.type = type;
    }
}
