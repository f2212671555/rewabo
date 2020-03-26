package translate.model.javabean.nicp.result;

import translate.model.javabean.nicp.originalMessage.NICPOriginalMessage;
import translate.model.javabean.nicp.payload.NICPPayload;
import translate.model.tools.Generator;

public class NICPResult extends Result{

  public NICPResult() {
    this.setPayload(new NICPPayload());
    this.setOriginalMessage(new NICPOriginalMessage());
    this.setMsgid(Generator.generateMessageId());
  }
}
