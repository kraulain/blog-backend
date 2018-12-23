package me.kraulain.backend.entities;

import java.util.Arrays;
import java.util.Objects;

public class Sms {
  private int id;
  private String[] receivers; //recievers phonenumbers
  private String body;
  private String status = "new"; // sent, failed, deleted
  private String language = "en"; // fr, de

  public Sms() {
  }

  public Sms(int id, String[] receivers, String body, String status, String language) {
    this.id = id;
    this.receivers = receivers;
    this.body = body;
    this.status = status;
    this.language = language;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String[] getReceivers() {
    return receivers;
  }

  public void setReceivers(String[] receivers) {
    this.receivers = receivers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "Sms{" +
      "id=" + id +
      ", receivers=" + Arrays.toString(receivers) +
      ", body='" + body + '\'' +
      ", status='" + status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sms sms = (Sms) o;
    return getId() == sms.getId() &&
      Arrays.equals(getReceivers(), sms.getReceivers()) &&
      Objects.equals(getBody(), sms.getBody()) &&
      Objects.equals(getStatus(), sms.getStatus()) &&
      Objects.equals(getLanguage(), sms.getLanguage());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getId(), getBody(), getStatus(), getLanguage());
    result = 31 * result + Arrays.hashCode(getReceivers());
    return result;
  }
}
