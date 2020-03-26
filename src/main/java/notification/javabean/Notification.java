package notification.javabean;

public class Notification {


    /**
     * Name : 套餐名稱
     * Price : 價錢
     * PushInfo : 推送名稱
     * Time : 多久推一次// 給一個數字x  x =  1 2 3 4 5  x代表幾天
     * date : 2019-05-22
     * oid  : 推播id //動作是刪除再給我
     * action : 動作
     * type: 定期推播
     */

    private String Name;
    private String Price;
    private String PushInfo;
    private int Time;
    private String date;
    private String oid;
    private String Action;
    private String type;

    public void settype(String type) {
        this.type = type;
    }

    public String gettype() {
        return type;
    }


    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }


    public String getoid() {
        return oid;
    }

    public void setoid(String oid) {
        this.oid = oid;
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

    public String getPushInfo() {
        return PushInfo;
    }

    public void setPushInfo(String PushInfo) {
        this.PushInfo = PushInfo;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int Time) {
        this.Time = Time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
