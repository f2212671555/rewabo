package customerService.model.javabean.dialogflow;

import java.util.List;

public class TrainingPhrase {

  /**
   * name : 68b7e851-7ce8-453e-8b86-b0a5907bdf10
   * type : EXAMPLE
   * parts : [{"text":"string23dfsakk"}]
   */

  private String name;
  private String type;
  private List<PartsBean> parts;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<PartsBean> getParts() {
    return parts;
  }

  public void setParts(List<PartsBean> parts) {
    this.parts = parts;
  }

  public static class PartsBean {

    /**
     * text : string23dfsakk
     */

    private String text;

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }
  }
}
