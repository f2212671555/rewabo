package order.model.javabean;

public class ResponseStatus {

    /**
     * Status : 200
     * date : 2019-04-01
     * key  : 特殊使用
     */

    private int Status;
    private String date;
    private boolean key;
    public boolean getkey() {
        return key;
    }

    public void setkey(boolean key) {
        this.key = key;
    }



    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
