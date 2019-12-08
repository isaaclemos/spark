package model;

import java.io.Serializable;
import javax.persistence.*;

import modelDB.ActiveRecord;

import java.util.List;


/**
 * The persistent class for the posicao database table.
 * 
 */
@Entity
@NamedQuery(name="Posicao.findAll", query="SELECT p FROM Posicao p")
public class Posicao extends ActiveRecord<Posicao> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	//bi-directional many-to-one association to Jogador
	@OneToMany(mappedBy="posicao")
	private List<Jogador> jogadores;

	public Posicao() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Jogador> getJogadores() {
		return this.jogadores;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public Jogador addJogadore(Jogador jogadore) {
		getJogadores().add(jogadore);
		jogadore.setPosicao(this);

		return jogadore;
	}

	public Jogador removeJogadore(Jogador jogadore) {
		getJogadores().remove(jogadore);
		jogadore.setPosicao(null);

		return jogadore;
	}

}