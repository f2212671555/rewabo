package nicp.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tool.HttpCommonAction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessagesResponse {

    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("date")
    @Expose
    private String date;

    public MessagesResponse() {
    }

    /**
     * @param statusCode
     * @param date
     */


    public MessagesResponse(String statusCode, String date) {
        super();
        this.statusCode = statusCode;
        this.date = date;
    }

    public MessagesResponse(String statusCode) {
        this.statusCode = statusCode;
        this.date = HttpCommonAction.getRewaboDate();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusCode", statusCode).append("date", date).toString();
    }
}
