package com.springboottest.demo.cache;

import com.springboottest.demo.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO>get(){//(可改成数据库实现)
        ArrayList<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO program=new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","java","node","python","c++"));
        tagDTOs.add(program);
        TagDTO framework=new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","mybatis","Hibernate"));
        tagDTOs.add(framework);


        return tagDTOs;
    }

    public static String filterValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        //flatMap把二维数组循环出来
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;

    }

}
