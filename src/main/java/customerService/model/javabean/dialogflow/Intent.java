package customerService.model.javabean.dialogflow;

import java.util.List;

public class Intent {
  private String name;
  private String displayName;
  private List<TrainingPhrase> trainingPhrases;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<TrainingPhrase> getTrainingPhrases() {
    return trainingPhrases;
  }

  public void setTrainingPhrases(
      List<TrainingPhrase> trainingPhrases) {
    this.trainingPhrases = trainingPhrases;
  }
}
