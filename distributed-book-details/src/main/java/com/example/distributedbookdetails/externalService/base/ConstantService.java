package com.example.distributedbookdetails.externalService.base;


import com.example.distributedcommon.custom.SysConst;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/9 11:40
 * description:
 */
public interface ConstantService {

    /**
     * 未删除
     */
    Integer UN_DELETED = SysConst.DeleteState.UN_DELETED.getCode();

    /**
     * 删除
     */
    Integer DELETED = SysConst.DeleteState.DELETE.getCode();

    /**
     * 隐藏
     */
    Integer HIDE = SysConst.ShowState.HIDE.getCode();

    /**
     * 显示
     */
    Integer DISPLAY = SysConst.ShowState.DISPLAY.getCode();

    /**
     * 置顶
     */
    Integer TOP = SysConst.TopState.TOP.getCode();

    /**
     * 不置顶
     */
    Integer UN_TOP = SysConst.TopState.UN_TOP.getCode();

    /**
     * 关
     */
    Integer OFF = SysConst.EnabledState.OFF.getCode();

    /**
     * 开
     */
    Integer ON = SysConst.EnabledState.ON.getCode();

    /**
     * 正序
     */
    String ASC = SysConst.SortOrder.ASC.getCode();
    /**
     * 倒叙
     */
    String DESC = SysConst.SortOrder.DESC.getCode();

    /**
     * 系统
     */
    Integer SYSTEM = SysConst.InfoClass.SYSTEM.getCode();
    /**
     * 自定义
     */
    Integer CUSTOM = SysConst.InfoClass.CUSTOM.getCode();

    /**
     * 居中
     */
    String CENTER = SysConst.Position.CENTER.getType();
    /**
     * 居右
     */
    String RIGHT = SysConst.Position.RIGHT.getType();

    String SORT_NAME_CREATED_TIME = SysConst.SORT_NAME_CREATED_TIME;
    String SORT_NAME_ORDER_BY = SysConst.SORT_NAME_ORDER_BY;

}
