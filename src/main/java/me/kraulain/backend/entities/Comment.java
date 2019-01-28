package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Comment {
  private int id;
  private int userId;
  private String body;
  private Date datePublished;
  private String status = "created"; // deleted
  private String language = "en"; // fr, de

  public Comment(){}

  public Comment(int id, int userId, String body, Date datePublished, String status, String language) {
    this.id = id;
    this.userId = userId;
    this.body = body;
    this.datePublished = datePublished;
    this.status = status;
    this.language = language;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Date getDatePublished() {
    return datePublished;
  }

  public void setDatePublished(Date datePublished) {
    this.datePublished = datePublished;
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
      ", datePublished=" + datePublished +
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
      Objects.equals(getDatePublished(), comment.getDatePublished()) &&
      Objects.equals(getStatus(), comment.getStatus()) &&
      Objects.equals(getLanguage(), comment.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserId(), getBody(), getDatePublished(), getStatus(), getLanguage());
  }
}
