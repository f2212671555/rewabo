package translate.model.tools;

import Menu.model.javabean.MenuClass;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pojo.Settings.rewabo.urls.webs;
import translate.model.javabean.nicp.button.Button;
import translate.model.javabean.nicp.element.Element;
import translate.model.javabean.nicp.template.NICPTemplate;
import translate.model.javabean.nicp.template.Template;

public class Generator {

  public static Template getFoodJson(String chatId, List<MenuClass> menuClassList) {

    NICPTemplate template = new NICPTemplate();
    // generate elements-----BEGIN
    List<Element> elements = new ArrayList<>();
    int count = 0;
    for (MenuClass menuClass : menuClassList) {
      count++;
      String price = menuClass.getPrice();
      String url = menuClass.getUrl();
      String name = menuClass.getName();
      Element element = new Element();
      element.setTitle(name);
      element.setSubtitle(price);
      element.setImageUrl(url);
      elements.add(element);

      if (count == 10)// messenger limit "element" at most 10
      {
        List<Button> buttons = new ArrayList<>();
        buttons.add(Generator.generateUrlButton(webs.orderUrl, "點我查看更多"));
        element.setButtons(buttons);
        break;
      }

    }
    // generate elements-----END
    template.getResult().getPayload().setElements(elements);
    template.getResult().getPayload().setChatId(chatId);
    template.getResult().getOriginalMessage().getChat().setId(chatId);
    return template;
  }

  public static Button generateUrlButton(String url, String msg) {
    Button button = new Button();
    button.setType("url");
    button.setLabel(msg);
    button.setUrl(url);
    button.setWebViewHeightRatio("full");
    button.setExtensions(true);
    button.setAlert(false);
    return button;
  }

  public static String generateMessageId() {
    return UUID.randomUUID().toString();
  }
}
