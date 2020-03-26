package notification.javabean;
//刪除用的
public class PushInfo {
    /**
     * date : 2018-04-20
     * PushInfo : Is it good to drink?
     * Name : 漢堡
     * Price : 50
     */

    private String date;
    private String PushInfo;
    private String Name;
    private String Price;
    private String oid;

    public String getoid() {
        return oid;
    }

    public void setoid(String oid) {
        this.oid = oid;
    }

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
}
