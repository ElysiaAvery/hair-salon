import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Appointment {
  private String date;
  private String time;
  private int clientId;
  private int id;

  public Appointment(String date, String time, int clientId) {
    this.date = date;
    this.time = time;
    this.clientId = clientId;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public int getId() {
    return id;
  }

  public int getClientId() {
    return clientId;
  }

  public int getStylistId() {
    return Client.findById(this.clientId).getStylistId();
  }

  public void setDate(String date) {
    this.date = date;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE appointments SET date=:date WHERE id=:id")
      .addParameter("date", date)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void setTime(String time) {
    this.time = time;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE appointments SET time=:time WHERE id=:id")
      .addParameter("time", time)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO appointments (date, time, clientId) VALUES (:date, :time, :clientId)", true)
        .addParameter("date", this.date)
        .addParameter("time", this.time)
        .addParameter("clientId", this.clientId)
        .executeUpdate().getKey();
    }
  }

  @Override
  public boolean equals(Object testObj) {
    if(!(testObj instanceof Appointment))
      return false;
    else {
      Appointment appointmentCast = (Appointment) testObj;
      return (this.id == appointmentCast.getId() && this.clientId == appointmentCast.getClientId());
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM appointments WHERE id = :id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static Appointment findByAId(int id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM appointments WHERE id = :id")
        .addParameter("id", id)
        .executeAndFetchFirst(Appointment.class);
    }
  }

  public static List<Appointment> all() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM appointments")
        .executeAndFetch(Appointment.class);
    }
  }

}
