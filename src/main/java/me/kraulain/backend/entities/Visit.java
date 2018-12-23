package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Visit {
  private int id;
  private Date date;
  private String ip;
  private String browser;
  private String country;
  private String device;
  private String language = "en"; // fr, de

  public Visit() {
  }

  public Visit(int id, Date date, String ip, String browser, String country, String device, String language) {
    this.id = id;
    this.date = date;
    this.ip = ip;
    this.browser = browser;
    this.country = country;
    this.device = device;
    this.language = language;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getBrowser() {
    return browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "Visit{" +
      "id=" + id +
      ", date=" + date +
      ", ip='" + ip + '\'' +
      ", browser='" + browser + '\'' +
      ", country='" + country + '\'' +
      ", device='" + device + '\'' +
      ", language='" + language + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Visit visit = (Visit) o;
    return getId() == visit.getId() &&
      Objects.equals(getDate(), visit.getDate()) &&
      Objects.equals(getIp(), visit.getIp()) &&
      Objects.equals(getBrowser(), visit.getBrowser()) &&
      Objects.equals(getCountry(), visit.getCountry()) &&
      Objects.equals(getDevice(), visit.getDevice()) &&
      Objects.equals(getLanguage(), visit.getLanguage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDate(), getIp(), getBrowser(), getCountry(), getDevice(), getLanguage());
  }
}
