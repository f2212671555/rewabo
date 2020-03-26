package translate.model.javabean.nicp.request;

public class OrderRequest {
    /**
     * query : {"orderID":"1phbzchv1dyvr6u"}
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

    public static class QueryBean {
        /**
         * orderID : 1phbzchv1dyvr6u
         */

        private String orderID;

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
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
