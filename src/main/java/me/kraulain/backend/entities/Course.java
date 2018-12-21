package me.kraulain.backend.entities;

import java.util.Arrays;
import java.util.Objects;

public class Course {
  private int id;
  private String title;
  private String imageUrl;
  private String Description;
  private int[] articles;
  private String Status = "draft"; // published, deleted

  public Course(){}

  public Course(int id, String title, String imageUrl, String description, int[] articles, String status) {
    this.id = id;
    this.title = title;
    this.imageUrl = imageUrl;
    Description = description;
    this.articles = articles;
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

  public int[] getArticles() {
    return articles;
  }

  public void setArticles(int[] articles) {
    this.articles = articles;
  }

  public void setStatus(String status) {
    Status = status;
  }

  @Override
  public String toString() {
    return "Course{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", articles=" + Arrays.toString(articles) +
      ", Status='" + Status + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Course course = (Course) o;
    return getId() == course.getId() &&
      Objects.equals(getTitle(), course.getTitle()) &&
      Objects.equals(getImageUrl(), course.getImageUrl()) &&
      Objects.equals(getDescription(), course.getDescription()) &&
      Arrays.equals(getArticles(), course.getArticles()) &&
      Objects.equals(getStatus(), course.getStatus());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getId(), getTitle(), getImageUrl(), getDescription(), getStatus());
    result = 31 * result + Arrays.hashCode(getArticles());
    return result;
  }
}
