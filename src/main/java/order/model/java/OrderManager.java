package order.model.java;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import order.model.javabean.Order;
import order.model.javabean.OrderContent;
import order.model.websocket.BossOrderSocket;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.eq;

public class OrderManager {

    private MongoDatabase mongoDatabase;
    private DB db;
    private ArrayList<OrderContent> orderContent ;
    private ArrayList<Order> order ;

    public OrderManager(MongoDatabase mongoDatabase, DB db){
        this.mongoDatabase = mongoDatabase;
        this.db = db;
    }

    public void add(Order javab) {

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
                System.out.println("number:" + number);
                javab.setNumber("A" + number);//設定外帶編號

                /////以下要將 OrderNumber 更新為新的number

                BasicDBObject newDocument =
                        new BasicDBObject().append("$inc",
                                new BasicDBObject().append("number", 1));//把number 設定成要+1

                collection1.update(query, newDocument);//使用update 將date 符合的 number+1


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




        JSONArray webSocketjsonArray =  getoneOrder(javab);

        try {
            sendBossOdrer(webSocketjsonArray);
        } catch (java.io.IOException e) {

        }
    }

    public void delete(OrderContent javab){


        DBCollection collection  = db.getCollection("Order");
        System.out.println("_id:"+javab.getOid());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId(javab.getOid()));
        System.out.println(document.toJson());
        collection.remove(document);
    }

    public void SetStatus(OrderContent javab){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0); // number represents number of days

        // set today
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        System.out.println("today's date is: " + today);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE,+1); // number represents number of days

        // set tomorrow
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        Date tomorrow = cal2.getTime();


        DBCollection collection  = db.getCollection("Order");
        DBCollection collectionPeople  = db.getCollection("People");
        System.out.println("_id:"+javab.getOid());
        System.out.println("Status:"+javab.getStatus());
        BasicDBObject MyNewOrderStatus = new BasicDBObject().append("$set", new BasicDBObject().append("Status", javab.getStatus()));
        System.out.println(MyNewOrderStatus.toJson());
        collection.update(new BasicDBObject().append("_id", new ObjectId(javab.getOid())), MyNewOrderStatus);
        ////OrderID 要是相符合 就會把該筆訂單改成 老闆端傳來的MyStatus的狀態

        BasicDBObject queryPeople = new BasicDBObject();
        queryPeople.put("date", BasicDBObjectBuilder.start("$gt", today).add("$lt", tomorrow).get());//日期在today-tomorrow之間

        if(javab.getStatus().equals("已結帳")){
            BasicDBObject newDocument =
                    new BasicDBObject().append("$inc",
                            new BasicDBObject().append("People", (-1)*javab.getPeople()));//把People 設定加上現在的人數
            collectionPeople.update(queryPeople, newDocument);//使用update 將date 符合的 People 加上現在的人數
        }
    }

    public JSONArray getOrder(String MyStatus) {

        //BasicDBObject fields = new BasicDBObject().append( 1); // SELECT name
        BasicDBObject query = new BasicDBObject();
        if(MyStatus.equals("已完成")|MyStatus.equals("已結帳")|MyStatus.equals("待製作")|MyStatus.equals("製作中")) {
            query.put("Status", new BasicDBObject("$regex", MyStatus));
        }else {
            query.put("CustomerID", MyStatus);
            query.put("Status", new BasicDBObject("$ne", "已結帳"));
            System.out.println(query.toJson());
            System.out.println(MyStatus);
        }
        MongoCollection  collection = mongoDatabase.getCollection("Order");

        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> cursor1 = findIterable.iterator();

        JSONArray jsonArray = new JSONArray();

        while (cursor1.hasNext()) {
            JSONObject jsonObject = new JSONObject(cursor1.next().toJson());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public JSONArray getoneOrder(Order javab) {

        //BasicDBObject fields = new BasicDBObject().append( 1); // SELECT name
        BasicDBObject query = new BasicDBObject();
        query.put("Type",javab.getType());
        query.put("OrderID",javab.getOrderID());
        query.put("CustomerID",javab.getCustomerID());
        query.put("Number",javab.getNumber());

        MongoCollection  collection = mongoDatabase.getCollection("Order");

        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> cursor1 = findIterable.iterator();

        JSONArray jsonArray = new JSONArray();

        while (cursor1.hasNext()) {
            JSONObject jsonObject = new JSONObject(cursor1.next().toJson());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


    public JSONArray getOrderByoid(String oid) {

        JSONArray jsonArray = new JSONArray();
        MongoCollection  collection = mongoDatabase.getCollection("Order");


        BasicDBObject match =  new BasicDBObject("Status", new BasicDBObject("$ne", "已結帳")).append("_id",new ObjectId(oid)) ;

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                Aggregates.match(match),
                Aggregates.project(new BsonDocument()
                        .append("_id", BsonBoolean.TRUE)
                        .append("Status", BsonBoolean.TRUE)
                        .append("date", BsonBoolean.TRUE)

                )));

        //  db.Order.aggregate([
//                {"$match":
//                   {
//                       "Status":{"$ne":"已結帳"},
//                       "CustomerID":"ID"
//                   }
//                 },
//                {"$project":{
//                          "_id":1,
//                          "date":1,
//                          "Status":1
//                }}
//    ])


        MongoCursor<Document> cursor = output.iterator();


        while (cursor.hasNext()) {

            JSONObject jsonObect = new JSONObject(cursor.next().toJson());
            jsonArray.put(jsonObect);
            //System.out.println(cursor.next().toJson(writerSettings));
        }



        return  jsonArray;
    }



    public ArrayList<Order> getOrderByDate(String CustomerID, String FromDate , String ToDate ) {

        // DBCollection collection  = db.getCollection("Order");
        MongoCollection<Document> collection = mongoDatabase.getCollection("Order");
        List<Bson> match = new ArrayList<>();
        Bson CustomerIdMatch = new BasicDBObject();
        Bson StatusIdMatch = new BasicDBObject();
        Bson dateMatch = new BasicDBObject();
        CustomerIdMatch = eq("CustomerID",CustomerID);
        StatusIdMatch = eq("Status",new BasicDBObject("$eq","已結帳"));
        dateMatch = eq("date",new BasicDBObject("$gt",FromDate).append("$lt",ToDate));
        match.add(CustomerIdMatch);
        match.add(StatusIdMatch);
        match.add(dateMatch);
        BasicDBObject order_id = new BasicDBObject();
        order_id.put("_id","$_id");
        order_id.put("OrderID","$OrderID");
        order_id.put("CustomerID","$CustomerID");
        order_id.put("Status","$Status");
        order_id.put("Type","$Type");
        order_id.put("Number","$Number");
        order_id.put("date","$date");
        order_id.put("Note","$Note");
        order_id.put("People","$People");
        order_id.put("TotalPrice","$TotalPrice");
        order_id.put("MyMenu","$MyMenu");
        BasicDBObject group = new BasicDBObject(order_id);

        //	FindIterable<Document> findIterable = collection.find();
        //	MongoCursor<Document> mongoCursor = findIterable.iterator();
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(match)),
                Aggregates.lookup("Menu", "MyMenu.Name", "Name", "Info"),
                Aggregates.unwind("$Info"),
                Aggregates.group(order_id,push("MyUrl","$Info"))
        ));
        MongoCursor<Document> cursor = output.iterator();
        order =new ArrayList<>();

        Order tmp ;
        Gson gson = new Gson();
        while (cursor.hasNext()) {

            Document document = cursor.next();

            JsonParser parser = new JsonParser();
            JsonElement mJson =  parser.parse(document.toJson());




            tmp = gson.fromJson(mJson.getAsJsonObject().get("_id"), Order.class);
            order.add(tmp);
        }



        return order;
    }

    public ArrayList<Order> getCustomerOrder(String CustomerID,String Status) {


        JSONArray jsonArray = new JSONArray();
        String iseqornot = "$ne";
        if(Status.equals("已結帳"))
            iseqornot = "$eq";

        try {

            MongoCollection<Document> collection = mongoDatabase.getCollection("Order");
            List<Bson> match = new ArrayList<>();
            Bson CustomerIdMatch = new BasicDBObject();
            Bson StatusIdMatch = new BasicDBObject();
            CustomerIdMatch = eq("CustomerID",CustomerID);
            StatusIdMatch = eq("Status",new BasicDBObject(iseqornot,"已結帳"));
            match.add(CustomerIdMatch);
            match.add(StatusIdMatch);
            BasicDBObject order_id = new BasicDBObject();
            order_id.put("_id","$_id");
            order_id.put("OrderID","$OrderID");
            order_id.put("CustomerID","$CustomerID");
            order_id.put("Status","$Status");
            order_id.put("Type","$Type");
            order_id.put("Number","$Number");
            order_id.put("People","$People");
            order_id.put("date","$date");
            order_id.put("Note","$Note");
            order_id.put("TotalPrice","$TotalPrice");
            order_id.put("MyMenu","$MyMenu");
            BasicDBObject group = new BasicDBObject(order_id);

            //	FindIterable<Document> findIterable = collection.find();
            //	MongoCursor<Document> mongoCursor = findIterable.iterator();
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    Aggregates.match(Filters.and(match)),
                    Aggregates.lookup("Menu", "MyMenu.Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id,push("Myurl","$Info"))
            ));
            System.out.println(Arrays.asList(
                    Aggregates.match(Filters.and(match)),
                    Aggregates.lookup("Menu", "MyMenu.Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id,push("MyUrl","$Info"))
            ).toString());
            System.out.println(group.toJson());

//            db.Order.aggregate([
//                    {$match:{"CustomerID":"ID123",
//                    "Status":{$ne:"製作中"}
//            }
//},
//            {$lookup:{
//                from:"ManageMenu",
//                        localField:"MyMenu.Name",
//                        foreignField:"Name",
//                        as:"info"
//            }
//            },
//            {$unwind : '$info'}
//,
//            {$group:{
//                "_id":{
//                    "_id":"$_id",
//                            "CustomerID":"$CustomerID",
//                            "OrderID":"$OrderID",
//                            "Status":"$Status",
//                            "Type":"$Type",
//                            "Number":"$Number",
//                            "date":"$date",
//                            "Note":"$Note",
//                            "TotalPrice":"$TotalPrice"
//                },
//                "MyMenu":{"$push":"$info"}
//            }
//            }
//])
            MongoCursor<Document> cursor = output.iterator();
            JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);
//			FindIterable<Document> findIterable = collection.find();
//
//			MongoCursor<Document> cursor1 = findIterable.iterator();
//
//
//			while (cursor1.hasNext()) {
//				JSONObject jsonObect = new JSONObject(cursor1.next().toJson());
//				jsonArray.put(jsonObect);
//
//				// System.out.println(cursor1.next().toJson(writerSettings));
//			}

            order =new ArrayList<>();

            Order tmp ;
            Gson gson = new Gson();
            while (cursor.hasNext()) {

                Document document = cursor.next();


                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(document.toJson());




                tmp = gson.fromJson(mJson.getAsJsonObject().get("_id"), Order.class);

                order.add(tmp);
            }





        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return order;

    }

    public Order getCustomerOrderByID(String oid,String Status) {


        JSONArray jsonArray = new JSONArray();
        String iseqornot = "$ne";
        Order tmp =null;

        if(Status.equals("已結帳")){
            iseqornot = "$eq";

        }


        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("Order");
            List<Bson> match = new ArrayList<>();
            Bson CustomerIdMatch = new BasicDBObject();
            Bson StatusIdMatch = new BasicDBObject();
            CustomerIdMatch = eq("_id",new ObjectId(oid));
            StatusIdMatch = eq("Status",new BasicDBObject(iseqornot,"已結帳"));
            match.add(CustomerIdMatch);
            match.add(StatusIdMatch);
            BasicDBObject order_id = new BasicDBObject();
            order_id.put("_id","$_id");
            order_id.put("OrderID","$OrderID");
            order_id.put("CustomerID","$CustomerID");
            order_id.put("Status","$Status");
            order_id.put("Type","$Type");
            order_id.put("Number","$Number");
            order_id.put("People","$People");
            order_id.put("date","$date");
            order_id.put("Note","$Note");
            order_id.put("TotalPrice","$TotalPrice");
            order_id.put("MyMenu","$MyMenu");
            BasicDBObject group = new BasicDBObject(order_id);

            //	FindIterable<Document> findIterable = collection.find();
            //	MongoCursor<Document> mongoCursor = findIterable.iterator();
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    Aggregates.match(Filters.and(match)),
                    Aggregates.lookup("Menu", "MyMenu.Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id,push("Myurl","$Info"))
            ));
            System.out.println(Arrays.asList(
                    Aggregates.match(Filters.and(match)),
                    Aggregates.lookup("Menu", "MyMenu.Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id,push("MyUrl","$Info"))
            ).toString());
            System.out.println(group.toJson());

//            db.Order.aggregate([
//                    {$match:{"CustomerID":"ID123",
//                    "Status":{$ne:"製作中"}
//            }
//},
//            {$lookup:{
//                from:"ManageMenu",
//                        localField:"MyMenu.Name",
//                        foreignField:"Name",
//                        as:"info"
//            }
//            },
//            {$unwind : '$info'}
//,
//            {$group:{
//                "_id":{
//                    "_id":"$_id",
//                            "CustomerID":"$CustomerID",
//                            "OrderID":"$OrderID",
//                            "Status":"$Status",
//                            "Type":"$Type",
//                            "Number":"$Number",
//                            "date":"$date",
//                            "Note":"$Note",
//                            "TotalPrice":"$TotalPrice"
//                },
//                "MyMenu":{"$push":"$info"}
//            }
//            }
//])
            MongoCursor<Document> cursor = output.iterator();
            JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);



            Gson gson = new Gson();

            while (cursor.hasNext()) {
                Document document = cursor.next();

                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(document.toJson());




                tmp = gson.fromJson(mJson.getAsJsonObject().get("_id"), Order.class);

            }



            return tmp;


        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return tmp;
    }











    public ArrayList<OrderContent> getCustomerStatus(String CustomerID) {



        MongoCollection  collection = mongoDatabase.getCollection("Order");

        orderContent =new ArrayList<>();

        OrderContent tmp ;

        BasicDBObject match =  new BasicDBObject("Status", new BasicDBObject("$ne", "已結帳")).append("CustomerID",CustomerID) ;
        Gson gson = new Gson();
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                Aggregates.match(match),
                Aggregates.project(new BsonDocument()
                        .append("_id", BsonBoolean.TRUE)
                        .append("Status", BsonBoolean.TRUE)
                        .append("date", BsonBoolean.TRUE)

                )));
        System.out.println(match.toJson());
