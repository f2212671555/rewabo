package translate.model.javabean.nicp.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CheckCustomerServiceRequest {

  /**
   * query : {"intentName":"補餐巾紙"}
   * userData : {"chatId":"2227048243983599"}
   * result : {}
   */

  private QueryBean query;
  private UserDataBean userData;
  private ResultBean result;

  public QueryBean getQuery() {
    return query;
  }

  public void setQuery(QueryBean query) {
    this.query = query;
  }

  public UserDataBean getUserData() {
    return userData;
  }

  public void setUserData(UserDataBean userData) {
    this.userData = userData;
  }

  public ResultBean getResult() {
    return result;
  }

  public void setResult(ResultBean result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("query", query)
        .append("userData", userData)
        .append("result", result)
        .toString();
  }

  public static class QueryBean {

    /**
     * intentName : 補餐巾紙
     */

    private String intentName;

    public String getIntentName() {
      return intentName;
    }

    public void setIntentName(String intentName) {
      this.intentName = intentName;
    }
  }

  public static class UserDataBean {

    /**
     * chatId : 2227048243983599
     */

    private String chatId;

    public String getChatId() {
      return chatId;
    }

    public void setChatId(String chatId) {
      this.chatId = chatId;
    }
  }

  public static class ResultBean {

  }
}
