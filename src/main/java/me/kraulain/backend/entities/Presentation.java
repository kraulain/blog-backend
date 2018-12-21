package me.kraulain.backend.entities;

import java.util.Arrays;
import java.util.Objects;

public class Presentation {
  private int id;
  private String title;
  private String subTitle;
  private String imageUrl;
  private String[] slides;
  private String description;
  private String Status = "draft"; // published, deleted

  public Presentation() {
  }

  public Presentation(int id, String title, String subTitle, String imageUrl, String[] slides, String description, String status) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.imageUrl = imageUrl;
    this.slides = slides;
    this.description = description;
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

  public String[] getSlides() {
    return slides;
  }

  public void setSlides(String[] slides) {
    this.slides = slides;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  @Override
  public String toString() {
    return "Presentation{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", subTitle='" + subTitle + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", slides=" + Arrays.toString(slides) +
      ", Status='" + Status + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Presentation that = (Presentation) o;
    return getId() == that.getId() &&
      Objects.equals(getTitle(), that.getTitle()) &&
      Objects.equals(getSubTitle(), that.getSubTitle()) &&
      Objects.equals(getImageUrl(), that.getImageUrl()) &&
      Arrays.equals(getSlides(), that.getSlides()) &&
      Objects.equals(getDescription(), that.getDescription()) &&
      Objects.equals(getStatus(), that.getStatus());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getId(), getTitle(), getSubTitle(), getImageUrl(), getDescription(), getStatus());
    result = 31 * result + Arrays.hashCode(getSlides());
    return result;
  }
}