//  db.Order.aggregate([
//                {"$match":
//                   {
//                       "Status":{"$ne":"已結帳"},
//                       "CustomerID":"ID"
//                   }
//                 },
//                {"$project":{
//                          "_id":1,
//                          "date":1,
//                          "Status":1
//                }}
//    ])



        MongoCursor<Document> cursor = output.iterator();


        while (cursor.hasNext()) {

            Document document = cursor.next();
            JsonParser parser = new JsonParser();
            JsonElement mJson =  parser.parse(document.toJson());




            tmp = gson.fromJson(mJson.getAsJsonObject().get("_id"), OrderContent.class);
            orderContent.add(tmp);
        }

        return orderContent;
    }

    public ArrayList<OrderContent> getCustomerHisOrder(String CustomerID) {

        MongoCollection  collection = mongoDatabase.getCollection("Order");

        orderContent =new ArrayList<>();

        OrderContent tmp ;

        BasicDBObject query = new BasicDBObject();
        query.put("CustomerID", CustomerID);
        query.put("Status", "已結帳");

        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> cursor1 = findIterable.iterator();

        // JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        while (cursor1.hasNext()) {
//            JSONObject jsonObject = new JSONObject(cursor1.next().toJson());
//            jsonArray.put(jsonObject);

            Document document = cursor1.next();

            JsonParser parser = new JsonParser();
            JsonElement mJson =  parser.parse(document.toJson());




            tmp = gson.fromJson(mJson.getAsJsonObject().get("_id"), OrderContent.class);
            orderContent.add(tmp);
        }



        return  orderContent;
    }








    private void sendBossOdrer(JSONArray jsonObject) throws IOException {
        BossOrderSocket.bossOrderWsSession.getBasicRemote().sendText(jsonObject.toString());

    }


}
