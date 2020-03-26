package notification.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferInfoJavabean {
    @Override
    public String toString() {
        return "OfferInfoJavabean{" +
                "id=" + id +
                ", MyMenu=" + MyMenu +
                '}';
    }

    /**
     * MyMenu : [{"Item":["起司牛肉堡","紅茶","薯條"],"Price":"99","ClassName":"套餐","_id":{"$oid":"5cc04522a5ed5405cbef5590"},"url":"https://ai-rest.cse.ntou.edu.tw/牛肉漢堡薯條套餐.jpg","Name":"牛肉漢堡薯條套餐"}]
     * _id : {"date":"2019-04-27 0:56:53","PushInfo":"牛肉堡特價99元！要買要快！","_id":{"$oid":"5cc33855a5ed5404df027872"}}
     */

    @SerializedName("_id")
    private IdBeanX id;
    private List<MyMenuBean> MyMenu;

    public IdBeanX getId() {
        return id;
    }

    public void setId(IdBeanX id) {
        this.id = id;
    }

    public List<MyMenuBean> getMyMenu() {
        return MyMenu;
    }

    public void setMyMenu(List<MyMenuBean> MyMenu) {
        this.MyMenu = MyMenu;
    }

    public static class IdBeanX {
        @Override
        public String toString() {
            return "IdBeanX{" +
                    "date='" + date + '\'' +
                    ", PushInfo='" + PushInfo + '\'' +
                    ", id=" + id +
                    '}';
        }

        /**
         * date : 2019-04-27 0:56:53
         * PushInfo : 牛肉堡特價99元！要買要快！
         * _id : {"$oid":"5cc33855a5ed5404df027872"}
         */

        private String date;
        private String PushInfo;
        @SerializedName("_id")
        private IdBean id;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPushInfo() {
            return PushInfo;
        }

        public void setPushInfo(String PushInfo) {
            this.PushInfo = PushInfo;
        }

        public IdBean getId() {
            return id;
        }

        public void setId(IdBean id) {
            this.id = id;
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
        }
    }

    public static class MyMenuBean {
        @Override
        public String toString() {
            return "MyMenuBean{" +
                    "price='" + price + '\'' +
                    ", className='" + className + '\'' +
                    ", id=" + id +
                    ", url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    ", Item=" + Item +
                    '}';
        }

        /**
         * Item : ["起司牛肉堡","紅茶","薯條"]
         * Price : 99
         * ClassName : 套餐
         * _id : {"$oid":"5cc04522a5ed5405cbef5590"}
         * url : https://ai-rest.cse.ntou.edu.tw/牛肉漢堡薯條套餐.jpg
         * Name : 牛肉漢堡薯條套餐
         */

        @SerializedName("Price")
        private String price;
        @SerializedName("ClassName")
        private String className;
        @SerializedName("_id")
        private IdBeanXX id;
        private String url;
        @SerializedName("Name")
        private String name;
        private List<String> Item;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public IdBeanXX getId() {
            return id;
        }

        public void setId(IdBeanXX id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getItem() {
            return Item;
        }

        public void setItem(List<String> Item) {
            this.Item = Item;
        }

        public static class IdBeanXX {
            @Override
            public String toString() {
                return "IdBeanXX{" +
                        "oid='" + oid + '\'' +
                        '}';
            }
            @SerializedName("$oid")
            private String oid;
            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }
        }
    }
}
