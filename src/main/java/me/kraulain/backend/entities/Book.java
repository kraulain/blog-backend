package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Book {
  private int id;
  private String title;
  private String subtitle;
  private String imageUrl;
  private String description;
  private Date datePublished;
  private String Status = "draft"; // published, deleted

  public Book(){}

  public Book(int id, String title, String subtitle, String imageUrl, String description, Date datePublished, String status) {
    this.id = id;
    this.title = title;
    this.subtitle = subtitle;
    this.imageUrl = imageUrl;
    this.description = description;
    this.datePublished = datePublished;
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

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDatePublished() {
    return datePublished;
  }

  public void setDatePublished(Date datePublished) {
    this.datePublished = datePublished;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  @Override
  public String toString() {
    return "Book{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", subtitle='" + subtitle + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", datePublished=" + datePublished +
      ", Status='" + Status + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return getId() == book.getId() &&
      Objects.equals(getTitle(), book.getTitle()) &&
      Objects.equals(getSubtitle(), book.getSubtitle()) &&
      Objects.equals(getImageUrl(), book.getImageUrl()) &&
      Objects.equals(getDescription(), book.getDescription()) &&
      Objects.equals(getDatePublished(), book.getDatePublished()) &&
      Objects.equals(getStatus(), book.getStatus());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getSubtitle(), getImageUrl(), getDescription(), getDatePublished(), getStatus());
  }
}
