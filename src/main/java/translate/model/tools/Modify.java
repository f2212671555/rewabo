package translate.model.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class Modify {

    public static JsonObject getJsonObject(String filePath){
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(content);
        return jsonTree.getAsJsonObject();
    }

    //public static <T> T generateObject(String filePath,Class<?> classOfT){
    //    Gson gson = new Gson();
    //    String content;
    //    Object object = null;
    //    JsonParser parser;
    //    try {
    //        object = classOfT.newInstance();
    //        content = new String(Files.readAllBytes(Paths.get(filePath)));
    //        parser = new JsonParser();
    //        JsonElement jsonTree = parser.parse(content);
    //        object = gson.fromJson(jsonTree,classOfT);
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }finally {
    //        content = null;
    //        parser = null;
    //        gson = null;
    //        return (T)object;
    //    }
    //}

    public static String dateToTimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JsonObject setHistoryOrder(JsonObject jsonObject, String orderNumber, String timestamp, String totalCost)
    {

        //set
        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject payload = result.getAsJsonObject("payload");
        JsonObject summary = payload.getAsJsonObject("summary");

        payload.addProperty("orderNumber",orderNumber);
        payload.addProperty("timestamp",timestamp);
        summary.addProperty("totalCost",totalCost);

        return jsonObject;
    }

    public static JsonObject setElements(JsonObject jsonObject,JsonArray elements)
    {

        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject payload = result.getAsJsonObject("payload");

        payload.add("elements",elements);

        return jsonObject;
    }

    public static JsonObject setChatId(String chatId,JsonObject jsonObject) {
        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject payload = result.getAsJsonObject("payload");
        JsonObject originalMessage = result.getAsJsonObject("originalMessage");
        JsonObject chat = originalMessage.getAsJsonObject("chat");
        payload.addProperty("chatId",chatId);
        chat.addProperty("id",chatId);
        return jsonObject;
    }

    public static JsonObject setMessageId(String messageId,JsonObject jsonObject) {
        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject payload = result.getAsJsonObject("payload");
        payload.addProperty("messageId",messageId);
        return jsonObject;
    }

}
