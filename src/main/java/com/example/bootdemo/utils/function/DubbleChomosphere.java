package com.example.bootdemo.utils.function;

import com.example.bootdemo.utils.DataFormatUtils.DateUtil;
import com.example.bootdemo.utils.log.LoggerUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * @Description
 * @Author panpan
 * @Date 2019-04-07 15:49
 * @Version 1.0
 **/
public class DubbleChomosphere {

    private static StringBuffer mStringBuffer;

    private static List<Byte[]> historyBalls;
    private static int listSize = 500;  //500只是一个大概的，实际上略大于500

    public static Map<Byte, Integer> appearCountRed = new HashMap<>();   //红球的范围是1-33，key为号码，value为次数
    public static Map<Byte, Integer> appearCountBlue = new HashMap<>();   //红球的范围是1-16

    public static List<Byte[]> lastBalls;   // 最近出的几次球号
    public static int lastCount = 10;   // 取最近10次

    public static List<Byte[]> random = new ArrayList<>();    // 输出随机号码，这里是三注
    public static int randomCount = 3;  // 随机n注

    public static int[] redBallChance;  // 蓝球每个号出现的概率
    public static int[] blueBallChance;  // 红球每个号出现的概率

    private static long initTime = 0L;

    /**
     * 初始化双色球数据,每天只初始化一次
     *
     * @param init 是否触发初始化，true：立即触发
     */
    public static void initSSQData(boolean init) {

        if (!init && (DateUtil.getTimeLong(new Date()) - DateUtil.getTimeLong(new Date(initTime))) < 1) {
            return;
        }
        LoggerUtil.info(DubbleChomosphere.class, "重刷双色球数据");
        historyBalls = new ArrayList<>();
        String baseUrlPrefix = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_";
        String baseUrlSuffix = ".html";
        String homeUrl = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_1.html";
        String pageCountContent = getHtmlString(homeUrl);
        int pageCount = getPageCount(pageCountContent);
        if (pageCount > 0) {
            for (int i = 1; i <= pageCount; i++) {
                String url = baseUrlPrefix + i + baseUrlSuffix;
                String pageContent = getHtmlString(url);
                if (pageContent != null && !pageContent.equals("")) {
                    getOneTermContent(pageContent);
                    if (historyBalls.size() > listSize) {
                        initTime = System.currentTimeMillis();
                        LoggerUtil.info(DubbleChomosphere.class, "完成初始化list");
                        break;
                    }
                } else {
                    System.out.println("第" + i + "页丢失");
                }

            }


            // 规则一：计算最近500多期开奖中，每个球号码出现的次数（包括红蓝球），并按次数升序排列
            appearCountRed = calRedBallAppearCount();
            appearCountBlue = calBlueBallAppearCount();

            // 规则二：根据规则一，给我提供三注
            // TODO 这里也涉及到算法，取六个红球：先把次数转化成概率，然后按照每个球的概率出号码，   取蓝球：同理
            createRandomBall();

            // 规则三：最近10次开奖记录
            lastBalls = injectLastBalls();
        }
        // 内存泄露
        historyBalls = null;
        LoggerUtil.info(DubbleChomosphere.class, "完成");
    }

    public static void randomMethod2Out (){
        createRandomBall();
    }
    /**
     * 随机生成三注
     */
    private static void createRandomBall() {
        random = new ArrayList<>();
        redBallChance = calBallChance(appearCountRed, 33);
        blueBallChance = calBallChance(appearCountBlue, 16);
        // 随机出三注
        for (int i = 0; i < randomCount; i++) {
            Byte[] bytes = new Byte[7];
            for (int j = 0; j < 7; j++) {
                if (j < 6) {
                    do {
                        Byte tmp = randomRedBall(bytes);
                        // 去重
                        if(! Arrays.asList(bytes).contains(tmp)){
                            bytes[j] = tmp;
                            break;
                        }
                    }while (true);

                }else {
                    bytes[j] = randomBlueBall(bytes);
                }
            }
            // 排序
            List<Byte> tmpList = Arrays.asList(bytes);
            List<Byte> bytes1 = tmpList.subList(0, 6);
            bytes1.sort((x1,x2) -> x1-x2);
            Byte[] bytes2 = bytes1.toArray(new Byte[7]);
            bytes2[6] = bytes[6];
            random.add(bytes2);
        }
    }

    private static int[] calBallChance(Map<Byte, Integer> appearCount, int length) {
        int[] result = new int[length];
        int i = 0;
        int sum = 0;
        Set<Byte> ts = new TreeSet(appearCount.keySet());
        Iterator<Byte> iterator = ts.iterator();
        while (iterator.hasNext()) {
            byte ballNum = iterator.next();
            sum += (10000 - appearCount.get(ballNum));
            result[i++] = sum;
        }
        return result;
    }

