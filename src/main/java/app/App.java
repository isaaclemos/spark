package app;

import java.util.HashMap;
import java.util.Map;

import controller.GrupoController;
import controller.JogadorController;
import controller.PosicaoController;
import controller.TimeController;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

	public static void main(String[] args) {
       
		Spark.port(getHerokuAssignedPort());
        
        
		Spark.before((request, response) -> {
			String usuario = "isaac";
			String senha = "123456";

			if (request.queryParams("usuario") != null && request.queryParams("senha") != null) {
				request.session().attribute("usuario", request.queryParams("usuario"));
				request.session().attribute("senha", request.queryParams("senha"));
			}

			String usuarioLogin = request.session().attribute("usuario");
			String senhaLogin = request.session().attribute("senha");

			if (usuarioLogin == null || senhaLogin == null || !usuarioLogin.equals(usuario)
					|| !senhaLogin.equals(senha)) {
				Map<String, Object> model = new HashMap<>();
				Spark.halt(new VelocityTemplateEngine().render(new ModelAndView(model, "view/login.vm")));
			}
		});

		Spark.get("https://sparkweb2.herokuapp.com/sair", (request, response) -> {
			request.session().invalidate();
			response.redirect("/grupo");
			return "";
		});
		
		Spark.get("/", (request, response) -> {
			response.redirect("/grupo");
			return "";
		});
		
		Spark.get("/login", (request, response) -> {
			response.redirect("/");
			return "";
		});

		new GrupoController();
		new PosicaoController();
		new TimeController();
		new JogadorController();
	}
	 static int getHerokuAssignedPort() {
	        ProcessBuilder processBuilder = new ProcessBuilder();
	        if (processBuilder.environment().get("PORT") != null) {
	            return Integer.parseInt(processBuilder.environment().get("PORT"));
	        }
	        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
	    }
}
