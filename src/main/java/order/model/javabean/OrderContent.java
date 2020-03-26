package order.model.javabean;


import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrderContent {

    /**
     * CustomerID : 1234
     * oid : 213456
     * FromDate : 2019-1-1
     * ToDate : 2019-1-12
     * Status : 製作中
     * People : 1
     */

    private String CustomerID;
    private String oid;
    private String FromDate;
    private String ToDate;
    private String Status;
    private int People;

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }


    public int getPeople() {
        return People;
    }

    public void setPeople(int People) {
        this.People = People;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("CustomerID", CustomerID)
            .append("oid", oid)
            .append("FromDate", FromDate)
            .append("ToDate", ToDate)
            .append("Status", Status)
            .append("People", People)
            .toString();
    }
}
