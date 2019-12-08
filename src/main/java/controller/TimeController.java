package controller;

import java.util.HashMap;
import java.util.Map;

import model.Grupo;
import model.Time;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public final class TimeController {

	private String list = "view/time/list.vm";
	private String form = "view/time/form.vm";
	private String mensagem = "";

	public TimeController() {
		list();
		formNew();
		formUpdate();
		save();
		delete();
		filterGrupo();
	}

	private void delete() {
		Spark.get("/time/delete/:id", (request, response) -> {
			String id = request.params("id");
			Time time = new Time().findById(Integer.parseInt(id));
			try {
				time.delete();				
			} catch (Exception e) {
				this.mensagem = "Existem jogadores no time";
			}
			response.redirect("/time");
			return "ok";
		});
	}

	private void list() {
		Spark.get("/time", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("times", new Time().find());
			model.put("mensagem", mensagem);
			this.mensagem = "";
			return new VelocityTemplateEngine().render(new ModelAndView(model, list));
		});
	}

	private void formNew() {
		Spark.get("/time/insert", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("grupos", new Grupo().find());
			model.put("text", "Adcionar um time");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}

	private void formUpdate() {
		Spark.get("/time/update/:id", (request, response) -> {
			String id = request.params("id");
			Time time = new Time().findById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<>();
			model.put("time", time);
			model.put("grupos", new Grupo().find());
			model.put("text", "Atualizar dados do time");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}
	
	private void filterGrupo() {
		Spark.get("/time/grupo/:id", (request, response) -> {
			String id = request.params("id");
			Grupo grupo = new Grupo().findById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<>();
			model.put("times", grupo.getTimes());
			model.put("text", "do grupo "+ grupo.getNome());
			return new VelocityTemplateEngine().render(new ModelAndView(model, list));
		});
	}

	private void save() {
		Spark.post("/time/save", (request, response) -> {
			String id = request.queryParams("id");
			String nome = request.queryParams("nome");
			String idGrupo = request.queryParams("idGrupo");

			int quantidade = new Time().find().size();
			Grupo grupo = new Grupo().findById(Integer.parseInt(idGrupo));

			if (quantidade == 12 || grupo.getTimes().size() == 4) {
				
				this.mensagem = "Numero maximo de times";

			} else {
				Time time = new Time();
				time.setNome(nome);
				time.setGrupo(grupo);

				if (id != null && !id.equals("")) {
					time.setId(Integer.parseInt(id));
					time.savIt();
				} else {
					time.creatIt();
				}

			}

			response.redirect("/time");
			return "ok";
		});

	}
}
