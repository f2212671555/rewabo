package nicp.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tool.HttpCommonAction;

public class NicpWebhook {
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("date")
    @Expose
    private String date;

    public NicpWebhook() {
    }

    public NicpWebhook(String roleName, String senderId, Message message, String date) {
        super();
        this.roleName = roleName;
        this.senderId = senderId;
        this.message = message;
        this.date = date;
    }

    public NicpWebhook(String roleName, String senderId, Message message) {
        this.roleName = roleName;
        this.senderId = senderId;
        this.message = message;
        this.date = HttpCommonAction.getRewaboDate();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("roleName", roleName)
                .append("senderId", senderId)
                .append("message", message)
                .append("date", date).toString();
    }
}