    public static void main(String[] args) {
        initSSQData(true);
    }

    /**
     * 获取总页数
     *
     * @param result
     */
    private static int getPageCount(String result) {
        String regex = "\\d+\">末页";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
        String[] splits = null;
        while (matcher.find()) {
            String content = matcher.group();
            splits = content.split("\"");
            break;
        }
        if (splits != null && splits.length == 2) {
            String countString = splits[0];
            if (countString != null && !countString.equals("")) {
                return Integer.parseInt(countString);
            }

        }
        return 0;
    }

    /**
     * 获取网页源码
     *
     * @return
     */
    private static String getHtmlString(String targetUrl) {
        String content = null;

        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
            connection.setRequestProperty(
                    "Accept",
                    "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            connection.setRequestProperty("UA-CPU", "x86");
            // 为什么没有deflate呢
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Content-type", "text/html");
            // keep-Alive，有什么用呢，你不是在访问网站，你是在采集。嘿嘿。减轻别人的压力，也是减轻自己。
            connection.setRequestProperty("Connection", "close");
            // 不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
            connection.setUseCaches(false);
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (200 == connection.getResponseCode()) {
                InputStream inputStream = null;
                if (connection.getContentEncoding() != null
                        && !connection.getContentEncoding().equals("")) {
                    String encode = connection.getContentEncoding()
                            .toLowerCase();
                    if (encode != null && !encode.equals("")
                            && encode.indexOf("gzip") >= 0) {
                        inputStream = new GZIPInputStream(
                                connection.getInputStream());
                    }
                }

                if (null == inputStream) {
                    inputStream = connection.getInputStream();
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                content = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return content;
    }

    private static void getOneTermContent(String pageContent) {
        String regex = "<td align=\"center\" style=\"padding-left:10px;\">[\\s\\S]+?</em></td>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);
        while (matcher.find()) {
            String oneTermContent = matcher.group();
            getOneTermNumbers(oneTermContent);
        }
    }

    private static void getOneTermNumbers(String oneTermContent) {

        String regex = ">\\d+<";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oneTermContent);
        Byte[] bytes = new Byte[7];
        int i = 0;
        while (matcher.find()) {
            if (i == 7) {
                LoggerUtil.info(DubbleChomosphere.class, "匹配筛选有问题，只有7个球的，应该不能到这里");
                break;
            }
            String content = matcher.group();
            String ballNumber = content.substring(1, content.length() - 1);
            bytes[i] = Byte.parseByte(ballNumber);
            i++;
        }
        historyBalls.add(bytes);
//        mStringBuffer.append("\r\n");
    }


    private static Map<Byte, Integer> calRedBallAppearCount() {
        // TODO 这里涉及到一个算法，比如给你一个List<Integer>,计算每个元素出现的次数，这里先用一个比较普通的方式
        Map<Byte, Integer> map = new HashMap<>();
        historyBalls.stream().forEach(bytes -> {
            for (int i = 0; i < bytes.length - 1; i++) {
                if (map.get(bytes[i]) != null) {
                    map.put(bytes[i], map.get(bytes[i]) + 1);
                } else {
                    map.put(bytes[i], 1);
                }
            }
        });
        return map;
    }


    private static Map<Byte, Integer> calBlueBallAppearCount() {
        Map<Byte, Integer> map = new HashMap<>();
        historyBalls.stream().forEach(bytes -> {
            if (map.get(bytes[6]) != null) {
                map.put(bytes[6], map.get(bytes[6]) + 1);
            } else {
                map.put(bytes[6], 1);
            }
        });
        return map;
    }

    /**
     * 随机出一个红球
     * @param bytes
     * @return
     */
    private static byte randomRedBall(Byte[] bytes) {
        int max = redBallChance[32];
        int random = new Random().nextInt(max) + 1;
        byte tmp = 0;
        for (int j = 0; j < redBallChance.length; j++) {
            if (redBallChance[j] > random) {
                tmp = Byte.valueOf((j + 1) + "");
                break;
            }
        }
        return tmp;
    }

    /**
     * 随机出一个蓝球
     * @param bytes
     * @return
     */
    private static byte randomBlueBall(Byte[] bytes) {
        int max = blueBallChance[15];
        int random = new Random().nextInt(max) + 1;
        byte tmp = 0;
        for (int j = 0; j < blueBallChance.length; j++) {
            if (blueBallChance[j] > random) {
                tmp = Byte.valueOf((j + 1) + "");
                break;
            }
        }
        return tmp;
    }

    /**
     * 注入最近几期的开奖号
     *
     * @return
     */
    private static List<Byte[]> injectLastBalls() {
        return historyBalls.subList(0, lastCount);
    }

}
