package Login.model.javabean;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class LoginResponse {

  private boolean success;
  private HtmlContent htmlContent;

  public HtmlContent getHtmlContent() {
    return htmlContent;
  }

  public void setHtmlContent(HtmlContent htmlContent) {
    this.htmlContent = htmlContent;
  }

  public boolean getResult() {
    return success;
  }

  public void setResult(boolean result) {
    this.success = result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("success", success)
        .append("htmlContent", htmlContent)
        .toString();
  }
}
