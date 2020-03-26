package order.model.java;

import com.google.gson.JsonObject;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import order.model.javabean.Order;
import order.model.websocket.BossOrderSocket;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static pojo.QkConn.*;
import static pojo.QkConn.dbName;

public class AddOrder {

    public JSONArray add(Order javab) {
        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection("Order");
        DBCollection collectionCustomerID = db.getCollection("CustomerID");
        DBCollection collection1 = db.getCollection("OrderNumber");
        DBCollection collectionPeople = db.getCollection("People");
        MongoCollection collection3 = mongoDatabase.getCollection("Order");
        MongoCollection collectionPeople2 = mongoDatabase.getCollection("People");
        MongoCollection collection2 = mongoDatabase.getCollection("OrderNumber");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0); // number represents number of days

        // set today
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        System.out.println("today's date is: " + today);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, +1); // number represents number of days

        // set tomorrow
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        Date tomorrow = cal2.getTime();

        System.out.println("tomorrow's date is: " + tomorrow);

        Date now = new Date(); //
        System.out.println("now time  is: " + now.toString());
        System.out.println(javab.getType());
        if (javab.getType().equals("外帶")) {//如果是外帶

            BasicDBObject query = new BasicDBObject();
            query.put("date", BasicDBObjectBuilder.start("$gt", today).add("$lt", tomorrow).get());//日期在today-tomorrow之間
            int cursor1 = collection1.find(query).count();  //日期符合的doc 有幾筆 正常來說只有 1筆 或 沒有
            System.out.println("count: " + cursor1);
            System.out.println("query: " + query.toJson());

            if (cursor1 > 0) {//原本資料庫就有
                System.out.println("近有if");
                //  query = new BasicDBObject("date", new BasicDBObject("$regex", javab.getDate()));//先比對日期
                FindIterable<Document> findIterable = collection2.find(query);
                MongoCursor<Document> cursornumber = findIterable.iterator();
                Document Ordernumber = cursornumber.next();//找到日期一樣的那個doc
                int number = (int) Ordernumber.get("number");//get("number")的value -> 會是 外帶客人的數量 int
                number++;//取出來之後要加1

                BasicDBObject newDocument =null;
                if(number>=100){
                    number = 1;

                    newDocument =
                            new BasicDBObject().append("number", 1);//把number 設定1

                }else{

                    newDocument =
                            new BasicDBObject().append("$inc",
                                    new BasicDBObject().append("number", 1));//把number 設定成要+1


                }

                System.out.println("number:" + number);
                javab.setNumber("A" + number);//設定外帶編號

                /////以下要將 OrderNumber 更新為新的number


                collection1.update(query, newDocument);//使用update 將date 符合的 新number


            } else if (cursor1 == 0) { //沒有        下面三行指令 ->就加一個doc 來放
                System.out.println("近沒有if");
                BasicDBObject document = new BasicDBObject();
                document.put("date", now);
                System.out.println("now time  is: " + now.toString());

                document.put("number", 1);
                collection1.insert(document);
                javab.setNumber("A1"); //把number 外帶設成A1
            }

            String newOrderId = javab.getType() + " " + javab.getNumber();
            javab.setOrderID(newOrderId);
        }

        BasicDBObject document = new BasicDBObject();
        document.put("CustomerID", javab.getCustomerID());
        document.put("OrderID", javab.getOrderID());
        document.put("Status", javab.getStatus());
        document.put("Type", javab.getType());
        document.put("Number", javab.getNumber());
        document.put("date", javab.getDate());
        document.put("Note", javab.getNote());
        document.put("TotalPrice", javab.getTotalPrice());
        document.put("People", javab.getPeople());
        List<BasicDBObject> documentDetail = new ArrayList<>();


        for (Order.MyMenuBean s : javab.getMyMenu()) {
            BasicDBObject documentMyMenu = new BasicDBObject();

            documentMyMenu.put("Name", s.getName());
            documentMyMenu.put("Price", s.getPrice());
            documentMyMenu.put("Amount", s.getAmount());
            //    System.out.println("Amount123:"+s.getAmount().toString());
            //    System.out.println("Name123:"+s.getName().toString());
            //    System.out.println("Price123:"+s.getPrice().toString());
            documentDetail.add(documentMyMenu);

            JsonObject element = new JsonObject();

        }


        document.put("MyMenu", documentDetail);
        collection.insert(document);


        BasicDBObject queryC = new BasicDBObject();
        queryC.put("CustomerID", javab.getCustomerID());//搜尋客戶ＩＤ
        int countID = collectionCustomerID.find(queryC).count(); //是否有客戶ＩＤ在資料庫

        if (countID == 0) {//沒有 就新增該客戶ＩＤ
            collectionCustomerID.insert(queryC);
        }


        BasicDBObject query = new BasicDBObject();
        query.put("CustomerID", javab.getCustomerID());
        query.put("OrderID", javab.getOrderID());
        query.put("date", javab.getDate());
        query.put("Note", javab.getNote());

        FindIterable<Document> findIterable1 = collection3.find(query);
        MongoCursor<Document> cursor1 = findIterable1.iterator();


        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        while (cursor1.hasNext()) {
            jsonObject = new JSONObject(cursor1.next().toJson());
            jsonArray.put(jsonObject);
        }


        //  JsonObject jsonObject1 = gson.toJsonTree(javab).getAsJsonObject();

        BasicDBObject queryPeople = new BasicDBObject();
        queryPeople.put("date", BasicDBObjectBuilder.start("$gt", today).add("$lt", tomorrow).get());//日期在today-tomorrow之間
        int cursor2 = collectionPeople.find(queryPeople).count();  //日期符合的doc 有幾筆 正常來說只有 1筆 或 沒有


        if (cursor2 > 0) {//原本資料庫就有

            //  query = new BasicDBObject("date", new BasicDBObject("$regex", javab.getDate()));//先比對日期
            FindIterable<Document> findIterable2 = collectionPeople2.find(queryPeople);
            MongoCursor<Document> cursorPeople = findIterable2.iterator();
            Document Ordernumber = cursorPeople.next();//找到日期一樣的那個doc
            int Peoplenumber = (int) Ordernumber.get("People");//get("number")的value -> 會是 外帶客人的數量 int
            Peoplenumber += javab.getPeople();//取出來之後要加人數
            System.out.println("number:" + Peoplenumber);


            /////以下要將 OrderNumber 更新為新的number

            BasicDBObject newDocument =
                    new BasicDBObject().append("$inc",
                            new BasicDBObject().append("People", javab.getPeople()));//把People 設定加上現在的人數
            collectionPeople.update(queryPeople, newDocument);//使用update 將date 符合的 People 加上現在的人數
        } else if (cursor2 == 0) { //沒有        下面指令 ->就加一個doc 來放
            BasicDBObject documentPeople = new BasicDBObject();
            documentPeople.put("date", now);
            System.out.println("now time  is: " + now.toString());
            documentPeople.put("People", javab.getPeople());
            collectionPeople.insert(documentPeople);

        }

        return jsonArray;
    }


}
