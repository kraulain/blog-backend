package me.kraulain.backend.entities;

import java.util.Arrays;
import java.util.Objects;

public class Email {
  private int id;
  private String[] receivers;
  private String subject;
  private String sender; //sender Issue address
  private String body;
  private String signature;
  private String status = "new"; // sent, failed, deleted
  private String language = "en"; // fr, de

  public Email() {
  }

  public Email(int id, String[] receivers, String subject, String sender, String body, String signature, String status, String language) {
    this.id = id;
    this.receivers = receivers;
    this.subject = subject;
    this.sender = sender;
    this.body = body;
    this.signature = signature;
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

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
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
    return "Email{" +
      "id=" + id +
      ", receivers=" + Arrays.toString(receivers) +
      ", subject='" + subject + '\'' +
      ", sender='" + sender + '\'' +
      ", body='" + body + '\'' +
      ", signature='" + signature + '\'' +
      ", status='" + status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Email email = (Email) o;
    return getId() == email.getId() &&
      Arrays.equals(getReceivers(), email.getReceivers()) &&
      Objects.equals(getSubject(), email.getSubject()) &&
      Objects.equals(getSender(), email.getSender()) &&
      Objects.equals(getBody(), email.getBody()) &&
      Objects.equals(getSignature(), email.getSignature()) &&
      Objects.equals(getStatus(), email.getStatus()) &&
      Objects.equals(getLanguage(), email.getLanguage());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getId(), getSubject(), getSender(), getBody(), getSignature(), getStatus(), getLanguage());
    result = 31 * result + Arrays.hashCode(getReceivers());
    return result;
  }
}
