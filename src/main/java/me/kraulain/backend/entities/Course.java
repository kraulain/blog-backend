package me.kraulain.backend.entities;

import java.util.Arrays;
import java.util.Objects;

public class Course {
  private int id;
  private String title;
  private String subTitle;
  private String imageUrl;
  private String Description;
  private int[] articles_ids;
  private String Status = "draft"; // published, deleted
  private String language = "en"; // fr, de

  public Course(){}

  public Course(int id, String title, String subTitle, String imageUrl, String description, int[] articles_ids, String status, String language) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.imageUrl = imageUrl;
    Description = description;
    this.articles_ids = articles_ids;
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

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public String getStatus() {
    return Status;
  }

  public int[] getArticles_ids() {
    return articles_ids;
  }

  public void setArticles_ids(int[] articles_ids) {
    this.articles_ids = articles_ids;
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
    return "Course{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", subTitle='" + subTitle + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", Description='" + Description + '\'' +
      ", articles_ids=" + Arrays.toString(articles_ids) +
      ", Status='" + Status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Course course = (Course) o;
    return getId() == course.getId() &&
      Objects.equals(getTitle(), course.getTitle()) &&
      Objects.equals(getSubTitle(), course.getSubTitle()) &&
      Objects.equals(getImageUrl(), course.getImageUrl()) &&
      Objects.equals(getDescription(), course.getDescription()) &&
      Arrays.equals(getArticles_ids(), course.getArticles_ids()) &&
      Objects.equals(getStatus(), course.getStatus()) &&
      Objects.equals(getLanguage(), course.getLanguage());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getId(), getTitle(), getSubTitle(), getImageUrl(), getDescription(), getStatus(), getLanguage());
    result = 31 * result + Arrays.hashCode(getArticles_ids());
    return result;
  }
}
