package translate.model.javabean.nicp.request;

public class CommonRequest {
    /**
     * query : {}
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
