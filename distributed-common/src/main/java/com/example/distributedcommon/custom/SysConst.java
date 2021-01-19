package com.example.distributedcommon.custom;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/9 10:18
 * description: 系统常量
 */
public class SysConst {

    public static final int DEFAULT_BATCH_SIZE = 200;
    public static final int WARNING_PEAK_VALUE = 100;

    public static final long ES_CACHE_TIMEOUT = 5L;
    public static final TimeUnit ES_CACHE_UNIT = TimeUnit.MINUTES;

    public static final int DEFAULT_SPECIAL_COUNT = 5;
    public static final int DEFAULT_EVENT_COUNT = 1;
    public static final String DEFAULT_PASSWORD = "123456";
    public static final String DEFAULT_SORT_NAME = "publishTime";
    public static final String SORT_NAME_CREATED_TIME = "createdTime";
    public static final String SORT_NAME_WARNING_TIME = "warningTime";
    public static final String SORT_NAME_ORDER_BY = "orderBy";
    public static final String SOURCE_ALL = "all";
    public static final String SOURCE_OTHER = "other";
    public static final List<String> SOURCE_ALL_LIST = Collections.singletonList(SOURCE_ALL);
    public static final String UTF_8 = "UTF-8";

    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    ///////////////////////////////////////////////////////////////////////////
    // 通用常量 -- 多实体类通用
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    @AllArgsConstructor
    public enum DeviceInfo {
        MOBILE("mobile", "手机"),
        WEB("web", "网页"),
        WE_CHAT("weChat", "微信");

        private final String type;
        private final String name;
    }

    /**
     * 排序
     */
    @Getter
    @AllArgsConstructor
    public enum SortOrder {

        ASC("asc"),
        DESC("desc");

        private final String code;
    }

    public static Optional<SortOrder> getSortOrderByType(String code) {
        return Arrays.stream(SortOrder.values())
                .filter(replyType -> StringUtils.equals(code, replyType.getCode()))
                .findFirst();
    }


    /**
     * 时间跨度
     */
    @Getter
    @AllArgsConstructor
    public enum TimeSpan {
        HOUR("hour", "小时"),
        DAY("day", "天");

        private final String type;
        private final String name;

    }


