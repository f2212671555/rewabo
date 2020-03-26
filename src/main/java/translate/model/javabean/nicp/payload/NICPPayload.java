package translate.model.javabean.nicp.payload;

import translate.model.tools.Generator;

public class NICPPayload extends Payload{

  public NICPPayload() {
    this.setType("generic-template");
    this.setAspectRatio("horizontal");
    this.setSharable(true);
    this.setMessageId(Generator.generateMessageId());
  }
}
