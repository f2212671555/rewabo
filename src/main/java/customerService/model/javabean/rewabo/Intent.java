package customerService.model.javabean.rewabo;

import java.util.List;

public class Intent {


    /**
     * Intent_Id : 1232435465ytrfdergr3
     * trainingPhrase : ["幫ㄇ開","電扇幫ㄎ"]
     */

    private String Intent_Id;
    private List<String> trainingPhrase;

    public String getIntent_Id() {
        return Intent_Id;
    }

    public void setIntent_Id(String Intent_Id) {
        this.Intent_Id = Intent_Id;
    }

    public List<String> getTrainingPhrase() {
        return trainingPhrase;
    }

    public void setTrainingPhrase(List<String> trainingPhrase) {
        this.trainingPhrase = trainingPhrase;
    }
}
