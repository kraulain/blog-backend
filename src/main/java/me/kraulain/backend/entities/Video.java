package me.kraulain.backend.entities;

public class Video {
  private int id;
  private String title;
  private String subTitle;
  private String youtubeUrl;
  private String description;
  private String Status = "draft"; // published, deleted

  public Video(){}

  public Video(int id, String title, String subTitle, String youtubeUrl, String description, String status) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.youtubeUrl = youtubeUrl;
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

  public String getYoutubeUrl() {
    return youtubeUrl;
  }

  public void setYoutubeUrl(String youtubeUrl) {
    this.youtubeUrl = youtubeUrl;
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
}
