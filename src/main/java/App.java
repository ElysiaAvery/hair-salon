import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import java.util.ArrayList;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("stylists/:id/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
      String name = request.queryParams("name");
      String phone = request.queryParams("phone");
      String notes = request.queryParams("notes");
      Client newClient = new Client(name, phone, notes, stylist.getId());
      newClient.save();
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylist-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("stylist-name");
      String specialty = request.queryParams("specialty");
      Stylist newStylist = new Stylist(name, specialty);
      newStylist.save();
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(client.getStylistId());
      client.delete();
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      String name = request.queryParams("name");
      String specialty = request.queryParams("specialty");
      stylist.updateStylistName(name);
      stylist.updateStylistSpecialty(specialty);
      String url = String.format("/stylists/%d", stylist.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      stylist.delete();
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      String name = request.queryParams("name");
      String phone = request.queryParams("phone");
      String notes = request.queryParams("notes");
      Stylist stylist = Stylist.find(client.getStylistId());
      client.updateClientName(name);
      client.updateClientPhone(phone);
      client.updateClientNotes(notes);
      String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}


//     get("/", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       model.put("clients", Client.all());
//       model.put("stylists", Stylist.all());
//       model.put("template", "templates/index.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/stylists", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       model.put("stylists", Stylist.all());
//       model.put("template", "templates/stylists.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     post("/stylists", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       String name = request.queryParams("stylist-name");
//       String specialty = request.queryParams("specialty");
//       Stylist newStylist = new Stylist(name, specialty);
//       newStylist.save();
//       model.put("stylists", newStylist);
//       model.put("template", "templates/stylists.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/stylists/:id", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
//       Client client = Client.find(Integer.parseInt(request.params(":id")));
//       model.put("stylist", stylist);
//       model.put("client", client);
//       model.put("template", "templates/stylist.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/clients", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       model.put("clients", Client.all());
//       model.put("template", "templates/clients.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     post("/clients", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
//       String name = request.queryParams("name");
//       String phone = request.queryParams("phone");
//       String notes = request.queryParams("notes");
//       Client newClient = new Client(name, phone, notes, stylist.getId());
//       newClient.save();
//       model.put("client", newClient);
//       model.put("stylist", stylist);
//       model.put("template", "templates/clients.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//
//     get("/clients/:id", (request, response) -> {
//       Map<String, Object> model = new HashMap<>();
//       Client client = Client.find(Integer.parseInt(request.params(":id")));
//       model.put("client", client);
//       model.put("template", "templates/client.vtl");
//       return new ModelAndView(model, layout);
//     }, new VelocityTemplateEngine());
//   }
// }
