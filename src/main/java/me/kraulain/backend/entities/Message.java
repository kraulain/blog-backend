package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Message {
  private int id;
  private String email;
  private String name;
  private String message;
  private Date date;
  private String status = "new"; // read, replied, deleted
  private String language = "en"; // fr, de

  public Message(){}

  public Message(int id, String email, String name, String message, Date date, String status, String language) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.message = message;
    this.date = date;
    this.status = status;
    this.language = language;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
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
    return "Message{" +
      "id=" + id +
      ", email='" + email + '\'' +
      ", name='" + name + '\'' +
      ", message='" + message + '\'' +
      ", date=" + date +
      ", status='" + status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Message message1 = (Message) o;
    return getId() == message1.getId() &&
      Objects.equals(getEmail(), message1.getEmail()) &&
      Objects.equals(getName(), message1.getName()) &&
      Objects.equals(getMessage(), message1.getMessage()) &&
      Objects.equals(getDate(), message1.getDate()) &&
      Objects.equals(getStatus(), message1.getStatus()) &&
      Objects.equals(getLanguage(), message1.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getName(), getMessage(), getDate(), getStatus(), getLanguage());
  }
}
