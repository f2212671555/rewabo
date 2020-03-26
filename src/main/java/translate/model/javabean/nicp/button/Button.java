package translate.model.javabean.nicp.button;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Button {
  public String type;
  public String label;
  public String value;
  public String answer;
  public boolean alert;
  public String style;
  public String url;
  public String webViewHeightRatio;
  public boolean extensions;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public boolean getAlert() {
    return alert;
  }

  public void setAlert(boolean alert) {
    this.alert = alert;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getWebViewHeightRatio() {
    return webViewHeightRatio;
  }

  public void setWebViewHeightRatio(String webViewHeightRatio) {
    this.webViewHeightRatio = webViewHeightRatio;
  }

  public boolean getExtensions() {
    return extensions;
  }

  public void setExtensions(boolean extensions) {
    this.extensions = extensions;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("label", label)
        .append("value", value)
        .append("answer", answer)
        .append("alert", alert)
        .append("style", style)
        .append("url", url)
        .append("webViewHeightRatio", webViewHeightRatio)
        .append("extensions", extensions)
        .toString();
  }
}
