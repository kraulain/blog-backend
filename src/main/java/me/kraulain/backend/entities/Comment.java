package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Comment {
  private int id;
  private String userEmail;
  private String body;
  private Date date;

  public Comment(){}

  public Comment(int id, String userEmail, String body, Date date) {
    this.setId(id);
    this.setUserEmail(userEmail);
    this.setBody(body);
    this.setDate(date);
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

  @Override
  public String toString() {
    return "Comment{" +
      "id=" + id +
      ", userEmail='" + userEmail + '\'' +
      ", date=" + date +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comment comment = (Comment) o;
    return getId() == comment.getId() &&
      Objects.equals(getUserEmail(), comment.getUserEmail()) &&
      Objects.equals(getBody(), comment.getBody()) &&
      Objects.equals(getDate(), comment.getDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserEmail(), getBody(), getDate());
  }
}