    public static Optional<TimeSpan> getTimeSpanByType(String type) {
        return Arrays.stream(TimeSpan.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 时间类型
     */
    @Getter
    @AllArgsConstructor
    public enum TimeType {
        CUSTOM_TIME("define", "自定义时间"),
        ALL("all", "全部"),
        TODAY("today", "今天"),
        YESTERDAY("yesterday", "昨天"),
        WEEK("week", "近7天"),
        MONTH("month", "近30天");

        private final String type;
        private final String name;

    }


    public static Optional<TimeType> getTimeTypeByType(String type) {
        return Arrays.stream(TimeType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 删除状态
     */
    @Getter
    @AllArgsConstructor
    public enum DeleteState {

        UN_DELETED(0, "未删除"),
        DELETE(1, "删除");

        private final Integer code;
        private final String name;
    }

    /**
     * 显示状态
     */
    @Getter
    @AllArgsConstructor
    public enum ShowState {

        HIDE(0, "hide", "隐藏"),
        DISPLAY(1, "display", "显示");

        private final Integer code;
        private final String type;
        private final String name;
    }

    public static Optional<ShowState> getShowStateByCode(Integer code) {
        return Arrays.stream(ShowState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 置顶状态
     */
    @Getter
    @AllArgsConstructor
    public enum TopState {

        UN_TOP(0, "不置顶"),
        TOP(1, "置顶");

        private final Integer code;
        private final String name;

    }


    public static Optional<TopState> getTopStateByCode(Integer code) {
        return Arrays.stream(TopState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }


    /**
     * 开启状态
     */
    @Getter
    @AllArgsConstructor
    public enum EnabledState {

        OFF(0, "停用"),
        ON(1, "开启");

        private final Integer code;
        private final String name;

    }

    public static Optional<EnabledState> getEnabledStateByCode(Integer code) {
        return Arrays.stream(EnabledState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getEnabledStateNameByCode(Integer code) {
        return getEnabledStateByCode(code).orElse(EnabledState.ON).getName();
    }


    /**
     * 阅读状态
     */
    @Getter
    @AllArgsConstructor
    public enum ReadState {

        UNREAD(0, "未读"),
        READ(1, "已读"),
        ALL(2, "全部");

        private final Integer code;
        private final String name;
    }

    public static Optional<ReadState> getReadStateByCode(Integer code) {
        return Arrays.stream(ReadState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 发送状态
     */
    @Getter
    @AllArgsConstructor
    public enum SendState {

        UNSENT(0, "未发送"),
        SENT(1, "已发送");

        private final Integer code;
        private final String name;

    }

    /**
     * 发送状态
     */
    @Getter
    @AllArgsConstructor
    public enum FromType {

        FILTER_BOX("filterBox", "收藏"),
        FAVORITES("favorites", "收藏"),
        PANORAMA("panorama", "全景"),
        SPECIAL("special", "专题"),
        EVENT("event", "事件"),
        WARNING("warning", "预警"),
        DATA_CENTER("dataCenter", "数据中心"),
        ABROAD("abroad", "境外(新闻)(媒体)"),
        SHORT_VIDEO("shortVideo", "短视频"),
        MULTILINGUAL("multilingual", "多语言"),
        INTENSIVE_MONITOR("intensiveMonitor", "重点监测"),
        THEME("theme", "主题"),
        PORTRAIT_ANALYSIS("portraitAnalysis", "人物画像"),
        INSTANT_MESSAGING("instantMessaging", "即时聊天");

        private final String type;
        private final String name;
    }

    public static Optional<FromType> getFromTypeByType(String type) {
        return Arrays.stream(FromType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 验证码类型
     */
    @Getter
    @AllArgsConstructor
    public enum VerificationCodeType {

        REG("reg", "账户注册"),
        LOGIN("login", "账户登陆"),
        FORGET("forget", "密码重置"),
        BIND("bind", "账户绑定"),
        UNBIND("unbind", "账户解除绑定"),
        UNKNOWN("unknown", "未知");

        private final String type;
        private final String name;
    }

    public static Optional<VerificationCodeType> getVerificationCodeTypeByType(String type) {
        return Arrays.stream(VerificationCodeType.values())
                .filter(replyType -> Objects.equal(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 文件扩展名
     */
    @Getter
    @AllArgsConstructor
    public enum ExtensionName {
        WORD2003_EDITION("doc", "word2003"),
        WORD2007_EDITION("docx", "word2007"),
        EXCEL2003_EDITION("xls", "excel2003"),
        EXCEL2007_EDITION("xlsx", "excel2007"),
        PDF_EDITION("pdf", "pdf");

        private final String type;
        private final String name;
    }

    public static Optional<ExtensionName> getExtensionNameByType(String type) {
        return Arrays.stream(ExtensionName.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 移动类型
     */
    @Getter
    @AllArgsConstructor
    public enum MoveType {
        UP("up", "上移"),
        DOWN("down", "下移");

        private final String type;
        private final String name;
    }

    public static Optional<MoveType> getMoveTypeByType(String type) {
        return Arrays.stream(MoveType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 时间单位
     */
    @Getter
    @AllArgsConstructor
    public enum TimeUnits {
        MINUTES("minutes", TimeUnit.MINUTES),
        HOURS("hours", TimeUnit.HOURS),
        DAYS("days", TimeUnit.DAYS);

        private final String type;
        private final TimeUnit timeUnit;
    }

    public static Optional<TimeUnits> getTimeUnitsByType(String type) {
        return Arrays.stream(TimeUnits.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static TimeUnit getTimeUnitByType(String type) {
        return getTimeUnitsByType(type).map(TimeUnits::getTimeUnit).orElse(TimeUnit.DAYS);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 特别常量 -- 单实体类通用
    ///////////////////////////////////////////////////////////////////////////
    @Getter
    @AllArgsConstructor
    public enum AppSystemType {
        ANDROID("android"),
        IOS("ios");

        private final String type;
    }


    /**
     * 登录类型
     */
    @Getter
    @AllArgsConstructor
    public enum LoginType {

        AJAX("ajax", "ajax登陆"),
        TOKEN("token", "token登陆"),
        ZM_TOKEN("zmToken", "token登陆"),
        PHONE("phone", "手机号登陆");

        private final String type;
        private final String name;

    }

    /**
     * 账户状态
     */
    @Getter
    @AllArgsConstructor
    public enum AccountState {

        FROZEN(0, "冻结"),
        FREE(1, "免费账户"),
        PROBATIONER(2, "试用账户"),
        CEREMONIAL(3, "正式账户");

        private final Integer code;
        private final String name;

    }

    public static Optional<AccountState> getAccountStateByCode(Integer code) {
        return Arrays.stream(AccountState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 账户等级
     */
    @Getter
    @AllArgsConstructor
    public enum AccountLevel {

        FIRST_LEVEL(1, "一级账号"),
        SECOND_LEVEL(2, "二级账号"),
        THIRD_LEVEL(3, "三级账号");

        private final Integer code;
        private final String name;

    }

    public static Optional<AccountLevel> getAccountLevelByCode(Integer code) {
        return Arrays.stream(AccountLevel.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 账户等级
     */
    @Getter
    @AllArgsConstructor
    public enum AccountType {

        ADMIN("admin", "管理员用户"),
        ORDINARY("ordinary", "普通用户");

        private final String type;
        private final String name;
    }

    /**
     * 到期状态
     */
    @Getter
    @AllArgsConstructor
    public enum ExpireState {

        UNEXPIRED(0, "未到期"),
        EXPIRE(1, "到期");

        private final Integer code;
        private final String name;

    }

    public static Optional<ExpireState> getExpireStateByCode(Integer code) {
        return Arrays.stream(ExpireState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 性别
     */
    @Getter
    @AllArgsConstructor
    public enum Sex {

        WOMEN(0, "女"),
        MEN(1, "男"),
        UNKNOWN(99, "未知");

        private final Integer code;
        private final String name;

    }

    public static Optional<Sex> getSexByCode(Integer code) {
        return Arrays.stream(Sex.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 过滤类型
     */
    @Getter
    @AllArgsConstructor
    public enum FilterType {

        KEYWORD(0, "keyword", "关键词"),
        SOURCE(1, "source", "来源"),
        AUTHOR(2, "author", "作者"),
        ID(3, "id", "Id过滤");

        private final Integer code;
        private final String type;
        private final String name;
    }

    public static Optional<FilterType> getFilterTypeByCode(Integer code) {
        return Arrays.stream(FilterType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getFilterTypeTypeByCode(Integer code) {
        return getFilterTypeByCode(code).map(FilterType::getType).orElse(FilterType.KEYWORD.getType());
    }

    public enum OptionTarget {
        MEDIA_ACTIVITY_LEADERS_TREND,
        PUBLIC_OPINION_LEADERS_TREND
    }

    /**
     * app首页颜色类型
     */
    @Getter
    @AllArgsConstructor
    public enum AppIndexColorType {

        WANING_TAG_COLOR(99, FromType.WARNING.type),
        EVENT_TAG_COLOR(0, FromType.EVENT.type),
        SPECIAL_TAG_COLOR_ONE(1, FromType.SPECIAL.type),
        SPECIAL_TAG_COLOR_TWO(2, FromType.SPECIAL.type),
        SPECIAL_TAG_COLOR_THREE(3, FromType.SPECIAL.type),
        SPECIAL_TAG_COLOR_FOUR(4, FromType.SPECIAL.type);

        private final Integer colorCode;
        private final String tagType;
    }


    /**
     * 面板类型
     */
    @Getter
    @AllArgsConstructor
    public enum PanelType {

        STANDARD("standard", "标准面板"),
        ANALYSIS("analysis", "分析面板");

        private final String type;
        private final String name;
    }

    public static Optional<PanelType> getPanelTypeByType(String type) {
        return Arrays.stream(PanelType.values())
                .filter(replyType -> type.equals(replyType.getType()))
                .findFirst();
    }


    /**
     * 面板类型
     */
    @Getter
    @AllArgsConstructor
    public enum SpecialType {

        ORDINARY(0, "普通专题"),
        ESPECIALLY(1, "特别专题");

        private final Integer code;
        private final String name;
    }

    /**
     * 面板类型
     */
    @Getter
    @AllArgsConstructor
    public enum SupportSourceSpecial {

        DEFAULT_DATA(0, "默认数据"),
        CUSTOM_DATE(1, "自定义数据");

        private final Integer code;
        private final String name;
    }

    /**
     * 过滤类型
     */
    @Getter
    @AllArgsConstructor
    public enum MessageType {

        NOTICE(1, "公告通知"),
        SYSTEM_UPDATE(2, "系统更新");
        private final Integer code;
        private final String name;
    }

    public static Optional<MessageType> getMessageTypeByCode(Integer code) {
        return Arrays.stream(MessageType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 位置
     */
    @Getter
    @AllArgsConstructor
    public enum Position {

        LEFT("left", "居左"),
        CENTER("center", "居中"),
        RIGHT("right", "居右");

        private final String type;
        private final String name;
    }

    public static Optional<Position> getPositionByType(String type) {
        return Arrays.stream(Position.values())
                .filter(replyType -> type.equals(replyType.getType()))
                .findFirst();
    }

    /**
     * 预警类型
     */
    @Getter
    @AllArgsConstructor
    public enum WarningType {

        MACHINE(0, "机器预警"),
        ARTIFICIAL(1, "手动预警");

        private final Integer code;
        private final String name;

    }

    public static Optional<WarningType> getWarningTypeByCode(Integer code) {
        return Arrays.stream(WarningType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 预警等级
     */
    @Getter
    @AllArgsConstructor
    public enum WarningLevel {

        RED("red", "红色预警"),
        ORANGE("orange", "橙色预警"),
        YELLOW("yellow", "黄色预警");

        private final String type;
        private final String name;

    }

    public static Optional<WarningLevel> getWarningLevelByType(String type) {
        return Arrays.stream(WarningLevel.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getWarningLevelNameByType(String type) {
        return getWarningLevelByType(type).orElse(WarningLevel.RED).getName();
    }

    /**
     * 预警等级
     */
    @Getter
    @AllArgsConstructor
    public enum WarningRuleState {

        NORMAL(0, "正常"),
        ABNORMAL(1, "异常");

        private final Integer code;
        private final String name;
    }

    public static Optional<WarningRuleState> getWarningRuleStateByCode(Integer code) {
        return Arrays.stream(WarningRuleState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 预警等级
     */
    @Getter
    @AllArgsConstructor
    public enum WarningNoticeSearchArea {

        PEOPLE("people", "人"),
        EMAIL("email", "邮箱"),
        PHONE("phone", "手机号");

        private final String type;
        private final String name;
    }

    public static Optional<WarningNoticeSearchArea> getWarningNoticeSearchAreaByType(String type) {
        return Arrays.stream(WarningNoticeSearchArea.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getWarningNoticeSearchAreaNameByType(String type) {
        return getWarningNoticeSearchAreaByType(type).orElse(WarningNoticeSearchArea.PEOPLE).getName();
    }

    public static List<String> getWarningNoticeSearchAreaNameByType(List<String> type) {
        return type.stream().map(SysConst::getWarningNoticeSearchAreaNameByType).collect(Collectors.toList());
    }

    /**
     * 预警规则class
     */
    @Getter
    @AllArgsConstructor
    public enum WarningRuleClass {

        //        USER_ID("userId", "用户id"),
        RULE_TITLE("ruleTitle", "规则名称"),
        ENABLED_STATE("enabledState", "预警状态"),
        //        WARNING_TYPE("warningType", "预警类型"),
        WARNING_LEVEL("warningLevel", "预警等级"),
        EMOTION("emotion", "情感特征"),
        //        REGION("region", "区域"),
        CARRIER("carrier", "信息载体"),
        RELATED_WORDS("relatedWords", "相关词"),
        THESAURUS_THEME_IDS("thesaurusThemeIds", "预警词-主题"),
        WARNING_WORDS("warningWords", "预警词-自定义"),
        EXCLUSION_WORDS("exclusionWords", "排除词"),
        SEARCH_AREA("searchArea", "出现位置"),
        WARNING_NOTICE_IDS("warningNoticeIds", "预警接收人"),
        WARNING_NOTICE_TYPE("warningNoticeType", "预警方式"),
        START_WARNING_TIME("startWarningTime", "预警开始时间"),
        END_WARNING_TIME("endWarningTime", "预警结束时间");

        private final String attrName;
        private final String attrDesc;
    }

    public static Optional<WarningRuleClass> getWarningRuleClassByAttrName(String attrName) {
        return Arrays.stream(WarningRuleClass.values())
                .filter(replyType -> StringUtils.equals(attrName, replyType.getAttrName()))
                .findFirst();
    }

    public static String getWarningRuleAttrDescByAttrName(String attrName) {
        return getWarningRuleClassByAttrName(attrName).orElse(WarningRuleClass.RULE_TITLE).getAttrDesc();
    }

    /**
     * 预警规则class
     */
    @Getter
    @AllArgsConstructor
    public enum OperationType {

        CREATE("create", "新增"),
        UPDATE("update", "修改"),
        DELETE("delete", "删除"),
        SAVE("save", "保存");

        private final String type;
        private final String name;
    }


    /**
     * 文章类型
     */
    @Getter
    @AllArgsConstructor
    public enum ArticleType {

        VIDEO(0, "video", "视频"),
        COMMENT(1, "comment", "评论"),
        INSTANT_MESSAGING(2, "instantMessaging", "即时通讯"),
        TRADITION(99, "tradition", "传统文章");

        private final Integer code;
        private final String type;
        private final String name;
    }

    public static Optional<ArticleType> getArticleTypeByCode(Integer code) {
        return Arrays.stream(ArticleType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static Optional<ArticleType> getArticleTypeByType(String type) {
        return Arrays.stream(ArticleType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getArticleTypeTypeByCode(Integer code) {
        return SysConst.getArticleTypeByCode(code).orElse(ArticleType.TRADITION).getType();
    }

    public static Integer getArticleTypeCodeByType(String type) {
        return SysConst.getArticleTypeByType(type).orElse(ArticleType.TRADITION).getCode();
    }

    private static List<ArticleType> getArticleTypeCodeVideoAll() {
        return Arrays.stream(ArticleType.values()).filter(s -> !Objects.equal(ArticleType.TRADITION.type, s.getType())).collect(Collectors.toList());
    }

    public static List<Integer> getArticleTypeCodeVideoAllType() {
        return SysConst.getArticleTypeCodeVideoAll().stream().map(ArticleType::getCode).collect(Collectors.toList());
    }

    public static List<String> getArticleTypeTypeVideoAllType() {
        return SysConst.getArticleTypeCodeVideoAll().stream().map(ArticleType::getType).collect(Collectors.toList());
    }

    public static List<Integer> getArticleTypeCodeByType(List<String> type) {
        return type.parallelStream().map(SysConst::getArticleTypeCodeByType).collect(Collectors.toList());
    }

    /**
     * 情感范围
     */
    @Getter
    @AllArgsConstructor
    public enum Emotion {

        POSITIVE("positive", "正面"),
        NEGATIVE("negative", "负面"),
        NEUTRAL("neutral", "中性"),
        IRRELEVANT("irrelevant", "无关");

        private final String type;
        private final String name;

    }

    public static Optional<Emotion> getEmotionByType(String type) {
        return Arrays.stream(Emotion.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static List<Emotion> getEmotionAll() {
        return Arrays.asList(Emotion.values());
    }

    public static List<Emotion> getEmotionParamsAll() {
        return Arrays.stream(Emotion.values()).filter(s -> !Objects.equal(Emotion.IRRELEVANT.type, s.getType())).collect(Collectors.toList());
    }

    public static List<String> getEmotionTypeParamsAll() {
        return SysConst.getEmotionParamsAll().stream().map(Emotion::getType).collect(Collectors.toList());
    }

    private static String getEmotionNameByType(String type) {
        return SysConst.getEmotionByType(type).orElse(Emotion.IRRELEVANT).getName();
    }

    public static List<String> getEmotionNameByType(List<String> type) {
        return type.parallelStream().map(SysConst::getEmotionNameByType).collect(Collectors.toList());
    }


    /**
     * 载体范围
     */
    @Getter
    @AllArgsConstructor
    public enum Carrier {

        CARRIER_NEWS("news", 2001, "新闻", "境外新闻"),
        CARRIER_WE_CHAT("weChat", 2005, "微信"),
        CARRIER_BLOG("blog", 2002, "博客", "境外博客"),
        CARRIER_MICRO_BLOG("microBlog", 2004, "微博"),
        CARRIER_FORUM("forum", 2003, "论坛", "境外论坛"),
        CARRIER_POSTS_BAR("postsBar", 2010, "贴吧"),
        CARRIER_ELECTRONIC_NEWSPAPER("electronicNewspaper", 2007, "电子报"),
        CARRIER_VIDEO("video", 2008, "视频", "境外视频"),
        CARRIER_APP("app", 2009, "APP"),
        CARRIER_INTER_LOCUTION("interLocution", 2011, "问答", "境外问答"),
        CARRIER_COMPREHENSIVE("comprehensive", 2000, "综合"),
        CARRIER_SHORT_VIDEO("shortVideo", 2012, "短视频"),
        CARRIER_OTHER("other", 2999, "其他", "其他"),
        CARRIER_ABROAD_TWITTER("twitter", 3001, "Twitter", "Twitter"),
        CARRIER_ABROAD_FACEBOOK("facebook", 3002, "Facebook", "Facebook");

        private final String type;
        private final Integer code;
        private final String name;
        private final String abroadName;

        Carrier(String type, Integer code, String name) {
            this.type = type;
            this.code = code;
            this.name = name;
            this.abroadName = name;
        }
    }

    public static Optional<Carrier> getCarrierByType(String type) {
        return Arrays.stream(Carrier.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static Optional<Carrier> getCarrierByCode(Integer code) {
        return Arrays.stream(Carrier.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static Integer getCarrierCodeByType(String type) {
        return SysConst.getCarrierByType(type).orElse(Carrier.CARRIER_COMPREHENSIVE).getCode();
    }

    public static String getCarrierTypeByCode(Integer code) {
        return SysConst.getCarrierByCode(code).orElse(Carrier.CARRIER_COMPREHENSIVE).getType();
    }

    public static String getCarrierNameByCode(Integer code) {
        return SysConst.getCarrierByCode(code).orElse(Carrier.CARRIER_COMPREHENSIVE).getName();
    }

    public static String getCarrierNameByType(String type) {
        return SysConst.getCarrierByType(type).orElse(Carrier.CARRIER_COMPREHENSIVE).getName();
    }

    public static List<String> getCarrierNameByType(List<String> type) {
        return type.parallelStream().map(SysConst::getCarrierNameByType).collect(Collectors.toList());
    }

    public static List<Integer> getCarrierCodeByType(List<String> type) {
        return type.parallelStream().map(SysConst::getCarrierCodeByType).collect(Collectors.toList());
    }

    public static List<Carrier> getTraditionCarrierAll() {
        Carrier[] carrierArr = {Carrier.CARRIER_NEWS,
                Carrier.CARRIER_WE_CHAT,
                Carrier.CARRIER_BLOG,
                Carrier.CARRIER_MICRO_BLOG,
                Carrier.CARRIER_FORUM,
                Carrier.CARRIER_POSTS_BAR,
                Carrier.CARRIER_ELECTRONIC_NEWSPAPER,
                Carrier.CARRIER_VIDEO,
                Carrier.CARRIER_APP,
                Carrier.CARRIER_INTER_LOCUTION,
                Carrier.CARRIER_COMPREHENSIVE,
                Carrier.CARRIER_OTHER};
        return Arrays.asList(carrierArr);
    }

    public static List<Integer> getTraditionCarrierCodeAll() {
        return SysConst.getTraditionCarrierAll().stream().map(Carrier::getCode).collect(Collectors.toList());
    }

    public static List<String> getTraditionCarrierTypeAll() {
        return SysConst.getTraditionCarrierAll().stream().map(Carrier::getType).collect(Collectors.toList());
    }

    public static List<String> getTraditionCarrieNameAll() {
        return SysConst.getTraditionCarrierAll().stream().map(Carrier::getName).collect(Collectors.toList());
    }

    public static List<Carrier> getCarrierAllExcludeShortVideo() {
        Carrier[] carrierArr = {
                Carrier.CARRIER_NEWS,
                Carrier.CARRIER_WE_CHAT,
                Carrier.CARRIER_BLOG,
                Carrier.CARRIER_MICRO_BLOG,
                Carrier.CARRIER_FORUM,
                Carrier.CARRIER_POSTS_BAR,
                Carrier.CARRIER_ELECTRONIC_NEWSPAPER,
                Carrier.CARRIER_VIDEO,
                Carrier.CARRIER_APP,
                Carrier.CARRIER_INTER_LOCUTION,
                Carrier.CARRIER_COMPREHENSIVE,
                Carrier.CARRIER_OTHER,
                Carrier.CARRIER_ABROAD_TWITTER,
                Carrier.CARRIER_ABROAD_FACEBOOK,
        };
        return Arrays.asList(carrierArr);
    }

    public static List<Carrier> getInnerCarrierAll() {
        Carrier[] carrierArr = {Carrier.CARRIER_NEWS,
                Carrier.CARRIER_WE_CHAT,
                Carrier.CARRIER_BLOG,
                Carrier.CARRIER_MICRO_BLOG,
                Carrier.CARRIER_FORUM,
                Carrier.CARRIER_POSTS_BAR,
                Carrier.CARRIER_ELECTRONIC_NEWSPAPER,
                Carrier.CARRIER_VIDEO,
                Carrier.CARRIER_APP,
                Carrier.CARRIER_INTER_LOCUTION,
                Carrier.CARRIER_COMPREHENSIVE,
                Carrier.CARRIER_SHORT_VIDEO,
                Carrier.CARRIER_OTHER};
        return Arrays.asList(carrierArr);
    }

    public static List<Carrier> getAbroadCarrierAll() {
        Carrier[] carrierArr = {Carrier.CARRIER_NEWS,
                Carrier.CARRIER_VIDEO,
                Carrier.CARRIER_BLOG,
                Carrier.CARRIER_FORUM,
                Carrier.CARRIER_ABROAD_TWITTER,
                Carrier.CARRIER_ABROAD_FACEBOOK,
                Carrier.CARRIER_INTER_LOCUTION,
                Carrier.CARRIER_OTHER};
        return Arrays.asList(carrierArr);
    }

    public static List<Carrier> getCarrierByRegion(String region) {
        List<Carrier> carriers = Lists.newArrayList();
        if (Objects.equal(region, SysConst.SOURCE_ALL)) {
            carriers = Arrays.asList(Carrier.values());
        } else if (Objects.equal(region, Region.REGION_INNER.getType())) {
            carriers = SysConst.getInnerCarrierAll();
        } else if (Objects.equal(region, Region.REGION_OUTSIDE.getType())) {
            carriers = SysConst.getAbroadCarrierAll();
        }
        return carriers;
    }

    public static List<Carrier> getCarrierByRegionExcludeShortVideo(String region) {
        List<Carrier> carriers = Lists.newArrayList();
        if (Objects.equal(region, SysConst.SOURCE_ALL)) {
            carriers = getCarrierAllExcludeShortVideo();
        } else if (Objects.equal(region, Region.REGION_INNER.getType())) {
            carriers = SysConst.getTraditionCarrierAll();
        } else if (Objects.equal(region, Region.REGION_OUTSIDE.getType())) {
            carriers = SysConst.getAbroadCarrierAll();
        }
        return carriers;
    }

    /**
     * 载体范围
     */
    @Getter
    @AllArgsConstructor
    public enum VideoCarrier {

        CARRIER_KUAISHOU("kuaishou", "快手app", "快手"),
        CARRIER_DOUYIN("douyin", "抖音app", "抖音"),
        CARRIER_LISHIPIN("lishipin", "梨视频", "梨视频"),
        CARRIER_XIGUA("xigua", "西瓜", "西瓜"),
        CARRIER_MIAOPAI("miaopai", "秒拍app", "秒拍"),
        CARRIER_XIAOYING("xiaoying", "小影app", "小影"),
        CARRIER_MEIPAI("meipai", "美拍", "美拍"),
        CARRIER_HUOSHAN("huoshan", "火山app", "火山");

        private final String type;
        private final String esName;
        private final String name;

    }

    public static Optional<VideoCarrier> getVideoCarrierByType(String type) {
        return Arrays.stream(VideoCarrier.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static Optional<VideoCarrier> getVideoCarrierByEsName(String esName) {
        return Arrays.stream(VideoCarrier.values())
                .filter(replyType -> StringUtils.equals(esName, replyType.getEsName()))
                .findFirst();
    }

    public static List<String> getVideoCarrierTypeAll() {
        return Arrays.stream(VideoCarrier.values()).map(VideoCarrier::getType).collect(Collectors.toList());
    }

    public static List<String> getVideoCarrierEsNameAll() {
        return Arrays.stream(VideoCarrier.values()).map(VideoCarrier::getEsName).collect(Collectors.toList());
    }

    public static String getVideoCarrierEsNameByType(String type) {
        return SysConst.getVideoCarrierByType(type).orElse(VideoCarrier.CARRIER_DOUYIN).getEsName();
    }

    public static String getVideoCarrierTypeByEsName(String esName) {
        return SysConst.getVideoCarrierByEsName(esName).orElse(VideoCarrier.CARRIER_DOUYIN).getType();
    }

    public static List<String> getVideoCarrierEsNameByType(List<String> type) {
        return type.parallelStream().map(SysConst::getVideoCarrierEsNameByType).collect(Collectors.toList());
    }

    /**
     * 搜索范围
     */
    @Getter
    @AllArgsConstructor
    public enum SearchArea {

        ALL("all", "全部"),
        AUTHOR("author", "作者"),
        TITLE("title", "标题"),
        INCIDENT_SUBJECT("incidentSubject", "主题"),
        SITE_NAME("siteName", "站点名称"),
        CHANNEL("channel", "频道"),
        ADDRESS("address", "地址"),
        CONTENT("content", "内容");

        private final String type;
        private final String name;
    }

    public static Optional<SearchArea> getSearchAreaByType(String type) {
        return Arrays.stream(SearchArea.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getSearchAreaNameByType(String type) {
        return getSearchAreaByType(type).orElse(SearchArea.ALL).getName();
    }

    /**
     * 搜索范围
     */
    @Getter
    @AllArgsConstructor
    public enum SearchMode {

        ACCURATE("accurate", "精确"),
        VAGUE("vague", "模糊");

        private final String type;
        private final String name;
    }

    public static Optional<SearchMode> getSearchModeByType(String type) {
        return Arrays.stream(SearchMode.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }


    /**
     * 搜索范围
     */
    @Getter
    @AllArgsConstructor
    public enum Development {

        INFO_DIVIDE_AUTHOR("infoDivideAuthor", "人均发文数"),
        AUTHOR("author", "作者量"),
        INFO("info", "舆情数");

        private final String type;
        private final String name;
    }

    public static Optional<Development> getDevelopmentByType(String type) {
        return Arrays.stream(Development.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 搜索范围
     */
    @Getter
    @AllArgsConstructor
    public enum SensitiveType {

        NEGATIVE("negative", "负面"),
        WARNING("warning", "预警");

        private final String type;
        private final String name;
    }

    public static Optional<SensitiveType> getSensitiveTypeByType(String type) {
        return Arrays.stream(SensitiveType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 搜索范围
     */
    @Getter
    @AllArgsConstructor
    public enum UserSearchArea {

        ACCOUNT("account", "账户"),
        COMPANY("company", "公司"),
        ACCOUNT_DESC("accountDesc", "公司");

        private final String type;
        private final String name;
    }

    public static Optional<UserSearchArea> getUserSearchAreaByType(String type) {
        return Arrays.stream(UserSearchArea.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 境内外类型
     */

    @Getter
    @AllArgsConstructor
    public enum Region {

        ALL(SOURCE_ALL, 0, "全部"),
        REGION_INNER("inner", 1, "境内"),
        REGION_OUTSIDE("outside", 2, "境外");

        private final String type;
        private final Integer code;
        private final String name;

    }

    public static Optional<Region> getRegionByType(String type) {
        return Arrays.stream(Region.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    /**
     * 语言类型
     */
    @Getter
    @AllArgsConstructor
    public enum Multilingual {

        CHINESE("chinese", "中文"),
        MONGOLIAN("mongolian", "蒙文"),
        VIVIEN("vivien", "维文"),
        TIBETAN("tibetan", "藏文");

        private final String type;
        private final String name;
    }

    public static Optional<Multilingual> getMultilingualByType(String type) {
        return Arrays.stream(Multilingual.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getMultilingualTypeNameByType(String type) {
        return getMultilingualByType(type).map(Multilingual::getName).orElse(Multilingual.CHINESE.getName());
    }

    /**
     * 采集类型
     */
    @Getter
    @AllArgsConstructor
    public enum CollectionType {

        REGION_INNER(0, "regionInner", "境内"),
        REGION_OUTSIDE(1, "regionOutside", "境外"),
        SHORT_VIDEO(2, "shortVideo", "短视频"),
        ONLY_EVENT_WORD(3, "onlyEventWord", "短视频");

        private final Integer code;
        private final String type;
        private final String name;

    }

    /**
     * 通知类型
     */
    @Getter
    @AllArgsConstructor
    public enum NoticeType {

        DIALOG("dialog", "弹框"),
        EMAIL("email", "邮件"),
        PHONE("phone", "短信"),
        APP("app", "APP"),
        WE_CHAT("weChat", "微信");

        private final String type;
        private final String name;

    }

    public static Optional<NoticeType> getNoticeTypeByType(String type) {
        return Arrays.stream(NoticeType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getNoticeTypeNameByType(String type) {
        return getNoticeTypeByType(type).orElse(NoticeType.PHONE).getName();
    }

    public static String getNoticeTypeNameByTypes(String type) {
        return Optional.ofNullable(type).map(t ->
                Arrays.stream(t.split(","))
                        .map(str -> {
                            Optional<NoticeType> noticeTypeOptional = SysConst.getNoticeTypeByType(str);
                            return noticeTypeOptional.map(NoticeType::getName).orElse("");
                        }).collect(Collectors.joining(",")))
                .orElse("");
    }

    /**
     * 报告类型
     */
    @Getter
    @AllArgsConstructor
    public enum BriefingType {
        DAY("day", "日报"),
        WEEK("week", "周报"),
        MONTH("month", "月报");

        private final String type;
        private final String name;
    }

    public static Optional<BriefingType> getBriefingTypeByType(String type) {
        return Arrays.stream(BriefingType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static String getBriefingNameTypeByType(String type) {
        return getBriefingTypeByType(type).orElse(BriefingType.DAY).getName();
    }

    /**
     * 报告模板类型
     */
    @Getter
    @AllArgsConstructor
    public enum TemplateType {
        CURRENCY_EDITION(1, "通用版"),
        MEDIA_EDITION(2, "媒体版");

        private final Integer code;
        private final String name;
    }

    public static Optional<TemplateType> getTemplateTypeByCode(Integer code) {
        return Arrays.stream(TemplateType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 模板编号
     */
    public static class TemplateNumber {

        @Getter
        @AllArgsConstructor
        public enum CurrencyEdition {

            TYPE_0(0, "预警舆情"),
            TYPE_1(1, "疑似敏感舆情"),
            TYPE_2(2, "重点舆情"),
            TYPE_3(3, "舆情载体趋势图"),
            TYPE_4(4, "情感分时段分布"),
            TYPE_5(5, "载体情感分布统计"),
            TYPE_6(6, "媒体活跃排行榜"),
            TYPE_7(7, "大众意见领袖榜"),
            TYPE_8(8, "地域参与热力图");
            private final Integer code;
            private final String name;
        }

        public static Optional<CurrencyEdition> getCurrencyTemplateNumberByCode(Integer code) {
            return Arrays.stream(CurrencyEdition.values())
                    .filter(replyType -> Objects.equal(code, replyType.getCode()))
                    .findFirst();
        }

        public static String getCurrencyTemplateNumberNameByCode(Integer code) {
            return getCurrencyTemplateNumberByCode(code).orElse(CurrencyEdition.TYPE_0).getName();
        }

        @Getter
        @AllArgsConstructor
        public enum MediaEdition {
            TYPE_0(0, "新闻舆情", Collections.singletonList(Carrier.CARRIER_NEWS)),
            TYPE_1(1, "两微舆情", Arrays.asList(Carrier.CARRIER_MICRO_BLOG, Carrier.CARRIER_WE_CHAT)),
            TYPE_2(2, "论坛舆情", Collections.singletonList(Carrier.CARRIER_FORUM)),
            TYPE_3(3, "贴吧舆情", Collections.singletonList(Carrier.CARRIER_POSTS_BAR)),
            TYPE_4(4, "纸媒舆情", Collections.singletonList(Carrier.CARRIER_ELECTRONIC_NEWSPAPER)),
            TYPE_5(5, "其他舆情", Arrays.asList(Carrier.CARRIER_APP, Carrier.CARRIER_INTER_LOCUTION,
                    Carrier.CARRIER_BLOG, Carrier.CARRIER_COMPREHENSIVE, Carrier.CARRIER_VIDEO, Carrier.CARRIER_SHORT_VIDEO)),
            TYPE_6(6, "外媒舆情", Collections.EMPTY_LIST);

            private final Integer code;
            private final String name;
            private final List<Carrier> carrierList;
        }

        public static Optional<MediaEdition> getMediaTemplateNumberByCode(Integer code) {
            return Arrays.stream(MediaEdition.values())
                    .filter(replyType -> Objects.equal(code, replyType.getCode()))
                    .findFirst();
        }

        public static String getMediaTemplateNumberNameByCode(Integer code) {
            return getMediaTemplateNumberByCode(code).orElse(MediaEdition.TYPE_0).getName();
        }

    }

    public static Optional<TemplateNumber.MediaEdition> getTemplateMediaEditionByCode(Integer code) {
        return Arrays.stream(TemplateNumber.MediaEdition.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static Optional<TemplateNumber.MediaEdition> getTemplateMediaEditionByCarrier(Carrier carrier) {
        return Arrays.stream(TemplateNumber.MediaEdition.values())
                .filter(replyType -> replyType.carrierList.contains(carrier))
                .findFirst();
    }

    /**
     * 报告生成方式 是否默认 或者自定义
     */
    @Getter
    @AllArgsConstructor
    public enum BriefingCustom {

        DEFAULT(0, "默认"),
        CUSTOM(1, "自定义");

        private final Integer code;
        private final String name;

    }

    /**
     * 报告生成方式 是否默认 或者自定义
     */
    @Getter
    @AllArgsConstructor
    public enum FolderType {

        DAILY(1, "日报"),
        WEEKLY(2, "周报"),
        MONTHLY(3, "月报"),
        QUARTERLY(4, "季报"),
        HALF_YEARLY(5, "半年报"),
        YEARLY(6, "年报"),
        SPECIAL(7, "专报"),
        OTHER(8, "其他");

        private final Integer code;
        private final String name;

    }

    public static Optional<FolderType> getFolderTypeByCode(Integer code) {
        return Arrays.stream(FolderType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }


    /**
     * 应用中心的所有app
     */
    @Getter
    @AllArgsConstructor
    public enum MenuApp {
        APP_CLOUD_SEARCH_INDEX(7L, "cloudSearchIndex", "云搜索"),
        APP_SITE(8L, "site", "网站巡查"),
        APP_PUBLIC_SENTIMENT_CHART(9L, "publicSentimentChart", "智能图表"),
        APP_BIG_SCREEN(10L, "bigScreen", "舆情大屏"),
        APP_ABROAD_NEWS(11L, "abroadNews", "境外新闻媒体监测"),
        APP_LANGUAGE(12L, "multilingual", "多语言监测"),
        APP_ABROAD_WEBSITE(13L, "abroadWebsite", "境外网址导航"),
        APP_COMMENT_ANALYSIS(14L, "commentAnalysis", "评论分析"),
        APP_APP(17L, "app", "舆情管家APP"),
        APP_SITE_CHANNEL(19L, "siteChannel", "网站频道巡查"),
        APP_ABROAD_SOCIALIZATION(20L, "abroadSocialization", "境外社交媒体监测"),
        APP_NAVY_SYSTEM(21L, "navySystem", "网络评论管理"),
        APP_SHORT_VIDEO(22L, "shortVideo", "短视频"),
        APP_OPINION_OA(24L, "opinionOA", "舆情OA"),
        APP_INCIDENT(25L, "incident", "舆情智库"),
        APP_TOPICAL(26L, "topical", "舆情风向标"),
        APP_PORTRAIT_ANALYSIS(27L, "portraitAnalysis", "人物画像"),
        APP_INSTANT_MESSAGING(27L, "instantMessaging", "群组监测"),
        APP_MICRO_BLOG_ANALYSIS(28L, "microBlogAnalysis", "微博分析");

        private final Long code;
        private final String identify;
        private final String name;

    }

    public static Optional<MenuApp> getMenuAppByCode(Long code) {
        return Arrays.stream(MenuApp.values())
                .filter(menuApp -> Objects.equal(code, menuApp.code))
                .findFirst();
    }

    /**
     * 信息的等级
     */
    @Getter
    @AllArgsConstructor
    public enum InfoClass {

        SYSTEM(0, "系统"),
        CUSTOM(1, "自定义");

        private final Integer code;
        private final String name;

    }

    public static Optional<InfoClass> getInfoClassByCode(Integer code) {
        return Arrays.stream(InfoClass.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * app状态
     */
    @Getter
    @AllArgsConstructor
    public enum MenuState {

        MENU_STATE_0(0, "可使用"),
        MENU_STATE_1(1, "敬请期待"),
        MENU_STATE_3(3, "用户已订阅"),
        MENU_STATE_4(4, "用户未订阅");

        private final Integer code;
        private final String name;

    }

    public static Optional<MenuState> getMenuStateByType(Integer code) {
        return Arrays.stream(MenuState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getMenuStateNameByType(Integer code) {
        return getMenuStateByType(code).orElse(MenuState.MENU_STATE_1).getName();
    }


    /**
     * app状态
     */
    @Getter
    @AllArgsConstructor
    public enum MenuType {

        SYSTEM_TYPE(0, "systemType", "系统"),
        MONITOR_TYPE(1, "monitorType", "舆情监测"),
        ANALYSIS_TYPE(2, "analysisType", "舆情分析"),
        WORK_TYPE(3, "workType", "办公辅助"),
        OTHER_TYPE(4, "otherType", "舆情周边"),
        COOPERATION_TYPE(5, "cooperationType", "协同处置");

        private final Integer code;
        private final String type;
        private final String name;

    }


    public static Optional<MenuType> getMenuTypeByType(Integer code) {
        return Arrays.stream(MenuType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getMenuTypeTypeByType(Integer code) {
        return getMenuTypeByType(code).orElse(MenuType.SYSTEM_TYPE).getType();
    }

    /**
     * app状态
     */
    @Getter
    @AllArgsConstructor
    public enum PermissionType {

        DISPLAY("display", "显示权限"),
        OPERATION("operation", "操作权限");

        private final String type;
        private final String name;

    }

    /**
     * 事件监测状态
     */
    @Getter
    @AllArgsConstructor
    public enum EventState {

        NOT_YET_BEGUN(0, "未开始"),
        MONITORING(1, "监测中"),
        HAS_ENDED(2, "已结束");

        private final Integer code;
        private final String name;
    }

    public static Optional<EventState> getEventStateByCode(Integer code) {
        return Arrays.stream(EventState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getEventStateNameByCode(Integer code) {
        return getEventStateByCode(code).map(EventState::getName).orElse(StringUtils.EMPTY);
    }

    /**
     * 站点信息
     *
     * @param websiteUrl websiteUrl
     * @return Optional
     */
    public static Optional<WebsiteInfoOften> getWebsiteInfo(String websiteUrl) {
        final String sina = "sina.com";
        final String qq = "qq.com";
        final String w163 = "163.com";
        final String sohu = "sohu.com";
        String webName;
        if (websiteUrl.contains(sina)) {
            webName = "新浪网";
        } else if (websiteUrl.contains(qq)) {
            webName = "腾讯网";
        } else if (websiteUrl.contains(w163)) {
            webName = "网易";
        } else if (websiteUrl.contains(sohu)) {
            webName = "搜狐网";
        } else {
            webName = "默认";
        }
        return getWebsiteInfoOftenBySource(webName);
    }

    /**
     * 站点信息
     */
    @Getter
    @AllArgsConstructor
    public enum WebsiteInfoOften {

        DEFAULT("默认", "", ""),
        WEBSITE_10("新浪网", "http://news.sina.com.cn/", "review-pic1_03.png"),
        WEBSITE_20("腾讯网", "http://news.qq.com/", "review-pic2_03.png"),
        WEBSITE_30("网易", "http://news.163.com/", "review-pic3_03.png"),
        WEBSITE_40("搜狐网", "http://news.sohu.com/", "review-pic4_03.png");

        private final String websiteSource;
        private final String websiteUrl;
        private final String websiteIcon;

    }

    private static Optional<WebsiteInfoOften> getWebsiteInfoOftenBySource(String websiteSource) {
        return Arrays.stream(WebsiteInfoOften.values())
                .filter(websiteInfo -> StringUtils.equals(websiteSource, websiteInfo.getWebsiteSource()))
                .findFirst();
    }

    /**
     * 分析状态
     */
    @Getter
    @AllArgsConstructor
    public enum TargetType {

        PORTRAIT_ANALYSIS(1, "画像分析"),
        MICRO_BLOG_ANALYSIS(2, "微博分析");

        private final Integer code;
        private final String name;
    }

    /**
     * 分析状态
     */
    @Getter
    @AllArgsConstructor
    public enum AnalysisState {

        SUBMISSION_SUCCESS(0, "提交成功"),
        ANALYSIS_ING(1, "分析中"),
        ANALYSIS_SUCCESS(2, "分析完成"),
        ANALYSIS_FAILURE(3, "分析失败");

        private final Integer code;
        private final String name;
    }

    public static Optional<AnalysisState> getAnalysisStateBySource(Integer code) {
        return Arrays.stream(AnalysisState.values())
                .filter(analysisState -> Objects.equal(code, analysisState.getCode()))
                .findFirst();
    }

    /**
     * 分析状态
     */
    @Getter
    @AllArgsConstructor
    public enum AnalysisDescribe {

        Describe_1("分析超时"),
        Describe_2("分析失败");

        private final String name;
    }


    /**
     * 词性类型
     */
    @Getter
    @AllArgsConstructor
    public enum WordType {

        WORD(0, "单词"),
        COMPOUND_WORD(1, "组合词");

        private final Integer code;
        private final String name;

    }

    /**
     * 词来源
     */
    @Getter
    @AllArgsConstructor
    public enum WordSource {

        SYSTEM(0, "system", "系统"),
        USER(1, "user", "用户"),
        RELATED_WORD(11, "relatedWord", "相关词"),
        WARNING_WORD(12, "warningWord", "预警词"),
        ALL(99, "all", "都有");

        private final Integer code;
        private final String type;
        private final String name;
    }


    /**
     * 监测媒体类型
     */
    @Getter
    @AllArgsConstructor
    public enum SocialType {

        TWITTER("twitter", "推特", Arrays.asList("www.twitter.com", "twitter.com")),
        FACEBOOK("facebook", "facebook", Arrays.asList("www.facebook.com", "facebook.com"));

        private final String type;
        private final String name;
        private final List<String> url;

    }

    public static Optional<SocialType> getSocialTypeByType(String type) {
        return Arrays.stream(SocialType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }


    /**
     * 重点监测项类型
     */
    @Getter
    @AllArgsConstructor
    public enum IntensiveMonitorItemType {

        ITEM_TYPE_WE_CHAT("weChat", "微信", Collections.singletonList(Carrier.CARRIER_WE_CHAT)),
        ITEM_TYPE_MICRO_BLOG("microBlog", "微博", Collections.singletonList(Carrier.CARRIER_MICRO_BLOG)),
        ITEM_TYPE_WEBSITE("website", "网站", SysConst.getTraditionCarrierAll()),
        ITEM_TYPE_POST_BAR("postBar", "贴吧", Collections.singletonList(Carrier.CARRIER_POSTS_BAR));

        private final String type;
        private final String name;
        private final List<Carrier> carrierList;

    }

    public static Optional<IntensiveMonitorItemType> getIntensiveMonitorItemTypeByType(String type) {
        return Arrays.stream(IntensiveMonitorItemType.values())
                .filter(replyType -> StringUtils.equals(type, replyType.getType()))
                .findFirst();
    }

    public static List<Carrier> getIntensiveMonitorItemTypeCarrierListByType(String type) {
        return getIntensiveMonitorItemTypeByType(type).orElse(IntensiveMonitorItemType.ITEM_TYPE_WEBSITE).getCarrierList();
    }


    @Getter
    @AllArgsConstructor
    public enum BigScreenSet {

        BS_SPECIAL("专题监测版大屏", "bsSpecial", "", "1", "1", 1),
        BS_EVENT("突发舆情事件指挥中心", "bsEvent", "", "1", "1", 2),
        BS_DATA("数据中心采集实时大屏", "bsData", "", "1", "1", 3),
        BS_HOTSPOT_RANKING("热点榜单大屏", "bsHotspotRanking", "", "1", "1", 4),
        BS_HOTSPOT_DISTRIBUTION("全球地域热点监控", "bsHotspotDistribution", "", "1", "1", 5);

        private final String bigScreenName;

        private final String bigScreenIdentify;

        private final String fontColor;

        private final String backgroundImg;

        private final String border;

        private final Integer orderBy;

    }

    public static Optional<BigScreenSet> getBigScreenSetByBigScreenIdentify(String bigScreenIdentify) {
        return Arrays.stream(BigScreenSet.values())
                .filter(replyType -> Objects.equal(replyType.getBigScreenIdentify(), bigScreenIdentify))
                .findFirst();
    }

    /**
     * 重点监测项类型
     */
    @Getter
    @AllArgsConstructor
    public enum IntensiveMonitorLevelType {

        LEVEL_A(1, "A"),
        LEVEL_B(2, "B"),
        LEVEL_C(3, "C");

        private final Integer code;
        private final String name;

    }

    public static Optional<IntensiveMonitorLevelType> getIntensiveMonitorLevelTypeByType(Integer code) {
        return Arrays.stream(IntensiveMonitorLevelType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getIntensiveMonitorLevelTypeNameByType(Integer code) {
        return getIntensiveMonitorLevelTypeByType(code).orElse(IntensiveMonitorLevelType.LEVEL_A).getName();
    }


    /**
     * 监测媒体类型
     */
    @Getter
    @AllArgsConstructor
    public enum DoubleSpeed {

        DOUBLE_SPEED_1(1, "一倍"),
        DOUBLE_SPEED_2(2, "二倍");

        private final Integer code;
        private final String name;

    }

    public static Optional<DoubleSpeed> getDoubleSpeedByCode(Integer code) {
        return Arrays.stream(DoubleSpeed.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    /**
     * 事件报告的处置建议的事件属性(紧急事件,轻微事件,中度事件)
     */
    @Getter
    @AllArgsConstructor
    public enum EventAttr {

        EVENT_ATTR_URGENT(1, "紧急事件"),
        EVENT_ATTR_MODERATE(2, "中度事件"),
        EVENT_ATTR_SLIGHT(3, "轻微事件");

        private final Integer code;
        private final String name;

    }

    /**
     * 事件类型
     */
    @Getter
    @AllArgsConstructor
    public enum IncidentType {
        INCIDENT_TYPE_1(1, "公共管理"),
        INCIDENT_TYPE_2(2, "社会矛盾"),
        INCIDENT_TYPE_3(3, "公共安全"),
        INCIDENT_TYPE_4(4, "企业管理"),
        INCIDENT_TYPE_5(5, "客户服务"),
        INCIDENT_TYPE_6(6, "食药安全"),
        INCIDENT_TYPE_7(7, "隐私保护"),
        INCIDENT_TYPE_8(8, "市场竞争"),
        INCIDENT_TYPE_9(9, "贪腐违纪"),
        INCIDENT_TYPE_10(10, "产品质量"),
        INCIDENT_TYPE_11(11, "生态保护"),
        INCIDENT_TYPE_12(12, "劳资纠纷"),
        INCIDENT_TYPE_13(13, "欺诈造假"),
        INCIDENT_TYPE_14(14, "公众人物"),
        INCIDENT_TYPE_15(15, "涉外涉军"),
        INCIDENT_TYPE_16(16, "其他");

        private final Integer code;
        private final String name;
    }

    public static Optional<IncidentType> getIncidentTypeByCode(Integer code) {
        return Arrays.stream(IncidentType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getIncidentTypeNameByCode(Integer code) {
        return getIncidentTypeByCode(code).map(IncidentType::getName).orElse(StringUtils.EMPTY);
    }

    /**
     * 事件根源
     */
    @Getter
    @AllArgsConstructor
    public enum IncidentRoots {
        INCIDENT_ROOTS_1(1, "社会道德争议"),
        INCIDENT_ROOTS_2(2, "贫富及城乡差距"),
        INCIDENT_ROOTS_3(3, "官民关系"),
        INCIDENT_ROOTS_4(4, "警民关系"),
        INCIDENT_ROOTS_5(5, "医患关系"),
        INCIDENT_ROOTS_6(6, "权益纠纷"),
        INCIDENT_ROOTS_7(7, "社会暴力"),
        INCIDENT_ROOTS_8(8, "未成年人及弱势群体保护"),
        INCIDENT_ROOTS_9(9, "群体维权"),
        INCIDENT_ROOTS_10(10, "意识形态"),
        INCIDENT_ROOTS_11(11, "国计民生"),
        INCIDENT_ROOTS_12(12, "其他");


        private final Integer code;
        private final String name;
    }

    public static Optional<IncidentRoots> getIncidentRootsByCode(Integer code) {
        return Arrays.stream(IncidentRoots.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getIncidentRootsNameByCode(Integer code) {
        return getIncidentRootsByCode(code).map(IncidentRoots::getName).orElse(StringUtils.EMPTY);
    }

    /**
     * 事件主体
     */
    @Getter
    @AllArgsConstructor
    public enum IncidentSubject {
        INCIDENT_SUBJECT_1(1, "政府或事业单位"),
        INCIDENT_SUBJECT_2(2, "企业或社会组织"),
        INCIDENT_SUBJECT_3(3, "公众人物"),
        INCIDENT_SUBJECT_4(4, "社会大众");

        private final Integer code;
        private final String name;
    }

    public static Optional<IncidentSubject> getIncidentSubjectByCode(Integer code) {
        return Arrays.stream(IncidentSubject.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getIncidentSubjectNameByCode(Integer code) {
        return getIncidentSubjectByCode(code).map(IncidentSubject::getName).orElse(StringUtils.EMPTY);
    }

    /**
     * 搜索范围
     */
    @Getter
    @AllArgsConstructor
    public enum IncidentDetailsType {

        PROCESS(1, "process", "事件经过"),
        HANDLE(2, "handle", "事件处理"),
        INSPIRE(3, "inspire", "事件启发");

        private final Integer code;
        private final String type;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum InstantMessagingType {
        QQ(0, "QQ"),
        WE_CHAT(1, "微信"),
        CURRENCY(99, "通用");

        private final Integer code;
        private final String name;
    }

    public static Optional<InstantMessagingType> getInstantMessagingTypeByCode(Integer code) {
        return Arrays.stream(InstantMessagingType.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }

    public static String getInstantMessagingTypeNameByCode(Integer code) {
        return getInstantMessagingTypeByCode(code)
                .map(InstantMessagingType::getName).orElse(StringUtils.EMPTY);
    }

    @Getter
    @AllArgsConstructor
    public enum InstantMessagingState {
        DEPLOY(1, "部署"),
        GATHER(2, "采集"),
        CLOSE(3, "关闭"),
        INVALID(99, "无效");

        private final Integer code;
        private final String name;
    }

    public static Optional<InstantMessagingState> getInstantMessagingStateByCode(Integer code) {
        return Arrays.stream(InstantMessagingState.values())
                .filter(replyType -> Objects.equal(code, replyType.getCode()))
                .findFirst();
    }


    public static String getInstantMessagingStateNameByCode(Integer code) {
        return getInstantMessagingStateByCode(code)
                .map(InstantMessagingState::getName).orElse(StringUtils.EMPTY);
    }

    @AllArgsConstructor
    @Getter
    public enum ContentType {

        /**
         * 文本
         */
        TEXT("text"),
        /**
         * 图片
         */
        IMAGE("image"),
        /**
         * 音频
         */
        VOICE("voice"),
        /**
         * 视频
         */
        VIDEO("video"),
        /**
         * 媒体
         */
        MEDIA("media"),
        /**
         * 文件
         */
        FILE("file");

        private final String type;
    }
}
