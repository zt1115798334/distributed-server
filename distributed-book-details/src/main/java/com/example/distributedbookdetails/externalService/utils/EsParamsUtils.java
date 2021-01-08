package com.example.distributedbookdetails.externalService.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedcommon.custom.SysConst;
import com.example.distributedcommon.utils.DateUtils;
import com.example.distributedcommon.utils.MStringUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.distributedcommon.custom.SysConst.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/1/26 13:15
 * description:
 */
public class EsParamsUtils {

    private static final String ES_ITEM_ID = "tagId";
    private static final String ES_REPEAT_STATE = "rel"; //排重状态
    private static final String ES_USER_ID = "userId"; //userId
    private static final String ES_ARTICLE_ID = "id"; //文章id
    private static final String ES_ARTICLE_ID_S = "ids"; //文章id
    private static final String ES_ARTICLE_PRIMARIES = "ids";
    private static final String ES_PUBLISH_TIME = "publishTime";
    private static final String ES_ARTICLE_REL_ID = "relId"; //文章id
    private static final String ES_ARTICLE_IDS = "ids"; //文章id集合
    private static final String ES_TITLE = "title"; //标题
    public static final String ES_START_TIME = "start_time"; //开始时间
    public static final String ES_END_TIME = "end_time"; //结束时间
    public static final String ES_TIME_SPAN = "time"; //结束时间
    private static final String ES_SEARCH_AREA = "searchType"; //搜索范围
    private static final String ES_SEARCH_VALUE = "searchValue"; //搜索关键字
    private static final String ES_SEARCH_AREA_Q = "relatedArea"; //关键词出现位置
    private static final String ES_EMOTION = "nature"; //情感
    private static final String ES_CARRIER = "carrie"; //载体
    public static final String ES_VIDEO_CARRIER = "siteName"; //主域名
    private static final String ES_ARTICLE_TYPE = "type"; //文章类型
    private static final String ES_READ_STATE = "isRead"; //阅读状态
    private static final String ES_SORT_NAME = "sortField"; //排序名称
    private static final String ES_SORT_ORDER = "sortType"; //排序类型
    private static final String ES_RELATED_WORDS = "relatedWords"; //相关词
    private static final String ES_OPINION_WORDS = "opinionWords"; //态势词
    public static final String ES_THESAURUS_THEME_IDS = "yuqing"; //主题id
    private static final String ES_EXCLUSION_WORDS = "unrelated"; //排除词
    private static final String ES_MULTILINGUAL = "language"; //语言
    private static final String ES_AUTHOR = "author"; //作者
    public static final String ES_URL_MAIL = "urlMain";
    private static final String ES_URL_ROOT = "urlRoot";
    private static final String ES_SITE_NAME = "siteName";
    private static final String ES_CHANNEL = "channel";
    private static final String ES_REGION = "country"; //境内外
    private static final String ES_HOT_WORD_COUNT = "count";
    private static final String ES_ITEM = "field";
    private static final String ES_ITEM_SIZE = "size";
    private static final String ES_JUDGE_TYPE = "type";
    private static final String ES_JUDGE_AUTO = "judgedAuto";
    private static final String ES_REFRESH_STATE = "refreshState";

    private static final List<String> TRADITION = Collections.singletonList(ArticleType.TRADITION.getType());


