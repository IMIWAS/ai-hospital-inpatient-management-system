package com.hims.utils;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 统一响应数据封装
 *   格式：{ code: 200, msg: "success", data: ... }
 *   分页数据放 data 中：data = { list: [...], total: 100, pageNum: 1, pageSize: 10 }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> {

    private Integer code;
    private String msg;
    private T data;

    private final static Integer SUCCESS_CODE = 200;
    private final static Integer ERROR_CODE = 201;
    private final static String SUCCESS_MSG = "success";
    private final static String ERROR_MSG = "error";

    public ResultVo<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static <T> ResultVo<T> success(String msg) {
        return new ResultVo<>(SUCCESS_CODE, msg, null);
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> ResultVo<T> success(String msg, T data) {
        return new ResultVo<>(SUCCESS_CODE, msg, data);
    }

    public static <T> ResultVo<T> error() {
        return new ResultVo<>(ERROR_CODE, ERROR_MSG, null);
    }

    public static <T> ResultVo<T> error(String msg) {
        return new ResultVo<>(ERROR_CODE, msg, null);
    }

    public static <T> ResultVo<T> error(Integer code, String msg) {
        return new ResultVo<>(code, msg, null);
    }

    /**
     * 自定义状态码（用于业务异常）
     */
    public static <T> ResultVo<T> of(Integer code, String msg, T data) {
        return new ResultVo<>(code, msg, data);
    }

    /**
     * 分页数据封装：将 PageInfo 转成 { list, total, pageNum, pageSize }
     */
    public static <T> ResultVo<PageResult<T>> page(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        PageResult<T> pageResult = new PageResult<>(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getPages()
        );
        return new ResultVo<>(SUCCESS_CODE, SUCCESS_MSG, pageResult);
    }

    /**
     * 分页结果内部类（data 中的分页结构）
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageResult<T> {
        private List<T> list;
        private Long total;
        private Integer pageNum;
        private Integer pageSize;
        private Integer pages;
    }
}
