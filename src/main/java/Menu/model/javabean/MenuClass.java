package Menu.model.javabean;

import com.google.gson.annotations.SerializedName;

public class MenuClass {
    /**
     * Price : 45
     * ClassName : 蛋餅
     * _id : {"$oid":"5c8dbc4c2a8ccf7694abc0fd"}
     * url : https://ai-rest.cse.ntou.edu.tw/蛋餅.jpg
     * Name : 火腿蛋餅
     */

    @SerializedName("ClassName")
    private String className;
    @SerializedName("_id")
    private IdBean id;
    private String url;
    @SerializedName("Price")
    private String price;
    @SerializedName("Name")
    private String name;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public IdBean getId() {
        return id;
    }

    public void setId(IdBean id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
