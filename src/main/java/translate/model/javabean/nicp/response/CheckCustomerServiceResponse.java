package translate.model.javabean.nicp.response;

public class CheckCustomerServiceResponse {

  /**
   * result : true
   * message :
   * status : 200
   */

  private boolean result;
  private String message;
  private int status;

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
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
}
