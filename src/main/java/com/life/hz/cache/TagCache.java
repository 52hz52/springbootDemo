package com.life.hz.cache;

import com.life.hz.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {

    public static List<TagDTO> get() {

        List<TagDTO> tagDTOs = new ArrayList<>();

        TagDTO language = new TagDTO();
        language.setCategoryTag("开发语言");
        language.setUrl("language");
        language.setStringListTag(Arrays.asList(
                "java", "c", "php", "perl", "python", "javascript", "c#",
                "ruby", "go", "lua", "node.js", "erlang", "scala", "bash","typescript", "flutter"
        ));

        TagDTO qianDaunKaiF = new TagDTO();
        qianDaunKaiF.setCategoryTag("前端开发");
        qianDaunKaiF.setUrl("qianDaunKaiF");
        qianDaunKaiF.setStringListTag(Arrays.asList(
                "html","htm5","css","javascript","jquery","json ajax","正则表达式","bootstrap"
        ));

        TagDTO dataBase = new TagDTO();
        dataBase.setCategoryTag("数据库和缓存");
        dataBase.setUrl("dataBase");
        dataBase.setStringListTag(Arrays.asList(
                "mysql","redis","mongodb","sql","oracle","nosql" ,"memcached", "sqlserver","postgresql","sqlite"
        ));

        TagDTO devTool = new TagDTO();
        devTool.setUrl("devTool");
        devTool.setCategoryTag("开发工具");
        devTool.setStringListTag(Arrays.asList(
                "git","gith","visu","vim","subl","xcod","inte","ecli","mave",
                "ide","svn","visu","atom","emac","text","hg"
        ));

        tagDTOs.add(language);
        tagDTOs.add(qianDaunKaiF);
        tagDTOs.add(dataBase);
        tagDTOs.add(devTool);


        return tagDTOs;

    }


    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getStringListTag().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }


}
