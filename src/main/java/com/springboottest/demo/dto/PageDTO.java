package com.springboottest.demo.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO>questionDTOS;
    private boolean showPre;//上一页按钮
    private boolean showNext;//下一页按钮
    private boolean showFirst;//返回第一页按钮
    private boolean showEnd;//返回最后一页按钮
    private Integer currentPage;//当前页
    private List<Integer>pages=new ArrayList<>();//显示的可直达页面
//    private Integer page;//页面数
    private Integer totalPage;//页面总数


    public void setPageDTO(Integer totalCount, Integer page, Integer size) {
        currentPage=page;//更新当前页
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        pages.add(page);//先加入当前页
        for (int i = 1; i <=3 ; i++) {//可尝试直接排序
            if(page-i>0)//检查是否大于起始页
                pages.add(0,page-i);
            if(page+i<=totalPage)//检查是否超出总页数
                pages.add(page+i);
        }

        //是否展示上一页按钮(是否位于第一页）
        if(page==1){
            showPre=false;//上一页按钮不显示
        }else {
            showPre=true;
        }
        //是否展示下一页按钮（是否位于最后一页）
        if(page==totalPage){
            showNext=false;//下一页按钮不显示
        }else {
            showNext=true;
        }
        //是否展示第一页(是否包含第一页）
        if (pages.contains(1)){
            showFirst=false;//返回第一页按钮不显示
        }else {
            showFirst=true;
        }
        //是否展示最后一页(是否包含最后一页）
        if (pages.contains(totalPage)){
            showEnd=false;//返回最后一页按钮不显示
        }else {
            showEnd=true;
        }

    }
}
