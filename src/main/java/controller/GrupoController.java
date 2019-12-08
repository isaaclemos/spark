package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Grupo;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public final class GrupoController {

	private String list = "view/grupo/list.vm";
	private String form = "view/grupo/form.vm";
	private String mensagem = "";

	public GrupoController() {
		list();
		formNew();
		formUpdate();
		save();
		delete();
	}

	private void delete() {
		Spark.get("/grupo/delete/:id", (request, response) -> {
			String id = request.params("id");
			Grupo grupo = new Grupo().findById(Integer.parseInt(id));
			try {
				grupo.delete();				
			} catch (Exception e) {
				this.mensagem = "Existem times nesse grupo";
			}
			response.redirect("/grupo");
			return "ok";
		});
	}

	private void list() {
		Spark.get("/grupo", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("grupos", new Grupo().find());
			model.put("mensagem", mensagem);
			this.mensagem ="";
			return new VelocityTemplateEngine().render(new ModelAndView(model, list));
		});
	}

	private void formNew() {
		Spark.get("/grupo/insert", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("text", "Adcionar um grupo");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}

	private void formUpdate() {
		Spark.get("/grupo/update/:id", (request, response) -> {
			String id = request.params("id");
			Grupo grupo = new Grupo().findById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<>();
			model.put("grupo", grupo);
			model.put("text", "Atualizar o grupo");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}
	

	private void save() {
		Spark.post("/grupo/save", (request, response) -> {
			List<Grupo> grupos = new Grupo().find();
			try {
				System.out.println(grupos.size());

				if (grupos.size() == 4) {
					this.mensagem = "Numero maximo de grupos";

				} else {
					String id = request.params("id");
					String nome = request.queryParams("nome");
					String cidade = request.queryParams("cidade");

					Grupo grupo = new Grupo();
					grupo.setNome(nome);
					grupo.setCidade(cidade);

					if (id != null && !id.equals("")) {
						grupo.setId(Integer.parseInt(id));
						grupo.savIt();
					} else {
						grupo.creatIt();
					}
				}
			} catch (Exception e) {
				this.mensagem = "Erro ao salvar grupo";
			} finally {
				response.redirect("/grupo");
			}
			return "";

		});

	}
}
