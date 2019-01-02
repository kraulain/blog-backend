package me.kraulain.backend.entities;

import java.util.Objects;

public class User {
  private int id;
  private String name;
  private String email;
  private String status = "active"; // inactive
  private String language = "en"; // fr, de

  public User(){}

  public User(int id, String name, String email, String status, String language) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.status = status;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", Issue='" + email + '\'' +
      ", status='" + status + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return getId() == user.getId() &&
      Objects.equals(getName(), user.getName()) &&
      Objects.equals(getEmail(), user.getEmail()) &&
      Objects.equals(getStatus(), user.getStatus()) &&
      Objects.equals(getLanguage(), user.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getEmail(), getStatus(), getLanguage());
  }
}
