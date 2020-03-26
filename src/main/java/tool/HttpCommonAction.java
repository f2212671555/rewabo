package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpCommonAction {
    // 用來取得doPost請求的body
    public static String getRequestBody(BufferedReader httpServletRequestGetReaderBuffer) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            while ((line = httpServletRequestGetReaderBuffer.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {

        }
        return jb.toString();
    }

    public static String stringParser(String url) throws IOException {
        String url2 = new String();
        for (int j = 0; j < url.length(); j++) {
            if (url.substring(j, j + 1).matches("[\\u4e00-\\u9fa5]+")) {
                try {
                    url2 = url2 + URLEncoder.encode(url.substring(j, j + 1), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                url2 = url2 + url.substring(j, j + 1).toString();
            }
        }

        return url2;
    }

    public static String getRewaboDate() {
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return formatter.format(dNow);
    }
}
