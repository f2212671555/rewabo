package recommendSet.model;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FrequentPattern {

    private MongoCollection<Document> menuColl, orderColl;
    private String[] nameMatrix;    // 拿來比對的菜單名稱
    private ArrayList<String> oneItem;
    private ArrayList<String[]> twoItem, threeItem;
    private ArrayList<String[]> orderList;
    private Integer[] twoItemTimes, threeItemTimes;
    private JSONArray result = new JSONArray();

    public FrequentPattern(MongoDatabase db) {
        menuColl = db.getCollection("Menu");
        orderColl = db.getCollection("Order");
        //test();
        init();
        //showTI();
        //showResult();
    }

    private void showTI() {
        System.out.println("Two Item:");
        for (int i=0;i<twoItem.size();i++) {
            System.out.println(i + " [" + twoItem.get(i)[0] + ", " + twoItem.get(i)[1] + ", " + twoItemTimes[i] + "]");
        }
    }

    private void showResult() {
        JSONObject jo;
        System.out.println("Am I a joke to you?");
        //System.out.println(result.length());
        for (int i=0;i<result.length();i++) {
            jo = result.getJSONObject(i);
            if (jo.getInt("ItemNum") == 2)
                System.out.print("[" + jo.getString("one") + ", " + jo.getString("two") + "] ");
            else
                System.out.print("[" + jo.getString("one") + ", " + jo.getString("two") + ", " + jo.getString("three") + "] ");

            if (i % 4 == 0)
                System.out.println();
        }
    }

    private void test() {
        String[] item = {"I1", "I2", "I3", "I4", "I5", "I6"};
        String[][] set = {
                {"I2", "I4"}, {"I1", "I3"}, {"I2", "I4", "I5"},
                {"I2", "I3"}, {"I1", "I2", "I3", "I4"}, {"I1", "I5"},
                {"I2", "I3", "I4", "I6"}, {"I1", "I2", "I3"}, {"I4", "I6"},
                {"I2", "I3", "I6"}, {"I2", "I3", "I4"}
        };
        int[] times = new int[6];    // 記錄出現次數
        Arrays.fill(times, 0);

        for (String[] s:set) {  // 遍歷訂單
            for (String i : s) {  // 遍歷餐點
                int index = Arrays.asList(item).indexOf(i);
                times[index]++;
            }
        }

        ArrayList<Integer> index = count1ItemProbability(times, 6, 11, true);

        for (Integer integer : index)
            System.out.println(item[integer] + " " + (float) times[integer] / 11);
    }

    public JSONArray getResult() {
        return result;
    }

    private void init() {
        BasicDBObject query = new BasicDBObject();
        query.append("ClassName", new BasicDBObject("$ne", "套餐"));
        FindIterable<Document> findIterable = menuColl.find(query);
        MongoCursor<Document> cursor = findIterable.iterator();
        int menuAmount = (int)menuColl.count();    // 餐點總數

        nameMatrix = new String[menuAmount];     // 拿來比對
        int[] times = new int[menuAmount];    // 記錄出現次數
        Arrays.fill(times, 0);
        int index = 0;
        while (cursor.hasNext()) {      // 遍歷菜單裡所有餐點
            JSONObject jsonObject = new JSONObject(cursor.next().toJson());
            nameMatrix[index] = jsonObject.getString("Name");
            index++;
        }

        query = new BasicDBObject();
        query.put("Status", "已結帳");
        findIterable = orderColl.find(query);
        cursor = findIterable.iterator();
        int orderAmount = 0;
        orderList = new ArrayList<>();

        while (cursor.hasNext()) {     // 遍歷所有已結帳訂單
            JSONObject jsonObject = new JSONObject(cursor.next().toJson());
            JSONArray j = new JSONArray(jsonObject.get("MyMenu").toString());
            String[] mm = new String[j.length()];

            for (int i=0;i<j.length();i++) {    // 遍歷該筆訂單的所有餐點
                String mealName = j.getJSONObject(i).get("Name").toString();
                //System.out.print(mealName + " ");
                index = Arrays.asList(nameMatrix).indexOf(mealName);
                if (index < 0)
                    continue;
                times[index]++;     // 計算該項餐點出現次數
                mm[i] = mealName;   // 該項餐點放進array
            }
            orderList.add(mm);
            orderAmount++;  // 計算訂單總數
        }
        System.out.println("訂單總數 : " + orderAmount);
        mining(times, menuAmount, orderAmount);
    }

    private void mining(int[] times, int menuAmount, int orderAmount) {
        count1ItemProbability(times, menuAmount, orderAmount, false);

        create2ItemArray();
        twoItemTimes = new Integer[twoItem.size()];
        Arrays.fill(twoItemTimes, 0);
        countTimes(twoItem, 2);
        countProbability(orderAmount, 2);

        create3ItemArray(orderAmount);
        threeItemTimes = new Integer[threeItem.size()];
        Arrays.fill(threeItemTimes, 0);
        countTimes(threeItem, 3);
        countProbability(orderAmount, 3);
    }

    private ArrayList<Integer> count1ItemProbability(int[] times, int round, int denominator, boolean test) {
        ArrayList<Integer> al = new ArrayList<>();
        oneItem = new ArrayList<>();
        for (int i = 0; i< round; i++) {
            if (((float)times[i]/denominator) >= 0.13) {
                if (test)   // testing
                    al.add(i);
                else    // mining
                    oneItem.add(nameMatrix[i]);
            }
        }
        return al;
    }

    private void create2ItemArray() {
        twoItem = new ArrayList<>();
        for (int i=0;i<oneItem.size()-1;i++) {
            for (int j = i + 1; j < oneItem.size(); j++) {
                String[] ti = new String[2];
                ti[0] = oneItem.get(i);
                ti[1] = oneItem.get(j);
                twoItem.add(ti);
            }
        }
    }

    private void create3ItemArray(int denominator) {
        threeItem = new ArrayList<>();
        ArrayList<String> hasIt = new ArrayList<>();
        int index1, index2;
        for (int i=0;i<twoItem.size();i++) {    // 找出有在twoItem裡的餐點
            if ((float)twoItemTimes[i]/denominator >= 0.02) {
                String[] tis = twoItem.get(i);
                for (String s:tis) {
                    index1 = Arrays.asList(nameMatrix).indexOf(s);
                    index2 = hasIt.indexOf(s);
                    if (index2 < 0)     // 還沒新增過
                        hasIt.add(nameMatrix[index1]);
                }
            }
        }

        for (int i = 0; i < hasIt.size() - 2; i++) {
            for (int j = i + 1; j < hasIt.size() - 1; j++) {
                for (int k = j + 1; k < hasIt.size(); k++) {
                    String[] ti = new String[3];
                    ti[0] = hasIt.get(i);
                    ti[1] = hasIt.get(j);
                    ti[2] = hasIt.get(k);
                    threeItem.add(ti);
                }
            }
        }
    }

    private void countTimes(ArrayList<String[]> source, int items) {
        for (int i=0;i<source.size();i++) {
            for (String[] mm:orderList) {
                boolean hasIt = true;
                if (mm.length < items)    // 如果訂單餐點數小於組合餐點數就跳過這筆訂單
                    continue;

                String[] ti = source.get(i);    // 要算次數的餐點組合
                for (int j=0;j<items;j++) {
                    int index = Arrays.asList(mm).indexOf(ti[j]);
                    if (index < 0) {    // 這筆訂單找不到第j項餐點
                        hasIt = false;
                        break;
                    }
                }

                if (hasIt) {    // 這筆訂單裡，這個組合的所有餐點都有
                    if (items == 2)
                        twoItemTimes[i]++;
                    else
                        threeItemTimes[i]++;
                }
            }
        }
    }

    private void countProbability(int denominator, int items) {
        ArrayList<String[]> source;
        Integer[] times;
        double prob;

        if (items == 2) {
            source = twoItem;
            times = twoItemTimes;
            prob = 0.02;
        } else {
            source = threeItem;
            times = threeItemTimes;
            prob = 0.005;
        }

        for (int i=0;i<source.size();i++) {
            if ((float)times[i]/denominator >= prob) {
                JSONObject jo = new JSONObject();
                jo.put("ItemNum", items);
                if (items == 2) {
                    jo.put("one", source.get(i)[0]);
                    jo.put("two", source.get(i)[1]);
                } else {
                    jo.put("one", source.get(i)[0]);
                    jo.put("two", source.get(i)[1]);
                    jo.put("three", source.get(i)[2]);
                }
                result.put(jo);
            }
        }
    }
}
