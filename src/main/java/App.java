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

    post("/clients",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("stylist-id")));
      String clientName = request.queryParams("client-name");
      Client newClient = new Client(clientName, stylist.getId());
      newClient.save();
      model.put("stylist", stylist);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/appointments", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      Client client = Client.findById(Integer.parseInt(request.queryParams("client-id")));
      Appointment newAppointment = new Appointment(date, time, client.getId());
      newAppointment.save();
      model.put("client", client);
      model.put("template", "templates/appointments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      Stylist stylistId = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylistId);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/:clientId/appointments/add", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":id"));
      Appointment newAppointment = new Appointment(date, time, clientId);
      model.put("stylist", Stylist.find(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/:clientId/appointments/:aId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int appointmentId = Integer.parseInt(request.params(":aId"));
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":id"));
      model.put("stylist", Stylist.find(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("appointment", Appointment.findByAId(appointmentId));
      model.put("template", "templates/appointment.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/add", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      String newName = request.queryParams("client-name");
      int stylistId = Integer.parseInt(request.params(":id"));
      Client newClient = new Client(newName, stylistId);
      newClient.save();
      model.put("stylist", Stylist.find(stylistId));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/:clientId", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int clientId = Integer.parseInt(request.params(":clientId"));
      int stylistId = Integer.parseInt(request.params(":id"));
      model.put("stylist", Stylist.find(stylistId));
      model.put("client", Client.findById(clientId));
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/update",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int stylistId = Integer.parseInt(request.params(":id"));
      String stylistName = request.queryParams("stylist-name");
      Stylist newStylist = new Stylist(stylistName);
      newStylist.updateStylistName(stylistName);
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("stylists/:id/clients/:clientId/update",  (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int stylistId = Integer.parseInt(request.params(":id"));
      int clientId = Integer.parseInt(request.params(":clientId"));
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("stylist-id")));
      String clientName = request.queryParams("client-name");
      Client newClient = new Client(clientName, stylist.getId());
      newClient.updateClientName(clientName);
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/appointments/:aId/update", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      int appointmentId = Integer.parseInt(request.params(":aId"));
      String date = request.queryParams("date");
      String time = request.queryParams("time");
      Client client = Client.findById(Integer.parseInt(request.queryParams("client-id")));
      Appointment newAppointment = new Appointment(date, time, client.getId());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients/:clientId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int stylistId = Integer.parseInt(request.params(":id"));
      Client clientId = Client.findById(Integer.parseInt(request.params(":clientId")));
      clientId.delete();
      model.put("stylist", Stylist.find(stylistId));
      model.put("template", "templates/clients.vtl");
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
