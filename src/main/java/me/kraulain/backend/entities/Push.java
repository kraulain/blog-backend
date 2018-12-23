package me.kraulain.backend.entities;

import java.util.Objects;

public class Push { // a push notification
  private int id;
  private String title;
  private String imageUrl;
  private String body;
  private String status = "new"; // read, deleted
  private String language = "en"; // fr, de

  public Push() {
  }

  public Push(int id, String title, String imageUrl, String body, String status, String language) {
    this.id = id;
    this.title = title;
    this.imageUrl = imageUrl;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
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
    return "Push{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", body='" + body + '\'' +
      ", status='" + status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Push push = (Push) o;
    return getId() == push.getId() &&
      Objects.equals(getTitle(), push.getTitle()) &&
      Objects.equals(getImageUrl(), push.getImageUrl()) &&
      Objects.equals(getBody(), push.getBody()) &&
      Objects.equals(getStatus(), push.getStatus()) &&
      Objects.equals(getLanguage(), push.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getImageUrl(), getBody(), getStatus(), getLanguage());
  }
}
