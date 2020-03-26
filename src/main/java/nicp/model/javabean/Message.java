package nicp.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Message {
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * No args constructor for use in serialization
     */
    public Message() {
    }

    /**
     * @param text
     */
    public Message(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("text", text).toString();
    }
}
