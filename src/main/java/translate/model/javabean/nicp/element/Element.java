package translate.model.javabean.nicp.element;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import translate.model.javabean.nicp.button.Button;

public class Element {
  public String title;
  public String subtitle;
  public String imageUrl;
  public List<Button> buttons;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<Button> getButtons() {
    return buttons;
  }

  public void setButtons(List<Button> buttons) {
    this.buttons = buttons;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("title", title)
        .append("subtitle", subtitle)
        .append("imageUrl", imageUrl)
        .append("buttons", buttons)
        .toString();
  }
}
