package controller;

import java.util.HashMap;
import java.util.Map;

import model.Posicao;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public final class PosicaoController {

	private String list = "view/posicao/list.vm";
	private String form = "view/posicao/form.vm";

	public PosicaoController() {
		list();
		formNew();
		formUpdate();
		save();
		delete();
	}

	private void delete() {
		Spark.get("/posicao/delete/:id", (request, response) -> {
			String id = request.params("id");
			Posicao posicao = new Posicao().findById(Integer.parseInt(id));
			posicao.delete();
			response.redirect("/posicao");
			return "ok";
		});		
	}

	private void list() {
		Spark.get("/posicao", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("posicoes", new Posicao().find());
			return new VelocityTemplateEngine().render(new ModelAndView(model, list));
		});
	}

	private void formNew() {
		Spark.get("/posicao/insert", (request, response) -> {
			Map<String, Object> model = new HashMap<>(); 
			model.put("text", "Adcionar uma poisção");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}
	
	private void formUpdate() {
		Spark.get("/posicao/update/:id", (request, response) -> {
			String id = request.params("id");
			Posicao posicao = new Posicao().findById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<>(); 
			model.put("posicao", posicao);
			model.put("text", "Atualizar uma Posicao");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}

	private void save() {
		Spark.post("/posicao/save", (request, response) -> {
			String id = request.queryParams("id");
			String nome = request.queryParams("nome");
			

			Posicao posicao = new Posicao();
			posicao.setNome(nome);

			if (id != null && !id.equals("")) {
				posicao.setId(Integer.parseInt(id));
				posicao.savIt();
			} else {
				posicao.creatIt();
			}
			response.redirect("/posicao");
			return "ok";
		});

	}
}
