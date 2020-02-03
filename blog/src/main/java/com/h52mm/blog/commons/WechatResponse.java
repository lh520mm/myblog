package com.h52mm.blog.commons;

/**
 * @author 刘豪
 * @date 2018/10/30
 * 接口统一返回实体类
 */
public class WechatResponse {
    /**
     * 响应状态,布尔值, false为失败,true为成功,只准出现这两个值,不是字符串
     */
    private boolean success = true;
    /**
     * 错误码,由接口自己定义, success 为false时生效
     */
    private String errorCode = "";
    /**
     * 可为空,由接口设置
     */
    private String message = "";
    /**
     * 响应结果数据,对象/array类型,对应后端的 Bean/List
     */
    private Object data = null;
    /**
     * 总数据条数,数字,如果涉及到分页以及多条数据
     */
    private Integer totalCount = 0;
    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;

    public static WechatResponse newInstance() {
        return new WechatResponse();
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置为成功状态
     */
    public WechatResponse setAsSuccess() {
        this.success = true;
        return this;
    }

    public static WechatResponse createSuccess() {
        WechatResponse data = new WechatResponse();
        data.success = true;
        return data;
    }

    public static WechatResponse createFailture() {
        WechatResponse data = new WechatResponse();
        data.success = false;
        return data;
    }

    public void setAsFailure() {
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public WechatResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public WechatResponse checkSuccess(boolean success, String codeMessageName) {
        this.success = success;
        if (!success) {
            CodeMessage codeMessage = CodeMessage.valueOf(codeMessageName);
            this.errorCode = codeMessage.getCode();
            this.message = codeMessage.getMsg();
        }
        return this;
    }

    public WechatResponse setAsFailure(String codeMessageName) {
        return this.checkSuccess(false, codeMessageName);
    }

    public WechatResponse checkSuccess(boolean success, String errorName, String successName) {
        this.success = success;
        if (success) {
            CodeMessage codeMessage = CodeMessage.valueOf(successName);
            this.errorCode = codeMessage.getCode();
            this.message = codeMessage.getMsg();
        } else {
            CodeMessage codeMessage = CodeMessage.valueOf(errorName);
            this.errorCode = codeMessage.getCode();
            this.message = codeMessage.getMsg();
        }
        return this;
    }

}
