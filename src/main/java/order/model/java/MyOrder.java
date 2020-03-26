package order.model.java;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import order.model.javabean.Order;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.eq;
import static pojo.QkConn.*;

public class MyOrder {

	static JSONArray order;
	MongoClient mongoClient;
	MongoDatabase mongoDatabase;
	MongoCollection<Document> collection;


	public MyOrder(){
		connectDb();
	}

	public void connectDb() {


		try {
			mongoClient = new MongoClient(dbHost, mongoDBPort);
			mongoDatabase = mongoClient.getDatabase(dbName);
			collection = mongoDatabase.getCollection("Order");




		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
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

	public List<Order> getCustomerHisOrderJavabean(String CustomerID) {
		List<Order> orderList = new ArrayList<>();
		BasicDBObject query = new BasicDBObject();
		query.put("CustomerID", CustomerID);
		query.put("Status", "已結帳");
		Gson gson = new Gson();
		FindIterable<Document> findIterable = collection.find(query);
		MongoCursor<Document> cursor = findIterable.iterator();

		while (cursor.hasNext()) {
			Document document = cursor.next();
			Order order = gson.fromJson(document.toJson(),Order.class);
			orderList.add(order);
		}

		return  orderList;
	}

	public JSONArray getCustomerHisOrder(String CustomerID) {




		BasicDBObject query = new BasicDBObject();
		query.put("CustomerID", CustomerID);
		query.put("Status", "已結帳");

		FindIterable<Document> findIterable = collection.find(query);
		MongoCursor<Document> cursor1 = findIterable.iterator();

		JSONArray jsonArray = new JSONArray();

		while (cursor1.hasNext()) {
			JSONObject jsonObject = new JSONObject(cursor1.next().toJson());
			jsonArray.put(jsonObject);
		}



		return  jsonArray;
	}


	public JSONArray getCustomerStatus(String CustomerID) {




		JSONArray jsonArray = new JSONArray();


		BasicDBObject match =  new BasicDBObject("Status", new BasicDBObject("$ne", "已結帳")).append("CustomerID",CustomerID) ;

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

			JSONObject jsonObect = new JSONObject(cursor.next().toJson());
			jsonArray.put(jsonObect);
			//System.out.println(cursor.next().toJson(writerSettings));
		}

		return jsonArray;
	}

	public JSONArray getOrderByDate(String CustomerID, String FromDate , String ToDate ) {




		MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
		DB db = mongoClient.getDB(dbName);
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
		JSONArray jsonArray = new JSONArray();
		while (cursor.hasNext()) {

			JSONObject jsonObect = new JSONObject(cursor.next().toJson());
			jsonArray.put(jsonObect);
			//System.out.println(cursor.next().toJson(writerSettings));
		}



		return jsonArray;
	}



	public JSONArray getCustomerOrderByID(String oid,String Status) {


		JSONArray jsonArray = new JSONArray();
		String iseqornot = "$ne";


		if(Status.equals("已結帳")){
			iseqornot = "$eq";

		}


		try {
			MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);

			MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

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


			while (cursor.hasNext()) {

				JSONObject jsonObect = new JSONObject(cursor.next().toJson());
				jsonArray.put(jsonObect);
				//System.out.println(cursor.next().toJson(writerSettings));
			}


		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return jsonArray;

	}

	public JSONArray getCustomerOrder(String CustomerID,String Status) {


		JSONArray jsonArray = new JSONArray();
		String iseqornot = "$ne";
		if(Status.equals("已結帳"))
			iseqornot = "$eq";

		try {
			MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);

			// connect DB
			MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

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

			while (cursor.hasNext()) {

				JSONObject jsonObect = new JSONObject(cursor.next().toJson());
				jsonArray.put(jsonObect);
				//System.out.println(cursor.next().toJson(writerSettings));
			}


		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return jsonArray;

	}
}
