import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.sql2o.*;

public class Client {
  private int id;
  private String name;
  private String phone;
  private String notes;
  private int stylistId;

  public Client(String name, String phone, String notes, int stylistId) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.notes = notes;
    this.stylistId = stylistId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getNotes() {
    return notes;
  }

  public int getStylistId() {
    return stylistId;
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getId() == newClient.getId() &&
             this.getName().equals(newClient.getName()) &&
             this.getPhone().equals(newClient.getPhone()) &&
             this.getNotes().equals(newClient.getNotes()) &&
             this.getStylistId() == newClient.getStylistId();
    }
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, phone, notes, stylistId FROM clients";
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients(name, phone, notes, stylistId)  VALUES (:name, :phone, :notes, :stylistId)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("phone", this.phone)
      .addParameter("notes", this.notes)
      .addParameter("stylistId", this.stylistId)
      .executeUpdate()
      .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id = :id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public void updateClientName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateClientPhone(String phone) {
    this.phone = phone;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET phone = :phone WHERE id = :id";
      con.createQuery(sql)
        .addParameter("phone", phone)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateClientNotes(String notes) {
    this.notes = notes;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET notes = :notes WHERE id = :id";
      con.createQuery(sql)
        .addParameter("notes", notes)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
