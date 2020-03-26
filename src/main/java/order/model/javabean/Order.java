package order.model.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Order {
    /**
     *
     * CustomerID : ID123TEST1
     * OrderID : T123123T
     * Status : done
     * Type : 內用
     * Number : 7
     * date : 2019-03-22T07:44:20.178Z
     * Note :
     * People: 0
     * TotalPrice : 110
     * _id : {"$oid":"5c8dbc4c2a8ccf7694abc0fd"}
     * MyMenu : [{"Name":"火腿蛋餅","Price":"30","Amount":"2","Url":"https://ai-rest.cse.ntou.edu.tw/卡啦雞腿堡.jpg"},{"Name":"咔啦雞腿堡","Price":"55","Amount":"2","Url":"https://ai-rest.cse.ntou.edu.tw/卡啦雞腿堡.jpg"}]
     */


    private String CustomerID;
    private String OrderID;
    private String Status;
    private String Type;
    private String Number;
    private String date;
    private String Note;
    private String TotalPrice;
    private int People;
    @SerializedName("_id")
    private IdBean id;
    private List<MyMenuBean> MyMenu;

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerId) {
        this.CustomerID = CustomerId;
    }

    public int getPeople() {
        return People;
    }

    public void setPeople(int People) {
        this.People = People;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String OrderId) {
        this.OrderID = OrderId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public List<MyMenuBean> getMyMenu() {
        return MyMenu;
    }

    public void setMyMenu(List<MyMenuBean> MyMenu) {
        this.MyMenu = MyMenu;
    }

    public IdBean getId() {
        return id;
    }

    public void setId(IdBean id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("CustomerID", CustomerID)
            .append("OrderID", OrderID)
            .append("Status", Status)
            .append("Type", Type)
            .append("Number", Number)
            .append("date", date)
            .append("Note", Note)
            .append("TotalPrice", TotalPrice)
            .append("People", People)
            .append("id", id)
            .append("MyMenu", MyMenu)
            .toString();
    }

    public static class IdBean {
        @SerializedName("$oid")
        private String oid;
        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("oid", oid)
                .toString();
        }
    }

    public static class MyMenuBean {
        /**
         * Name : 火腿蛋餅
         * Price : 30
         * Amount : 2
         * Url : https://ai-rest.cse.ntou.edu.tw/卡啦雞腿堡.jpg
         */

        private String Name;
        private String Price;
        private String Amount;
        private String Url;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String Amount) {
            this.Amount = Amount;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("Name", Name)
                .append("Price", Price)
                .append("Amount", Amount)
                .append("Url", Url)
                .toString();
        }
    }
}