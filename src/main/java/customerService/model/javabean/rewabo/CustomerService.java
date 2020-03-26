package customerService.model.javabean.rewabo;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CustomerService {

  /**
   * label : 餐廳資訊 value : 餐廳資訊 intentId : 5dc2ad46-d167-43b8-8be2-e9d872d3a2d7
   */

  private String label;
  @SerializedName("Intent_Id")
  private String intentId;
  private String value;

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getIntentId() {
    return intentId;
  }

  public void setIntentId(String intentId) {
    this.intentId = intentId;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("label", label)
        .append("intentId", intentId)
        .append("value", value)
        .toString();
  }
}
