package me.kraulain.backend.entities;

import java.util.Objects;

public class Podcast {
  private int id;
  private String title;
  private String subTitle;
  private String imageUrl;
  private String soundcloudUrl;
  private String description;
  private String Status = "draft"; // published, deleted
  private String language = "en"; // fr, de

  public Podcast(){}

  public Podcast(int id, String title, String subTitle, String imageUrl, String soundcloudUrl, String description, String status, String language) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.imageUrl = imageUrl;
    this.soundcloudUrl = soundcloudUrl;
    this.description = description;
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

  public String getSoundcloudUrl() {
    return soundcloudUrl;
  }

  public void setSoundcloudUrl(String soundcloudUrl) {
    this.soundcloudUrl = soundcloudUrl;
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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "Video{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", subTitle='" + subTitle + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", soundcloudUrl='" + soundcloudUrl + '\'' +
      ", description='" + description + '\'' +
      ", Status='" + Status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Podcast podcast = (Podcast) o;
    return getId() == podcast.getId() &&
      Objects.equals(getTitle(), podcast.getTitle()) &&
      Objects.equals(getSubTitle(), podcast.getSubTitle()) &&
      Objects.equals(getImageUrl(), podcast.getImageUrl()) &&
      Objects.equals(getSoundcloudUrl(), podcast.getSoundcloudUrl()) &&
      Objects.equals(getDescription(), podcast.getDescription()) &&
      Objects.equals(getStatus(), podcast.getStatus()) &&
      Objects.equals(getLanguage(), podcast.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getSubTitle(), getImageUrl(), getSoundcloudUrl(), getDescription(), getStatus(), getLanguage());
  }
}
