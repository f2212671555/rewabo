package Menu.model.javabean;

import java.util.List;

public class Set {
    /**
     * ClassName : 套餐
     * Name : 好健康雙人分享餐
     * Item : ["原味蛋餅","雞塊","牛奶"]
     * Price : 165
     * url : https://ai-rest.cse.ntou.edu.tw/起司牛肉堡.jpg
     * _id : {"$oid":"541333629520f6e05b0cb410"}
     * SoldOut : true
     */

    private String ClassName;
    private String Name;
    private String Price;
    private String url;
    private List<String> Item;
    private IdBean _id;
    private boolean SoldOut;

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getItem() {
        return Item;
    }

    public void setItem(List<String> Item) {
        this.Item = Item;
    }

    public IdBean get_id() {
        return _id;
    }

    public void set_id(IdBean _id) {
        this._id = _id;
    }

    public boolean isSoldOut() {
        return SoldOut;
    }

    public void setSoldOut(boolean SoldOut) {
        this.SoldOut = SoldOut;
    }

    public static class IdBean {
        /**
         * $oid : 541333629520f6e05b0cb410
         */

        private String $oid;

        public String get$oid() {
            return $oid;
        }

        public void set$oid(String $oid) {
            this.$oid = $oid;
        }
    }
}
