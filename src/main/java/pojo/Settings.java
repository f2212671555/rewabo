package pojo;

public class Settings {

  public static class nicp {

    public static class urls {

      public static String baseUrl;

      public static class services {

        public static String webhookUrl;
        public static String receiveCustomerOrderUrl;
        public static String pushUrl;
      }
    }
      public static class shopKeeper {

        public static String id;
      }
  }
  public static class rewabo {

      public static class platform {

        public static String name;
        public static String type;
        public static String address;
        public static String googleMapUrl;
    }
    public static class urls {

      public static String baseUrl;

      public static class webs {

        public static String orderUrl;
        public static String orderTargetContentUrl;
        public static String historyOrderUrl;
      }
    }
  }
  public static class google {

    public static class api {

      public static class dialogFlow {

        public static String authUrl;
        public static String baseUrl;

        public static class intents {

          public static String listUrl;
          public static String getUrl;
          public static String createUrl;
          public static String deleteUrl;
          public static String addTrainingPhrasesUrl;
        }
      }
    }
  }
}
