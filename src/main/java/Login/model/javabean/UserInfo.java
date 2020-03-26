package Login.model.javabean;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserInfo {
  String username;
  String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("username", username)
        .append("password", password)
        .toString();
  }
}
