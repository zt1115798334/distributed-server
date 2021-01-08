package com.example.distributedcommon.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/11/17 13:43
 * description:
 */
@Slf4j
public class MStringUtils {

    private static final Pattern linePattern = Pattern.compile("_(\\w)");
    private static final Pattern humpPattern = Pattern.compile("[A-Z]");

    public static final List<String> specialSymbols = Lists.newArrayList("\n", "\r", "\b", "\f", "\t");

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String getExceptionDetail(Exception e) {
        StringBuilder stringBuilder = new StringBuilder(e.toString() + "\n");
        StackTraceElement[] messages = e.getStackTrace();
        for (StackTraceElement message : messages) {
            stringBuilder.append("\t").append(message.toString()).append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * 中去掉网页HTML标记的方法
     *
     * @param htmlStr html文本
     * @return String
     */
    public static String delHtmlTag(String htmlStr) {
        if (StringUtils.isNotEmpty(htmlStr)) {
            Matcher m_script = Pattern.compile(RegularMatchUtils.REG_SCRIPT, Pattern.CASE_INSENSITIVE).matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            Matcher m_style = Pattern.compile(RegularMatchUtils.REG_STYLE, Pattern.CASE_INSENSITIVE).matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            Matcher m_html = Pattern.compile(RegularMatchUtils.REG_HTML, Pattern.CASE_INSENSITIVE).matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            Matcher m_special = Pattern.compile(RegularMatchUtils.REG_SPECIAL, Pattern.CASE_INSENSITIVE).matcher(htmlStr);
            htmlStr = m_special.replaceAll(""); // 过滤特殊标签
        }
        return htmlStr;
    }


    /**
     * 转全角的函数(SBC case)
     * 全角字符串
     * 全角空格为12288,半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input input
     * @return String
     */
    public static String toSBC(String input) { //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 转半角的函数(DBC case)
     * 任意字符串
     * 半角字符串
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input input
     * @return String
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * @param text        字符串
     * @param matchingStr 需要匹配的字符
     * @param spanStart   匹配字符前添加的字符
     * @param spanEnd     匹配字符后添加的字符
     * @return String
     */
    public static String signString(String text, String matchingStr, String spanStart, String spanEnd) {
        matchingStr = matchingStr.trim();
        StringBuilder sb = new StringBuilder(text);
        Pattern pattern = Pattern.compile("(^|>)([^<]*)" + matchingStr + "([^<]*)(<|$)", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(text);
        int len = 0;
        while (match.find()) {
            int start = match.start();
            int end = match.end();
            String substring = sb.substring(start + len, end + len);
            Pattern patternDeviation = Pattern.compile(matchingStr, Pattern.CASE_INSENSITIVE);
            Matcher matchDeviation = patternDeviation.matcher(substring);
            while (matchDeviation.find()) {
                int startDeviation = matchDeviation.start();
                int endDeviation = matchDeviation.end();
                sb.insert(start + len + startDeviation, spanStart);
                sb.insert(end + len + spanStart.length() - (substring.length() - endDeviation), spanEnd);
                len += (spanStart + spanEnd).length();
            }
        }
        return sb.toString();
    }


    /**
     * 将中文符号转换为英文符号 并且清除空字符串
     *
     * @param text text
     * @return String
     */
    public static String stringToDBCClearBlank(String text) {
        return Optional.ofNullable(text)
                .map(MStringUtils::toDBC)
                .map(str -> String.join(",", MStringUtils.stringCommaRtListString(str))) //清空 空字符串
                .orElse("");
    }

    /**
     * 对包含 + * ，( ) 进行切分
     *
     * @param text test
     * @return List<String>
     */
    public static List<String> splitStr(String text) {
        return Optional.ofNullable(text).map(t -> splitStr(t, "[+*,()]")).orElse(Lists.newArrayList());
    }

    /**
     * 对包含 + * ，( ) 进行切分
     *
     * @param text test
     * @return List<String>
     */
    public static List<String> splitStr(List<String> text) {
        return text.parallelStream().map(MStringUtils::splitStr).flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    /**
     * 对满足规则进行切分
     *
     * @param text test
     * @return List<String>
     */
    public static List<String> splitStr(String text, String regex) {
        return Optional.ofNullable(text).map(t ->
                Arrays.stream(t.split(regex))
                        .filter(StringUtils::isNotEmpty)
                        .collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }


    /**
     * 切分成最小粒度的排列组合词
     *
     * @param text test
     * @return List<String>
     */
    public static List<String> splitMinGranularityStr(String text) {
        return Optional.ofNullable(text).map(te -> Arrays.stream(te.split(","))
                .filter(StringUtils::isNotBlank)
                .map(comWord -> {
                    List<List<String>> collect = Arrays.stream(comWord.split("[*]"))
                            .map(word -> Arrays.stream(word.split("[+()]"))
                                    .filter(StringUtils::isNotEmpty)
                                    .collect(Collectors.toList()))
                            .collect(Collectors.toList());
                    return calculateCombination(collect);
                }).flatMap(Collection::parallelStream)
                .distinct()
                .map(String::toLowerCase)
                .collect(Collectors.toList())).orElseGet(Lists::newArrayList);
    }

    public static void main(String[] args) {
        System.out.println(getUrlRoot("www.baidu" +
                ".com"));
    }


    /**
     * 算法二，非递归计算所有组合
     *
     * @param inputList 所有数组的列表
     */
    private static List<String> calculateCombination(List<List<String>> inputList) {
        int n = inputList.size();

        List<Integer> combination = IntStream.range(0, n).map(i -> 0).boxed().collect(Collectors.toList());

        int i = 0;
        boolean isContinue;
        List<String> result = Lists.newArrayList();

        do {
            List<String> str = IntStream.range(0, n).boxed()
                    .filter(j -> inputList.get(j) != null && !inputList.get(j).isEmpty())
                    .map(j -> inputList.get(j).get(combination.get(j)))
                    .collect(Collectors.toList());

            result.add(String.join("*", str));

            i++;
            combination.set(n - 1, i);
            for (int j = n - 1; j >= 0; j--) {
                if (combination.get(j) >= inputList.get(j).size()) {
                    combination.set(j, 0);
                    i = 0;
                    if (j - 1 >= 0) {
                        combination.set(j - 1, combination.get(j - 1) + 1);
                    }
                }
            }
            isContinue = false;
            for (Integer integer : combination) {
                if (integer != 0) {
                    isContinue = true;
                    break;
                }
            }
        } while (isContinue);
        return result;
    }

    /**
     * id转换为 #1#，#2#，的格式 方便模糊查询  否则会出现查询不准确的
     *
     * @param text text
     * @return String
     */
    public static String decorateStr(String text) {
        if (StringUtils.isNotBlank(text)) {
            List<Long> longList = Arrays.stream(text.trim().split(",")).
                    map(Long::valueOf)
                    .collect(Collectors.toList());
            return decorateStr(longList);
        }
        return text;
    }


    /**
     * id转换为 #1#，#2#，的格式 方便模糊查询  否则会出现查询不准确的
     *
     * @param text long集合
     * @return String
     */
    public static String decorateStr(List<Long> text) {
        return Optional.ofNullable(text)
                .map(str ->
                        str.stream()
                                .filter(Objects::nonNull)
                                .map(MStringUtils::strAddSign)
                                .collect(Collectors.joining(",")))
                .orElse(StringUtils.EMPTY);
    }

    public static String decorateStrAddElement(String decorateStr, Long element) {
        List<Long> oldStr = decorateRecoveryStr(decorateStr);
        oldStr.add(element);
        oldStr = oldStr.stream().distinct().collect(Collectors.toList());
        return decorateStr(oldStr);

    }

    public static String decorateStrRemoveElement(String decorateStr, Long element) {
        List<Long> oldStr = decorateRecoveryStr(decorateStr);
        oldStr.remove(element);
        return decorateStr(oldStr);
    }

    public static String decorateStrRemoveElement(List<Long> decorate, Long element) {
        decorate.remove(element);
        return decorateStr(decorate);
    }


    public static String strAddSign(Object text) {
        return "#" + text + "#";
    }

    /**
     * id转换为 #1#，#2#，的格式 转换为list<Long>
     *
     * @param text text
     * @return List<Long>
     */
    public static List<Long> decorateRecoveryStr(String text) {
        return Optional.ofNullable(text).map(str -> Arrays.stream(text.replace("#", "").split(","))
                .filter(StringUtils::isNotBlank)
                .map(Long::valueOf).collect(Collectors.toList())).orElse(Lists.newArrayList());
    }

    /**
     * 截取字符串
     *
     * @param text        内容
     * @param splitLen    长度
     * @param replaceText 替换符号
     * @return String
     */
    public static String interceptingReplacing(String text, Integer splitLen, String replaceText) {
        return Optional.ofNullable(text)
                .map(str -> str.length() > splitLen ? StringUtils.substring(str, 0, splitLen) + replaceText : str)
                .orElse("");

    }

    /**
     * 请求参数格式转换 aop 接口使用
     *
     * @param parameterNames  parameterNames
     * @param parameterValues parameterValues
     * @return String
     */
    public static String parseParams(String[] parameterNames, Object[] parameterValues) {
        StringBuilder stringBuffer = new StringBuilder();
        int length = parameterNames.length;
        for (int i = 0; i < length; i++) {
            String parameterName = Optional.ofNullable(parameterNames[i]).orElse("unknown");
            Object parameterValueObj = Optional.ofNullable(parameterValues[i]).orElse("unknown");
            Class<?> parameterValueClazz = parameterValueObj.getClass();
            String parameterValue;
            if (parameterValueClazz.isPrimitive() ||
                    parameterValueClazz == String.class) {
                parameterValue = parameterValueObj.toString();
            } else if (parameterValueObj instanceof Serializable) {
                parameterValue = JSON.toJSONString(parameterValueObj);
            } else {
                parameterValue = parameterValueObj.toString();
            }
            stringBuffer.append(parameterName).append(":").append(parameterValue).append(" ");
        }
        return stringBuffer.toString();
    }

    /**
     * 拼接邮件内容
     *
     * @param title           标题
     * @param siteName        站点名称
     * @param publishTime     发布时间
     * @param url             url
     * @param officialNetwork 官网地址
     * @param webName         官网名称
     * @return String
     */
    public static String splicingWarningEmailText(String title, String siteName, String publishTime, String url,
                                                  String officialNetwork, String webName) {
        StringBuilder stringBuilder = new StringBuilder();
        webName = StringUtils.isNotEmpty(webName) ? webName : "未知站点";
        stringBuilder.append("标题：").append(title).append("<br>")
                .append("来源：").append(siteName).append("<br>")
                .append("发布时间：").append(publishTime).append("<br>")
                .append("文章链接：").append("<a target='_blank' href='").append(url).append("'>").append(url).append("</a>")
                .append("<br>").append("<br>").append("<br><a href='").append(officialNetwork)
                .append("' target='_blank'>【").append(webName).append("】<a>");
        return stringBuilder.toString();
    }

    /**
     * 拼接报告邮件内容
     *
     * @param briefingType    标题
     * @param url             url
     * @param officialNetwork 官网地址
     * @param webName         官网名称
     * @return String
     */
    public static String splicingBriefingEmailText(String briefingType, String url,
                                                   String officialNetwork, String webName) {
        return "正文：收到一份来自态势管家的态势" + briefingType + "，请注意查收!!" + "<br>" +
                "链接：" + "<a target='_blank' href='" + url + "'>" + "点击 查看" + "</a>" +
                "<br>" + "<br>" + "<br><a href='" + officialNetwork +
                "' target='_blank'>【" + webName + "】<a>";
    }


    public static String splicingSmsCodeText(String code) {
        return "验证码为：" + code + "，十分钟内有效！";
    }

    /**
     * 拼接短信内容
     *
     * @param publishTime 发布时间
     * @param url         url
     * @param webName     官网名称
     * @return String
     */
    public static String splicingWarningShortMessage(String publishTime, String url, String webName) {
        return "【" + webName + "】" + publishTime + "监测到一条预警信息，请及时关注，文章链接" + url;
    }

    /**
     * 正则提取url中的主域名
     *
     * @param url url
     * @return String
     */
    public static String getUrlMain(String url) {
        Pattern urlMainPattern = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher m = urlMainPattern.matcher(url);
        String urlMain;
        if (m.find()) {
            urlMain = m.group();
        } else {
            urlMain = "default.com";
        }
        return urlMain;
    }

    /**
     * 提取url中的根域名
     *
     * @param url url
     * @return 根域名
     */
    public static String getUrlRoot(String url) {
        if (!Pattern.matches(RegularMatchUtils.MATCH_URL_2, url)) {
            url = "http://" + url;
        }
        try {
            URL u = new URL(url.toLowerCase());
            String host = u.getHost();
            StringBuilder root = new StringBuilder();
            String[] rootMembers = host.split("\\.");
            // 一般情况下，取host的最后2个，如www.abc.com取abc.com
            int n = 2;
            // 判断TLD后缀是否超过1个，比如edu.cn，如果是，则判断域名倒数第2个后缀是否是.com等（.com.cn形式）
            if (rootMembers.length > 2) {
                String secondLastMember = rootMembers[rootMembers.length - 2];
                if (secondLastMember.matches("^com$|^net$|^gov$|^edu$|^co$|^org$"))
                    n = 3;
            }
            // 确定了n的取数后，将结果保存到StringBuffer中，保存顺序是自左至右
            for (int i = n; i > 0; i--) {
                try {
                    root.append(rootMembers[host.split("\\.").length - i]);
                    if ((i - 1) > 0)
                        root.append(".");
                } catch (ArrayIndexOutOfBoundsException e) {
                    log.info("Wrong Url:" + url);
                }
            }
            return root.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String specialIdKeys(Long[] specialIdArr) {
        return Optional.ofNullable(specialIdArr)
                .map(sp -> Arrays.stream(sp).map(String::valueOf).collect(Collectors.joining("_")))
                .orElse("");
    }

    public static List<String> stringCommaRtListString(String string) {
        return Optional.ofNullable(string)
                .map(str -> Arrays.stream(str.split(",")).distinct().filter(StringUtils::isNotBlank).collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }

    public static List<Integer> stringCommaRtListInteger(String string) {
        return Optional.ofNullable(string)
                .map(str -> Arrays.stream(str.split(",")).filter(StringUtils::isNotBlank).map(Integer::valueOf).distinct().collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }

    public static List<Long> stringCommaRtListLong(String string) {
        return Optional.ofNullable(string)
                .map(str -> Arrays.stream(str.split(",")).filter(StringUtils::isNotBlank).map(Long::valueOf).distinct().collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }

    public static String stringListRtStringComma(List<String> list) {
        return list.stream().map(String::valueOf).distinct().collect(Collectors.joining(","));
    }

    public static String integerListRtStringComma(List<Integer> list) {
        return list.stream().map(String::valueOf).distinct().collect(Collectors.joining(","));
    }

    public static String longListRtStringComma(List<Long> list) {
        return list.stream().map(String::valueOf).distinct().collect(Collectors.joining(","));

    }

    public static String getImageBase64(String base) {
        return Optional.ofNullable(base)
                .map(text -> text.replace("data:image/png;base64,", "")
                        .replace("data:image/jpeg;base64,", ""))
                .orElse("");
    }

    /**
     * 导出word的特殊符号处理
     *
     * @param value 过滤字符串
     * @return String
     */
    public static String filterWordSpecialSymbols(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        value = decodeIllegalCharacter(value);
        StringBuffer result = null;
        String filtered;
        for (int i = 0; i < value.length(); i++) {
            filtered = null;
            switch (value.charAt(i)) {
                case 60: // '<'
                    filtered = "&lt;";
                    break;
                case 62: // '>'
                    filtered = "&gt;";
                    break;
                case 38: // '&'
                    filtered = "&amp;";
                    break;
                case 34: // '"'
                    filtered = "&quot;";
                    break;
                case 39: // '\''
                    filtered = "'";
                    break;
            }
            if (result == null) {
                if (filtered != null) {
                    result = new StringBuffer(value.length() + 50);
                    if (i > 0) {
                        result.append(value, 0, i);
                    }
                    result.append(filtered);
                }
            } else if (filtered == null) {
                result.append(value.charAt(i));
            } else {
                result.append(filtered);
            }
        }
        return result != null ? result.toString() : value;
    }

    public static String decodeIllegalCharacter(String str) {
        StringBuilder out = new StringBuilder();
        if (str == null || ("".equals(str)))
            return "";
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            //中日朝兼容形式的unicode编码范围： U+4E00——U+9FA5
            //中日朝兼容形式扩展
            //中日朝兼容形式扩展
            //中日朝兼容形式扩展
            //全角ASCII、全角中英文标点、半宽片假名、半宽平假名、半宽韩文字母的unicode编码范围：U+FF00——U+FFEF
            //半角字符的unicode编码范围：U+0020-U+007e
            if (aChar >= 11904 && aChar <= 42191 ||
                    aChar >= 40869 && aChar <= 55295 ||
                    aChar >= 63744 && aChar <= 64255 ||
                    aChar >= 65072 && aChar <= 65103 ||
                    aChar >= 65280 && aChar <= 65519 ||
                    aChar >= 32 && aChar <= 126//全角字符的unicode编码范围：U+3000——U+301F
            ) {
                out.append(aChar);
            }
        }
        String result = out.toString().trim();
        result = result.replaceAll("\\?", "").replaceAll("\\*", "").replaceAll("[<>]", "").replaceAll("\\|", "").replaceAll("/", "");
        return result;
    }

}