    /**
     * 处理统一参数   queryParams 内部使用的参数
     *
     * @param searchArea        搜索位置 关键词的位置
     * @param searchMode        搜索方式
     * @param emotion           情感
     * @param carrier           载体
     * @param relatedWords      相关词
     * @param opinionWords      态势词
     * @param thesaurusThemeIds 主题id
     * @param exclusionWords    排除词
     * @return JSONObject
     */
    public static JSONObject getQueryParams(Long itemId, LocalDateTime startDateTime, LocalDateTime endDateTime, String searchArea, String searchMode, String emotion, String carrier,
                                            String relatedWords, String opinionWords, String thesaurusThemeIds,
                                            String exclusionWords) {
        JSONObject csJo = new JSONObject();
        searchArea = StringUtils.isNotEmpty(searchArea) ? searchArea : SearchArea.ALL.getType();
        csJo.put(ES_SEARCH_AREA_Q, searchArea); //关键词的位置
        csJo.putAll(getQueryItemIdParams(itemId));
        csJo.putAll(getQueryEmotionParams(emotion));//拼接情感参数
        csJo.putAll(getQueryCarrierTypeParams(MStringUtils.stringCommaRtListString(carrier)));//拼接载体参数
        if (startDateTime != null && endDateTime != null) {
            csJo.putAll(getQueryTimeParams(startDateTime, endDateTime));
        }
        //精确查询
        if (StringUtils.equals(searchMode, SearchMode.VAGUE.getType())) {
            csJo.put(ES_RELATED_WORDS, MStringUtils.splitMinGranularityStr(relatedWords));
        } else {
            csJo.put(ES_SEARCH_VALUE, MStringUtils.splitMinGranularityStr(relatedWords));
            csJo.put(ES_SEARCH_AREA, searchArea);
        }

        if (StringUtils.isNotEmpty(opinionWords)) {
            csJo.put(ES_OPINION_WORDS, MStringUtils.splitMinGranularityStr(opinionWords));
        }
        csJo.putAll(EsParamsUtils.getQueryThesaurusThemeParams(thesaurusThemeIds));
        if (StringUtils.isNotEmpty(exclusionWords)) {
            csJo.put(ES_EXCLUSION_WORDS, MStringUtils.splitMinGranularityStr(exclusionWords));
        }
        return csJo;
    }


    /**
     * 处理统一参数   queryParams 内部使用的参数
     *
     * @param searchArea        搜索位置 关键词的位置
     * @param searchMode        搜索方式
     * @param emotion           情感
     * @param carrier           载体
     * @param relatedWords      相关词
     * @param opinionWords      态势词
     * @param thesaurusThemeIds 主题id
     * @param exclusionWords    排除词
     * @return JSONObject
     */
    public static JSONObject getQueryParams(Long itemId, String searchArea, String searchMode, String emotion, String carrier,
                                            String relatedWords, String opinionWords, String thesaurusThemeIds,
                                            String exclusionWords) {

        return getQueryParams(itemId, null, null, searchArea, searchMode, emotion, carrier, relatedWords, opinionWords, thesaurusThemeIds, exclusionWords);
    }

    /**
     * 外部 搜索 条件参数
     *
     * @param emotion   情感
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param readState 阅读状态
     * @return JSONObject
     */
    public static JSONObject getQueryExternalParams(String emotion, List<String> carrier,
                                                    String startTime, String endTime,
                                                    Integer readState, Boolean repeatState) {
        return getQueryExternalParams(emotion, carrier,
                startTime, endTime,
                SearchArea.ALL.getType(), StringUtils.EMPTY,
                TRADITION,
                readState, repeatState);

    }

    /**
     * 外部 搜索 条件参数
     *
     * @param emotion     情感
     * @param carrier     载体
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param articleType 文章类型
     * @param readState   阅读状态
     * @param repeatState 排重状态
     * @return JSONObject
     */
    public static JSONObject getQueryExternalParams(String emotion, List<String> carrier,
                                                    String startTime, String endTime,
                                                    List<String> articleType,
                                                    Integer readState, Boolean repeatState) {
        return getQueryExternalParams(emotion, carrier,
                startTime, endTime,
                SearchArea.ALL.getType(), StringUtils.EMPTY,
                articleType,
                readState, repeatState);

    }

    /**
     * 外部 搜索 条件参数
     *
     * @param emotion     情感
     * @param carrier     载体
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param searchArea  搜索范围
     * @param searchValue 搜索值
     * @param articleType 文章类型
     * @param readState   阅读状态
     * @param repeatState 排重状态
     * @return JSONObject
     */
    public static JSONObject getQueryExternalParams(String emotion, List<String> carrier,
                                                     String startTime, String endTime,
                                                     String searchArea, String searchValue,
                                                     List<String> articleType,
                                                     Integer readState,
                                                     Boolean repeatState) {
        JSONObject csJo = new JSONObject();
        csJo.putAll(getQueryEmotionParams(emotion));
        csJo.putAll(getQueryCarrierTypeParams(carrier));
        csJo.putAll(getQueryTimeParams(startTime, endTime));
        csJo.putAll(getQuerySearchParams(searchArea, searchValue));
        csJo.putAll(getQueryArticleTypeParams(articleType));
        csJo.putAll(getQueryReadStateParams(readState));
        csJo.putAll(getQueryRepeatStateParams(repeatState));
        return csJo;
    }

