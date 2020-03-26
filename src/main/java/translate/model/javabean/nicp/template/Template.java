package translate.model.javabean.nicp.template;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import translate.model.javabean.nicp.result.Result;

public class Template {

  private Result result;
  private String message;
  private int status;
  @SerializedName("userID")
  private List<String> userIds;
  private ConfigBean config;
  private String originalPlatform;

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public ConfigBean getConfig() {
    return config;
  }

  public void setConfig(ConfigBean config) {
    this.config = config;
  }

  public String getOriginalPlatform() {
    return originalPlatform;
  }

  public void setOriginalPlatform(String originalPlatform) {
    this.originalPlatform = originalPlatform;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("result", result)
        .append("message", message)
        .append("status", status)
        .append("userIds", userIds)
        .append("config", config)
        .append("originalPlatform", originalPlatform)
        .toString();
  }

  public static class ConfigBean {
  }
}
