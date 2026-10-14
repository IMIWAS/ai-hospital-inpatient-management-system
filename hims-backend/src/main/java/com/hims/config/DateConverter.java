package com.hims.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局日期转换器
 *
 * 解决前端 el-date-picker 清空后回传空字符串 "" 导致 Date 字段绑定失败的问题。
 * 兼容多种日期格式；空串 / 空白串统一转为 null。
 */
@Component
public class DateConverter implements Converter<String, Date> {

    private static final String[] PATTERNS = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm",
            "yyyy/MM/dd"
    };

    @Override
    public Date convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        source = source.trim();
        for (String pattern : PATTERNS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setLenient(false);
                return sdf.parse(source);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException("无法解析的日期格式: " + source);
    }
}