    /**
     * 文章类型参数
     *
     * @param articleType 文章类型参数
     * @return JSONObject
     */
    public static JSONObject getQueryArticleTypeParams(List<String> articleType) {
        JSONObject csJo = new JSONObject();
        if (articleType != null && articleType.size() != 0
                && !Objects.equal(articleType, SOURCE_ALL_LIST)
                && !Objects.equal(articleType, TRADITION)) {
            csJo.put(ES_ARTICLE_TYPE, SysConst.getArticleTypeCodeByType(articleType));
        } else if (Objects.equal(articleType, TRADITION)) {
//            csJo.put(ES_ARTICLE_TYPE, getArticleTypeCodeVideoAllType());
        }
        return csJo;
    }

    /**
     * 相似文章 搜索 条件参数
     *
     * @param userId       用户id
     * @param articleId    文章id
     * @param articleRelId 相似文章id
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param sortName     排序名称
     * @param sortOrder    排序类型
     * @return JSONObject
     */
    public static JSONObject getQuerySimilarParams(Long userId, String articleId, String articleRelId,
                                                   String startTime, String endTime,
                                                   String sortName, String sortOrder) {
        JSONObject csJo = new JSONObject();
        csJo.putAll(EsParamsUtils.getQueryUserIdParams(userId));
        csJo.putAll(EsParamsUtils.getQueryArticleIdParams(articleId));
        csJo.putAll(EsParamsUtils.getQueryArticleRelIdParams(articleRelId));
        csJo.putAll(EsParamsUtils.getQueryTimeParams(startTime, endTime));
        csJo.putAll(EsParamsUtils.getQuerySortParams(sortName, sortOrder));
        return csJo;
    }

