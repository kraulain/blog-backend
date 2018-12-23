package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Reply {
  private int id;
  private String userEmail;
  private String name;
  private int replyTo;
  private String body;
  private Date date;
  private String status = "created"; // deleted
  private String language = "en"; // fr, de

  public Reply(){}

  public Reply(int id, String userEmail, String name, int replyTo, String body, Date date, String status, String language) {
    this.id = id;
    this.userEmail = userEmail;
    this.name = name;
    this.replyTo = replyTo;
    this.body = body;
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

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getReplyTo() {
    return replyTo;
  }

  public void setReplyTo(int replyTo) {
    this.replyTo = replyTo;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
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
    return "Reply{" +
      "id=" + id +
      ", userEmail='" + userEmail + '\'' +
      ", name='" + name + '\'' +
      ", replyTo=" + replyTo +
      ", body='" + body + '\'' +
      ", date=" + date +
      ", status='" + status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reply reply = (Reply) o;
    return getId() == reply.getId() &&
      getReplyTo() == reply.getReplyTo() &&
      Objects.equals(getUserEmail(), reply.getUserEmail()) &&
      Objects.equals(getName(), reply.getName()) &&
      Objects.equals(getBody(), reply.getBody()) &&
      Objects.equals(getDate(), reply.getDate()) &&
      Objects.equals(getStatus(), reply.getStatus()) &&
      Objects.equals(getLanguage(), reply.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserEmail(), getName(), getReplyTo(), getBody(), getDate(), getStatus(), getLanguage());
  }
}
