import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("appointments", Appointment.all());
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String stylistName = request.queryParams("stylist-name");
      Stylist newStylist = new Stylist(stylistName);
      newStylist.save();
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylistId);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
      String clientName = request.queryParams("client-name");
      Client newClient = new Client(clientName, stylist.getId());
      newClient.save();
      model.put("stylist", stylist);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:clientId", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Client client = Client.findById(Integer.parseInt(request.params(":clientId")));
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/appointments", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      Client client = Client.findById(Integer.parseInt(request.queryParams("clientId")));
      Appointment newAppointment = new Appointment(date, time, client.getId());
      newAppointment.save();
      model.put("client", client);
      model.put("template", "templates/appointments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/appointments/:aId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Appointment appointment = Appointment.findByAId(Integer.parseInt(request.params(":aId")));
      model.put("appointment", appointment);
      model.put("template", "templates/appointment.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:clientId/appointments/:aId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Appointment appointmentId = Appointment.findByAId(Integer.parseInt(request.params(":aId")));
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":clientId")));
      model.put("client", clientId);
      model.put("appointment", appointmentId);
      model.put("template", "templates/appointment.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/:clientId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylistId);
      model.put("client", clientId);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/update",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      String stylistName = request.queryParams("stylist-name");
      Stylist newStylist = Stylist.find(stylist.getId());
      stylist.updateStylistName(stylistName);
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/:clientId/update",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Client client = Client.findById(Integer.parseInt(request.params(":clientId")));
      String clientName = request.queryParams("client-name");
      Stylist stylist = Stylist.find(client.getStylistId());
      client.updateClientName(clientName);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/:clientId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      clientId.delete();
      model.put("stylist", stylistId);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/:clientId/appointments/:aId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      Appointment appointment = Appointment.findByAId(Integer.parseInt(request.params(":aId")));
      appointment.delete();
      model.put("client", clientId);
      model.put("template", "templates/appointments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      stylist.delete();
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("appointments", Appointment.all());
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String stylistName = request.queryParams("stylist-name");
      Stylist newStylist = new Stylist(stylistName);
      newStylist.save();
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylistId);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
      String clientName = request.queryParams("client-name");
      Client newClient = new Client(clientName, stylist.getId());
      newClient.save();
      model.put("stylist", stylist);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:clientId", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Client client = Client.findById(Integer.parseInt(request.params(":clientId")));
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/appointments", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      Client client = Client.findById(Integer.parseInt(request.queryParams("clientId")));
      Appointment newAppointment = new Appointment(date, time, client.getId());
      newAppointment.save();
      model.put("client", client);
      model.put("template", "templates/appointments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/appointments/:aId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Appointment appointment = Appointment.findByAId(Integer.parseInt(request.params(":aId")));
      model.put("appointment", appointment);
      model.put("template", "templates/appointment.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:clientId/appointments/:aId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Appointment appointmentId = Appointment.findByAId(Integer.parseInt(request.params(":aId")));
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":clientId")));
      model.put("client", clientId);
      model.put("appointment", appointmentId);
      model.put("template", "templates/appointment.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/:clientId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylistId);
      model.put("client", clientId);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/update",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      String stylistName = request.queryParams("stylist-name");
      Stylist newStylist = Stylist.find(stylist.getId());
      stylist.updateStylistName(stylistName);
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/:clientId/update",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Client client = Client.findById(Integer.parseInt(request.params(":clientId")));
      String clientName = request.queryParams("client-name");
      Stylist stylist = Stylist.find(client.getStylistId());
      client.updateClientName(clientName);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/:clientId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      clientId.delete();
      model.put("stylist", stylistId);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/:clientId/appointments/:aId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      Appointment appointment = Appointment.findByAId(Integer.parseInt(request.params(":aId")));
      appointment.delete();
      model.put("client", clientId);
      model.put("template", "templates/appointments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      stylist.delete();
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
