package com.example.distributedbookdetails.externalService.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.distributedbookdetails.externalService.domain.EsArticle;
import com.example.distributedcommon.custom.SysConst;
import com.example.distributedcommon.utils.DateUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/9/6 17:50
 * description: 文章工具类
 */
public class ArticleUtils {

    /**
     * json 转换 esArticle
     *
     * @param jsonObj json
     * @return EsArticle
     */
    public static EsArticle jsonObjectConvertEsArticle(JSONObject jsonObj) {
        EsArticle esArticle = null;
        if (jsonObj != null) {
            esArticle = new EsArticle();
            esArticle.setId(jsonObj.getString("id"));   //文章id
            esArticle.setRelId(jsonObj.getString("relId"));   //相似文章id
            esArticle.setRegion(jsonObj.getInteger("country"));// 境内外
            esArticle.setCarrier(SysConst.getCarrierTypeByCode(jsonObj.getInteger("carrie")));// 载体
            esArticle.setSiteName(jsonObj.getString("siteName"));// 网站/引擎名称
            esArticle.setChannel(jsonObj.getString("channel"));
            esArticle.setCreateTime(DateUtils.parseDateTimeUTC(jsonObj.getString("createTime")));// 索引时间
            esArticle.setGatherTime(DateUtils.parseDateTimeUTC(jsonObj.getString("gatherTime")));// 采集时间
            LocalDateTime publishTime = DateUtils.parseDateTimeUTC(jsonObj.getString("publishTime"));
            esArticle.setPublishTime(publishTime); // 发布时间
            esArticle.setPublishDate(publishTime.toLocalDate());
            esArticle.setUrlMain(jsonObj.getString("urlMain"));// url主域名
            esArticle.setUrl(jsonObj.getString("url"));// 原文地址
            esArticle.setAuthor(jsonObj.getString("author"));// 作者
            esArticle.setFrom(jsonObj.getString("from"));// 转载源 [新闻、视频]
            esArticle.setView(jsonObj.getLong("view")); // 点击数 [新闻、论坛、博客、视频]
            esArticle.setComment(jsonObj.getLong("comment"));// 评论数 [新闻、论坛、博客、视频]
            esArticle.setZan(jsonObj.getLongValue("like"));//点赞数
            esArticle.setIsRelated(jsonObj.getInteger("isrelated")); //是否相关  0 否 1是
            esArticle.setIsOpinion(jsonObj.getInteger("isyuqing")); //是否舆情 0 否 1是
            List<Long> thesaurusThemeIds = jsonObj.getJSONArray("yuqing").parallelStream().map(String::valueOf).map(Long::valueOf).collect(toList());
            esArticle.setThesaurusThemeIds(thesaurusThemeIds);
            String emotion = "";
            Integer isNegative = jsonObj.getInteger("isnegative"); //负面
            Integer isNeutral = jsonObj.getInteger("isneutral");  //中性
            Integer isPositive = jsonObj.getInteger("ispositive");    //正面
            if (isNegative == 1 && isNeutral == 0 && isPositive == 0) {
                emotion = SysConst.Emotion.NEGATIVE.getType();
            }
            if (isNegative == 0 && isNeutral == 1 && isPositive == 0) {
                emotion = SysConst.Emotion.NEUTRAL.getType();
            }
            if (isNegative == 0 && isNeutral == 0 && isPositive == 1) {
                emotion = SysConst.Emotion.POSITIVE.getType();
            }
            esArticle.setEmotion(emotion);
            String title = jsonObj.getString("title");
            String cleanTitle = jsonObj.getString("cleanTitle");
            esArticle.setOssPath(jsonObj.getString("ossPath"));// 采集文件的名称
            esArticle.setTitleOriginal(title);
            esArticle.setCleanTitleOriginal(cleanTitle);
            esArticle.setTitle(title);// 原文标题
            esArticle.setCleanTitle(cleanTitle); // 将文章标题中出现的特殊的字符替换掉
            esArticle.setTitleCut(title); // 将文章标题中出现的特殊的字符替换掉
            esArticle.setCleanTitleCut(cleanTitle); // 将文章标题中出现的特殊的字符替换掉
            esArticle.setContentWords(jsonObj.getLongValue("contentWords"));//文章字数
            esArticle.setSummary(jsonObj.getString("summary"));// 摘要
            esArticle.setContent(jsonObj.getString("content"));//内容
            Integer articleType = SysConst.ArticleType.TRADITION.getCode();
            //短视频
            if (Objects.equals(esArticle.getCarrier(), SysConst.Carrier.CARRIER_SHORT_VIDEO.getType())) {
                articleType = jsonObj.containsKey("type") && StringUtils.isNotEmpty(jsonObj.getString("type")) ?
                        jsonObj.getIntValue("type") :
                        SysConst.ArticleType.TRADITION.getCode();
            }
            esArticle.setArticleType(articleType);
            esArticle.setArticleCoverUrl(jsonObj.getString("images"));

            esArticle.setReadUserIds(JSONObject.parseArray(jsonObj.getString("readUsers"), Long.class));// 已读用户的ID数组，分析时默认是空集
            esArticle.setUnRelatedUserIds(JSONObject.parseArray(jsonObj.getString("unRelatedUsers"), Long.class)); // 研判为与我无关的用户的ID数组，分析时默认是空集
            esArticle.setEntityArea(JSONObject.parseArray(jsonObj.getString("entityArea"), String.class));//实体识别地域名
            esArticle.setEntityName(JSONObject.parseArray(jsonObj.getString("entityName"), String.class));//实体识别人名
            esArticle.setEntityOrganization(JSONObject.parseArray(jsonObj.getString("entityOrganization"), String.class));//实体识别组织机构名
            esArticle.setSimilarArticleCount(0L);
            if (Objects.equals(SysConst.Multilingual.MONGOLIAN.getName(), jsonObj.getString("language"))) {
                esArticle.setMultilingualType(SysConst.Multilingual.MONGOLIAN.getType());
            } else if (Objects.equals(SysConst.Multilingual.VIVIEN.getName(), jsonObj.getString("language"))) {
                esArticle.setMultilingualType(SysConst.Multilingual.VIVIEN.getType());
            } else if (Objects.equals(SysConst.Multilingual.TIBETAN.getName(), jsonObj.getString("language"))) {
                esArticle.setMultilingualType(SysConst.Multilingual.TIBETAN.getType());
            } else {
                esArticle.setMultilingualType(SysConst.Multilingual.CHINESE.getType());
            }
        }
        return esArticle;
    }
}
