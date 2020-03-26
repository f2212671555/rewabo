package nicp.model.javabean;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MessageRecord {
    /**
     * recipientId : 3858417550861136
     * chatbot : 老闆，26桌的顧客要開電扇，你要讓他開嗎？
     * boss : 不要！！
     * date : 2019-03-22 20:35:20
     * originalSenderId : 1254459154682919
     */

    private String recipientId;
    private String chatbot;
    private String boss;
    private String date;
    private String originalSenderId;

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getChatbot() {
        return chatbot;
    }

    public void setChatbot(String chatbot) {
        this.chatbot = chatbot;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOriginalSenderId() {
        return originalSenderId;
    }

    public void setOriginalSenderId(String originalSenderId) {
        this.originalSenderId = originalSenderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("recipientId", recipientId)
            .append("chatbot", chatbot)
            .append("boss", boss)
            .append("date", date)
            .append("originalSenderId", originalSenderId)
            .toString();
    }
}
