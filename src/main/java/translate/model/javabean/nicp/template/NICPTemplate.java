package translate.model.javabean.nicp.template;

import javax.servlet.http.HttpServletResponse;
import translate.model.javabean.nicp.result.NICPResult;

public class NICPTemplate extends Template {

  public NICPTemplate() {
    this.setResult(new NICPResult());
    this.setMessage("");
    this.setStatus(HttpServletResponse.SC_OK);
  }
}
