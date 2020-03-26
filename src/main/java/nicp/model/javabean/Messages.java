package nicp.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Messages {
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("recipientId")
    @Expose
    private String recipientId;
    @SerializedName("originalSenderId")
    @Expose
    private String originalSenderId;
    @SerializedName("message")
    @Expose
    private Message message;

    public Messages() {
    }

    /**
     * @param message
     * @param recipientId
     * @param roleName
     */
    public Messages(String roleName, String recipientId, String originalSenderId, Message message) {
        super();
        this.roleName = roleName;
        this.recipientId = recipientId;
        this.originalSenderId = originalSenderId;
        this.message = message;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getOriginalSenderId() {
        return originalSenderId;
    }

    public void setOriginalSenderId(String originalSenderId) {
        this.originalSenderId = originalSenderId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("roleName", roleName)
                .append("recipientId", recipientId)
                .append("originalSenderId", originalSenderId)
                .append("message", message).toString();
    }
}
