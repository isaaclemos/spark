package controller;

import java.util.HashMap;
import java.util.Map;

import model.Jogador;
import model.Posicao;
import model.Time;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public final class JogadorController {

	private String list = "view/jogador/list.vm";
	private String form = "view/jogador/form.vm";
	private String mensagem="";

	public JogadorController() {
		list();
		formNew();
		formUpdate();
		save();
		delete();
		filterTime();
	}

	private void delete() {
		Spark.get("/jogador/delete/:id", (request, response) -> {
			try {
				String id = request.params("id");
				Jogador jogador = new Jogador().findById(Integer.parseInt(id));
				jogador.delete();
				response.redirect("/jogador");
				return "ok";
			} catch (Exception e) {
				return "Erro";
			}
		});
	}

	private void list() {
		Spark.get("/jogador", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("jogadores", new Jogador().find());
			model.put("mensagem", mensagem);
			return new VelocityTemplateEngine().render(new ModelAndView(model, list));
		});
	}

	private void formNew() {
		Spark.get("/jogador/insert", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("times", new Time().find());
			model.put("posicoes", new Posicao().find());
			model.put("text", "Adcionar um jogador");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}

	private void formUpdate() {
		Spark.get("/jogador/update/:id", (request, response) -> {
			String id = request.params("id");
			Jogador jogador = new Jogador().findById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<>();
			model.put("jogador", jogador);
			model.put("times", new Time().find());
			model.put("posicoes", new Posicao().find());
			model.put("text", "Atualizar jogador");
			return new VelocityTemplateEngine().render(new ModelAndView(model, form));
		});
	}
	
	private void filterTime() {
		Spark.get("/jogador/time/:id", (request, response) -> {
			String id = request.params("id");
			Time time = new Time().findById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<>();
			model.put("jogadores", time.getJogadores());
			model.put("text", "do time "+ time.getNome());
			return new VelocityTemplateEngine().render(new ModelAndView(model, list));
		});
	}

	private void save() {
		Spark.post("/jogador/save", (request, response) -> {
			String id = request.queryParams("id");
			String nome = request.queryParams("nome");
			String idade = request.queryParams("idade");
			String idTime = request.queryParams("idTime");
			String idPosicao = request.queryParams("idPosicao");
			try {
				Time time = new Time().findById(Integer.parseInt(idTime));
				if (time.getJogadores().size() == 23) {
					
					this.mensagem = "<script>alert('Numero maximo de jogadores')</script>";
					
				} else {

					Jogador jogador = new Jogador();
					jogador.setNome(nome);
					jogador.setIdade(Integer.parseInt(idade));

					jogador.setTime(time);
					jogador.setPosicao(new Posicao().findById(Integer.parseInt(idPosicao)));

					if (id != null && !id.equals("")) {
						jogador.setId(Integer.parseInt(id));
						jogador.savIt();
					} else {
						jogador.creatIt();
					}
				}
			} catch (Exception e) {
				this.mensagem = "<script>alert('Erro ao salvar jogador')</script>";
			} finally {
				response.redirect("/jogador");

			}
			return "";
		});

	}
}