    /**
     * 文章id参数
     *
     * @param articleRelId 相似文章id
     * @return JSONObject
     */
    public static JSONObject getQueryArticleRelIdParams(String articleRelId) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_ARTICLE_REL_ID, articleRelId);
        return csJo;
    }

    /**
     * 类型id
     *
     * @param itemId 类型id
     * @return JSONObject
     */
    public static JSONObject getQueryItemIdParams(Long itemId) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_ITEM_ID, itemId);
        return csJo;
    }


    /**
     * 情感参数
     *
     * @param emotion 情感
     * @return JSONObject
     */
    public static JSONObject getQueryEmotionParams(String emotion) {
        JSONObject csJo = new JSONObject();
        if (!Objects.equal(emotion, SOURCE_ALL)) {
            csJo.put(ES_EMOTION, MStringUtils.stringCommaRtListString(emotion));
        } else {
            csJo.put(ES_EMOTION, getEmotionTypeParamsAll());
        }
        return csJo;
    }


    /**
     * 情感参数
     *
     * @param emotions 情感
     * @return JSONObject
     */
    public static JSONObject getQueryEmotionsParams(String... emotions) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_EMOTION, emotions);
        return csJo;
    }

    /**
     * 载体参数
     *
     * @param carrier 载体
     * @return JSONObject
     */
    public static JSONObject getQueryCarrierTypeParams(List<String> carrier) {
        JSONObject csJo = new JSONObject();
        if (carrier != null && carrier.size() != 0 && !Objects.equal(carrier, SOURCE_ALL_LIST)) {
            csJo.put(ES_CARRIER, getCarrierCodeByType(carrier));
        } else {
            csJo.put(ES_CARRIER, getTraditionCarrierCodeAll());
        }
        return csJo;
    }

    /**
     * 载体参数
     *
     * @param type 载体
     * @return JSONObject
     */
    public static JSONObject getQueryCarrierCodeParams(String type) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_CARRIER, Collections.singletonList(getCarrierCodeByType(type)));
        return csJo;
    }

    /**
     * 主题参数
     *
     * @param thesaurusThemeIds 主题参数集合
     * @return JSONObject
     */
    public static JSONObject getQueryThesaurusThemeParams(String thesaurusThemeIds) {
        return EsParamsUtils.getQueryThesaurusThemeParams(MStringUtils.decorateRecoveryStr(thesaurusThemeIds));
    }

    /**
     * 主题参数
     *
     * @param thesaurusThemeIds 主题参数集合
     * @return JSONObject
     */
    public static JSONObject getQueryThesaurusThemeParams(List<Long> thesaurusThemeIds) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_THESAURUS_THEME_IDS, thesaurusThemeIds);
        return csJo;
    }


    /**
     * userId参数
     *
     * @param userId 用户id
     * @return JSONObject
     */
    public static JSONObject getQueryUserIdParams(Long userId) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_USER_ID, userId);
        return csJo;
    }

    /**
     * 文章id参数
     *
     * @param articleId 文章id
     * @return JSONObject
     */
    public static JSONObject getQueryArticleIdParams(String articleId) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_ARTICLE_ID, articleId);
        return csJo;
    }

    /**
     * 文章ids参数
     *
     * @param articleId 文章id
     * @return JSONObject
     */
    public static JSONObject getQueryArticleIdsParams(String articleId) {
        return getQueryArticleIdsParams(Collections.singletonList(articleId));
    }

    /**
     * 文章ids参数
     *
     * @param articleIds 文章id集合
     * @return JSONObject
     */
    public static JSONObject getQueryArticleIdsParams(List<String> articleIds) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_ARTICLE_ID_S, articleIds);
        return csJo;
    }

    /**
     * 标题参数
     *
     * @param title 标题
     * @return JSONObject
     */
    public static JSONObject getQueryTitleParams(String title) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_TITLE, title);
        return csJo;
    }

    /**
     * 时间参数
     *
     * @param timeType 时间类型
     * @return JSONObject
     */
    public static JSONObject getQueryTimeParams(String timeType) {
        DateUtils.DateRange dateTimeRange = DateUtils.findDateTimeRange(timeType);
        return EsParamsUtils.getQueryTimeParams(dateTimeRange.getStartDateTimeStr(), dateTimeRange.getEndDateTimeStr());
    }

    /**
     * 时间参数
     *
     * @param timeType      时间类型
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return JSONObject
     */
    public static JSONObject getQueryTimeParams(String timeType, String startDateTime, String endDateTime) {
        DateUtils.DateRange dateTimeRange = DateUtils.findDateTimeRange(timeType, startDateTime, endDateTime);
        return EsParamsUtils.getQueryTimeParams(dateTimeRange.getStartDateTimeStr(), dateTimeRange.getEndDateTimeStr());
    }


    /**
     * 时间参数
     *
     * @param timeType      时间类型
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return JSONObject
     */
    public static JSONObject getQueryTimeParams(String timeType, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        DateUtils.DateRange dateTimeRange = DateUtils.findDateTimeRange(timeType, startDateTime, endDateTime);
        return EsParamsUtils.getQueryTimeParams(dateTimeRange.getStartDateTimeStr(), dateTimeRange.getEndDateTimeStr());
    }

    /**
     * 时间参数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return JSONObject
     */
    public static JSONObject getQueryTimeParams(String startTime, String endTime) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_START_TIME, startTime);
        csJo.put(ES_END_TIME, endTime);
        return csJo;
    }

    /**
     * 时间参数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return JSONObject
     */
    public static JSONObject getQueryTimeParams(LocalDateTime startTime, LocalDateTime endTime) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_START_TIME, DateUtils.formatDateTime(startTime));
        csJo.put(ES_END_TIME, DateUtils.formatDateTime(endTime));
        return csJo;
    }

    /**
     * 时间跨度参数
     *
     * @param timeSpan 时间跨度
     * @return JSONObject
     */
    public static JSONObject getQueryTimeSpanParams(String timeSpan) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_TIME_SPAN, timeSpan);
        return csJo;
    }

    /**
     * 搜索参数
     *
     * @param searchArea  搜索范围 all>>全部,title>>标题,content>>内容
     * @param searchValue 搜索关键字
     * @return JSONObject
     */
    public static JSONObject getQuerySearchParams(String searchArea, String searchValue) {
        JSONObject csJo = new JSONObject();
        if (StringUtils.isNotBlank(searchValue)) {
            if (Objects.equal(SearchArea.AUTHOR.getType(), searchArea)) {
                csJo.put(ES_AUTHOR, Collections.singletonList(searchValue));
            } else if (Objects.equal(SearchArea.SITE_NAME.getType(), searchArea)) {
                csJo.put(ES_SITE_NAME, Collections.singletonList(searchValue));
            } else if (Objects.equal(SearchArea.CHANNEL.getType(), searchArea)) {
                csJo.put(ES_CHANNEL, Collections.singletonList(searchValue));
            } else {
                csJo.put(ES_SEARCH_VALUE, MStringUtils.splitStr(searchValue, " "));
                csJo.put(ES_SEARCH_AREA, searchArea);
            }

        }
        return csJo;
    }

    /**
     * 阅读状态参数
     *
     * @param readState 阅读状态
     * @return JSONObject
     */
    public static JSONObject getQueryReadStateParams(Integer readState) {
        JSONObject csJo = new JSONObject();
        if (!Objects.equal(ReadState.ALL.getCode(), readState)) {
            csJo.put(ES_READ_STATE, (Objects.equal(readState, ReadState.READ.getCode())));
        }
        return csJo;
    }

    /**
     * 默认排重状态 -- 排重
     *
     * @return JSONObject
     */
    public static JSONObject getQueryTrueRepeatStateParams() {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_REPEAT_STATE, Boolean.TRUE);
        return csJo;
    }

    /**
     * 默认排重状态 -- 不排重
     *
     * @return JSONObject
     */
    public static JSONObject getQueryFalseRepeatStateParams() {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_REPEAT_STATE, Boolean.FALSE);
        return csJo;
    }

    /**
     * 排重状态参数
     *
     * @param repeatState 排重状态
     * @return JSONObject
     */
    public static JSONObject getQueryRepeatStateParams(Boolean repeatState) {
        JSONObject csJo = new JSONObject();
        csJo.put(ES_REPEAT_STATE, repeatState);
        return csJo;
    }

    /**
     * 默认排序参数 --按照发布时间倒叙
     *
     * @return 追加后的参数
     */
    public static JSONObject getQueryDefaultSortParams() {
        return getQuerySortParams(DEFAULT_SORT_NAME, SortOrder.DESC.getCode());
    }

    /**
     * 默认排序参数 --按照发布时间倒叙
     *
     * @return 追加后的参数
     */
    public static JSONObject getQueryDefaultSortParams(String sortOrder) {
        return getQuerySortParams(DEFAULT_SORT_NAME, sortOrder);
    }

    /**
     * 排序参数
     *
     * @param sortName  排序名称
     * @param sortOrder 排序类型
     * @return 追加后的参数
     */
    public static JSONObject getQuerySortParams(String sortName, String sortOrder) {
        JSONObject param = new JSONObject();
        param.put(ES_SORT_NAME, sortName);
        param.put(ES_SORT_ORDER, sortOrder);
        return param;
    }

    /**
     * 语言,境内参数默认参数 中文境内
     *
     * @return 追加后的参数
     */
    public static JSONObject getQueryDefaultChineseAndRegionInnerParams() {
        JSONObject param = new JSONObject();
        param.put(ES_MULTILINGUAL, Multilingual.CHINESE.getName());
        param.put(ES_REGION, Region.REGION_INNER.getCode());
        return param;
    }

    /**
     * 语言,境内外参数
     *
     * @param multilingual 语言
     * @param region       境内外
     * @return 追加后的参数
     */
    public static JSONObject getQueryMultilingualAndRegionParams(String multilingual, Integer region) {
        JSONObject param = new JSONObject();
        param.put(ES_MULTILINGUAL, getMultilingualTypeNameByType(multilingual));
        param.put(ES_REGION, region);
        return param;
    }

    /**
     * 语言参数
     *
     * @param multilingual 语言
     * @return 追加后的参数
     */
    public static JSONObject getQueryMultilingualParams(String multilingual) {
        JSONObject param = new JSONObject();
        param.put(ES_MULTILINGUAL, getMultilingualTypeNameByType(multilingual));
        return param;
    }

    /**
     * 范围参数
     *
     * @param region 境内外
     * @return 追加后的参数
     */
    public static JSONObject getQueryRegionParams(String region) {
        Integer code = getRegionByType(region).orElse(Region.ALL).getCode();
        return getQueryRegionParams(code);
    }

    /**
     * 范围参数
     *
     * @param region 境内外
     * @return 追加后的参数
     */
    public static JSONObject getQueryRegionParams(Integer region) {
        JSONObject param = new JSONObject();
        if (!Objects.equal(region, Region.ALL.getCode())) {
            param.put(ES_REGION, region);
        }
        return param;
    }

    /**
     * 统计参数
     *
     * @param item item对象
     * @return 追加后的参数
     */
    public static JSONObject getQueryItemParams(String item) {
        JSONObject param = new JSONObject();
        param.put(ES_ITEM, item);
        return param;
    }

    /**
     * 统计参数大小
     *
     * @param itemSize item对象大小
     * @return 追加后的参数
     */
    public static JSONObject getQueryItemSizeParams(Long itemSize) {
        JSONObject param = new JSONObject();
        param.put(ES_ITEM_SIZE, itemSize);
        return param;
    }

    /**
     * 境外参数
     *
     * @param socialType socialType
     * @return 追加后的参数
     */
    public static JSONObject getQuerySocialTypeUrlMainParams(String socialType) {
        List<String> socialTypeList = MStringUtils.stringCommaRtListString(socialType);
        return getQuerySocialTypeUrlMainParams(socialTypeList);
    }

    /**
     * 境外参数
     *
     * @param socialTypeList socialTypeList
     * @return 追加后的参数
     */
    public static JSONObject getQuerySocialTypeUrlMainParams(List<String> socialTypeList) {
        List<String> socialTypeUrl =
                Optional.ofNullable(socialTypeList)
                        .map(socialTypes -> socialTypes.parallelStream()
                                .map(s -> getSocialTypeByType(s).map(SocialType::getUrl).orElse(Lists.newArrayList()))
                                .flatMap(Collection::parallelStream)
                                .collect(Collectors.toList()))
                        .orElseGet(Collections::emptyList);
        JSONObject param = new JSONObject();
        param.put(ES_URL_MAIL, socialTypeUrl);
        return param;
    }


    /**
     * url 参数
     *
     * @param urlList urlList
     * @return JSONObject
     */
    public static JSONObject getQueryUrlMainParams(List<String> urlList) {
        urlList = urlList.stream()
                .filter(StringUtils::isNotEmpty)
                .map(MStringUtils::getUrlMain)
                .collect(Collectors.toList());
        JSONObject param = new JSONObject();
        param.put(ES_URL_MAIL, urlList);
        return param;
    }

    /**
     * url 参数
     *
     * @param urlList urlList
     * @return JSONObject
     */
    public static JSONObject getQueryUrlRootParams(List<String> urlList) {
        urlList = urlList.stream()
                .filter(StringUtils::isNotEmpty)
                .map(MStringUtils::getUrlRoot)
                .collect(Collectors.toList());
        JSONObject param = new JSONObject();
        param.put(ES_URL_ROOT, urlList);
        return param;
    }


    public static JSONObject getQueryUrlExactMatch(List<String> urlList, Integer urlExactMatchState) {
        if (Objects.equal(EnabledState.ON.getCode(), urlExactMatchState)) {
            return getQueryUrlMainParams(urlList);
        } else {
            return getQueryUrlRootParams(urlList);
        }
    }

    /**
     * 作者集合 参数
     *
     * @param author 作者
     * @return JSONObject
     */
    public static JSONObject getQueryAuthor(String author) {
        return getQueryAuthor(Collections.singletonList(author));
    }

    /**
     * 作者集合 参数
     *
     * @param authorList 作者集合
     * @return JSONObject
     */
    public static JSONObject getQueryAuthor(List<String> authorList) {
        authorList = authorList.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        JSONObject param = new JSONObject();
        param.put(ES_AUTHOR, authorList);
        return param;
    }

    /**
     * 站点集合 参数
     *
     * @param siteNameList 作者集合
     * @return JSONObject
     */
    public static JSONObject getQuerySiteName(List<String> siteNameList) {
        siteNameList = siteNameList.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        JSONObject param = new JSONObject();
        param.put(ES_SITE_NAME, siteNameList);
        return param;
    }

    /**
     * 频道集合 参数
     *
     * @param channelList 频道集合
     * @return JSONObject
     */
    public static JSONObject getQueryChannel(List<String> channelList) {
        channelList = channelList.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        JSONObject param = new JSONObject();
        param.put(ES_CHANNEL, channelList);
        return param;
    }

    public static JSONObject getQueryHotWordCount(int count) {
        JSONObject param = new JSONObject();
        param.put(ES_HOT_WORD_COUNT, count);
        return param;
    }

    public static JSONObject getQueryJudgeType(String judgeType) {
        JSONObject param = new JSONObject();
        param.put(ES_JUDGE_TYPE, judgeType);
        return param;
    }

    public static JSONObject getQueryJudgeAuto(int judgeAuto) {
        JSONObject param = new JSONObject();
        param.put(ES_JUDGE_AUTO, judgeAuto);
        return param;
    }

    public static JSONObject getQueryRefreshState(boolean refreshState) {
        JSONObject param = new JSONObject();
        param.put(ES_REFRESH_STATE, refreshState);
        return param;
    }

}
