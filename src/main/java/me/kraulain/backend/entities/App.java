package me.kraulain.backend.entities;

import java.util.Arrays;
import java.util.Objects;

public class App {
  private int id;
  private String name;
  private String subTitle;
  private String description;
  private String[] imageUrls;
  private String playStoreUrl;
  private String appStoreUrl;
  private String Status = "draft"; // published, deleted
  private String language = "en"; // fr, de

  public App() {
  }

  public App(int id, String name, String subTitle, String description, String[] imageUrls, String playStoreUrl, String appStoreUrl, String status, String language) {
    this.id = id;
    this.name = name;
    this.subTitle = subTitle;
    this.description = description;
    this.imageUrls = imageUrls;
    this.playStoreUrl = playStoreUrl;
    this.appStoreUrl = appStoreUrl;
    Status = status;
    this.language = language;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String[] getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(String[] imageUrls) {
    this.imageUrls = imageUrls;
  }

  public String getPlayStoreUrl() {
    return playStoreUrl;
  }

  public void setPlayStoreUrl(String playStoreUrl) {
    this.playStoreUrl = playStoreUrl;
  }

  public String getAppStoreUrl() {
    return appStoreUrl;
  }

  public void setAppStoreUrl(String appStoreUrl) {
    this.appStoreUrl = appStoreUrl;
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
    return "App{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", subTitle='" + subTitle + '\'' +
      ", description='" + description + '\'' +
      ", imageUrls=" + Arrays.toString(imageUrls) +
      ", playStoreUrl='" + playStoreUrl + '\'' +
      ", appStoreUrl='" + appStoreUrl + '\'' +
      ", Status='" + Status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    App app = (App) o;
    return getId() == app.getId() &&
      Objects.equals(getName(), app.getName()) &&
      Objects.equals(getSubTitle(), app.getSubTitle()) &&
      Objects.equals(getDescription(), app.getDescription()) &&
      Arrays.equals(getImageUrls(), app.getImageUrls()) &&
      Objects.equals(getPlayStoreUrl(), app.getPlayStoreUrl()) &&
      Objects.equals(getAppStoreUrl(), app.getAppStoreUrl()) &&
      Objects.equals(getStatus(), app.getStatus()) &&
      Objects.equals(getLanguage(), app.getLanguage());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getId(), getName(), getSubTitle(), getDescription(), getPlayStoreUrl(), getAppStoreUrl(), getStatus(), getLanguage());
    result = 31 * result + Arrays.hashCode(getImageUrls());
    return result;
  }
}
