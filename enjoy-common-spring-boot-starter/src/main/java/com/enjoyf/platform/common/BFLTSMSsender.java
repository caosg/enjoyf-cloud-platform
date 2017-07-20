package com.enjoyf.platform.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-30 下午3:33
 * Description:
 */
public class BFLTSMSsender {

    private final Logger log = LoggerFactory.getLogger(BFLTSMSsender.class);

    private static final String API_URL = "http://cf.lmobile.cn/submitdata/service.asmx/g_Submit";
    private static final String SNAME = "dllxfd00";
    private static final String SPWD = "QZKcI2cOb8PW";

    private String smsName = SNAME;
    private String smsPwd = SPWD;

    public BFLTSMSsender() {
    }

    public BFLTSMSsender(String smsName, String smsPwd) {
        this.smsName = smsName;
        this.smsPwd = smsPwd;
    }

    /**
     * string sname,    提交账户
     * string spwd,     提交账户的密码
     * string scorpid,  企业代码（扩展号，不确定请赋值空）
     * string sprdid,   产品编号
     * string sdst,   接收号码间用英文半角逗号“,”隔开，触发产品一次只能提交一个,,其他产品一次不能超过10万个号码
     * string smsg   信息内容, UTF-8编码，通常为70汉字以内，具体由平台内部决定
     *
     * @return
     */
    public SendResult sendAction(String phone, String content) {
        SendResult returnObj = new SendResult();

        try {
            String postData = "sname=" + smsName + "&spwd=" + smsPwd + "&scorpid=&sprdid=1012818&sdst=" + phone + "&smsg=" + java.net.URLEncoder.encode(content, "utf-8");
            //发送POST请求
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                returnObj.setCode(SendResult.URL_ERROR);
                return returnObj;
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return getSendResult(result);
        } catch (IOException e) {
//            logger.error(this.getClass().getName() + " occured Exception.e:", e);

            returnObj.setCode(SendResult.URL_ERROR);

        } catch (Exception e) {
            returnObj.setCode(SendResult.SEND_ERROR);
        }
        return returnObj;
    }

    private SendResult getSendResult(String xmlResult) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String,String> map=xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xmlResult, Map.class);

        SendResult sendResult = new SendResult();
        if (map == null) {
            sendResult.setCode(SendResult.SEND_ERROR);
            return sendResult;
        }
        int code = Integer.parseInt(map.get("State"));
        String msg = map.get("MsgState");
        sendResult.setCode(code);
        sendResult.setMsg(msg);
        return sendResult;
    }

    public static void main(String[] args) {
        System.out.println(new BFLTSMSsender ("dllxfd00","QZKcI2cOb8PW").sendAction("18511849086","欢迎来到着迷网【着迷网】"));

    }
}


