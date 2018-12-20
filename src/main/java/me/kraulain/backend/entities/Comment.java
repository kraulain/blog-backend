package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Comment {
  private int id;
  private String userEmail;
  private String name;
  private String body;
  private Date date;
  private String status = "created"; // deleted

  public Comment(){}

  public Comment(int id, String userEmail, String name, String body, Date date, String status) {
    this.id = id;
    this.userEmail = userEmail;
    this.name = name;
    this.body = body;
    this.date = date;
    this.status = status;
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
