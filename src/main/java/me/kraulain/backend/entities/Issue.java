package me.kraulain.backend.entities;

import java.sql.Date;
import java.util.Objects;

public class Issue {
  private int id;
  private Date date;
  private String cause;
  private String errorMessage;
  private String status = "new"; // seen, solved, deleted

  public Issue() {
  }

  public Issue(int id, Date date, String cause, String errorMessage, String status) {
    this.id = id;
    this.date = date;
    this.cause = cause;
    this.errorMessage = errorMessage;
    this.status = status;
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

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Issue{" +
      "id=" + id +
      ", date=" + date +
      ", cause='" + cause + '\'' +
      ", errorMessage='" + errorMessage + '\'' +
      ", status='" + status + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return getId() == issue.getId() &&
      Objects.equals(getDate(), issue.getDate()) &&
      Objects.equals(getCause(), issue.getCause()) &&
      Objects.equals(getErrorMessage(), issue.getErrorMessage()) &&
      Objects.equals(getStatus(), issue.getStatus());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDate(), getCause(), getErrorMessage(), getStatus());
  }
}
