package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Article {
  private int id;
  private String title;
  private String subTitle;
  private String imageUrl;
  private String body;
  private Date publishedDate;
  private String Status = "draft"; // published, deleted
  private String language = "en"; // fr, de

  public Article(){}

  public Article(int id, String title, String subTitle, String imageUrl, String body, Date publishedDate, String status, String language) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.imageUrl = imageUrl;
    this.body = body;
    this.publishedDate = publishedDate;
    Status = status;
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

  public Date getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(Date publishedDate) {
    this.publishedDate = publishedDate;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "Article{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", subTitle='" + subTitle + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", body='" + body + '\'' +
      ", publishedDate=" + publishedDate +
      ", Status='" + Status + '\'' +
      ", language='" + language + '\'' +
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
      Objects.equals(getPublishedDate(), article.getPublishedDate()) &&
      Objects.equals(getStatus(), article.getStatus()) &&
      Objects.equals(getLanguage(), article.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getSubTitle(), getImageUrl(), getBody(), getPublishedDate(), getStatus(), getLanguage());
  }
}
