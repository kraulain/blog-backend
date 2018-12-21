package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Article {
  private int id;
  private String title;
  private String subTitle;
  private String imageUrl;
  private String body;
  private Date date;
  private String Status = "draft"; // published, deleted

  public Article(){}

  public Article(int id, String title, String subtitle, String imageUrl, String body, Date date, String status) {
    this.id = id;
    this.title = title;
    this.subTitle = subtitle;
    this.imageUrl = imageUrl;
    this.body = body;
    this.date = date;
    Status = status;
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

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  @Override
  public String toString() {
    return "Article{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", subTitle='" + subTitle + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", date=" + date +
      ", Status='" + Status + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Article article = (Article) o;
    return getId() == article.getId() &&
      Objects.equals(getTitle(), article.getTitle()) &&
      Objects.equals(getSubTitle(), article.getSubTitle()) &&
      Objects.equals(getImageUrl(), article.getImageUrl()) &&
      Objects.equals(getBody(), article.getBody()) &&
      Objects.equals(getDate(), article.getDate()) &&
      Objects.equals(getStatus(), article.getStatus());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getSubTitle(), getImageUrl(), getBody(), getDate(), getStatus());
  }
}
