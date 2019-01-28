package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Comment {
  private int id;
  private String userId;
  private String body;
  private Date date;
  private String status = "created"; // deleted
  private String language = "en"; // fr, de

  public Comment(){}

  public Comment(int id, String userId, String body, Date date, String status, String language) {
    this.id = id;
    this.userId = userId;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
    return "Comment{" +
      "id=" + id +
      ", userId='" + userId + '\'' +
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
    Comment comment = (Comment) o;
    return getId() == comment.getId() &&
      Objects.equals(getUserId(), comment.getUserId()) &&
      Objects.equals(getBody(), comment.getBody()) &&
      Objects.equals(getDate(), comment.getDate()) &&
      Objects.equals(getStatus(), comment.getStatus()) &&
      Objects.equals(getLanguage(), comment.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserId(), getBody(), getDate(), getStatus(), getLanguage());
  }
}
