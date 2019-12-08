package model;

import java.io.Serializable;
import javax.persistence.*;

import modelDB.ActiveRecord;

import java.util.List;


/**
 * The persistent class for the time database table.
 * 
 */
@Entity
@NamedQuery(name="Time.findAll", query="SELECT t FROM Time t")
public class Time extends ActiveRecord<Time> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	//bi-directional many-to-one association to Jogador
	@OneToMany(mappedBy="time")
	private List<Jogador> jogadores;

	//bi-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name="id_grupo")
	private Grupo grupo;

	public Time() {
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
		jogadore.setTime(this);

		return jogadore;
	}

	public Jogador removeJogadore(Jogador jogadore) {
		getJogadores().remove(jogadore);
		jogadore.setTime(null);

		return jogadore;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}