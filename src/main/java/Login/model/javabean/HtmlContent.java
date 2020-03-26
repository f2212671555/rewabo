package Login.model.javabean;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HtmlContent {

  private String html;

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("html", html)
        .toString();
  }
}
