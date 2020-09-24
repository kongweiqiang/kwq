package com.kwq.root.elasticsearch.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/11
 * @DESC :
 */
public class DateTimeUtil {

    public LocalDateTime parse(String dateStr,DateTimeFormatter dateTimeFormatter ,ZoneOffset zoneId){
        if(StringUtils.isEmpty(dateStr)){
            return LocalDateTime.now();
        }
        zoneId = null == zoneId ? ZoneOffset.UTC : zoneId;
        dateTimeFormatter = null == dateTimeFormatter ? DateTimeFormatter.BASIC_ISO_DATE : dateTimeFormatter;
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dateTimeFormatter);
        return LocalDateTime.ofInstant(localDateTime.toInstant(zoneId),zoneId);
    }

    public LocalDateTime parse(String dateStr){
        return parse(dateStr,null,null);
    }

}
