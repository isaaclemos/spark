package model;

import java.io.Serializable;
import javax.persistence.*;

import modelDB.ActiveRecord;


/**
 * The persistent class for the jogador database table.
 * 
 */
@Entity
@NamedQuery(name="Jogador.findAll", query="SELECT j FROM Jogador j")
public class Jogador extends ActiveRecord<Jogador> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer idade;

	private String nome;

	//bi-directional many-to-one association to Posicao
	@ManyToOne
	@JoinColumn(name="id_posicao")
	private Posicao posicao;

	//bi-directional many-to-one association to Time
	@ManyToOne
	@JoinColumn(name="id_time")
	private Time time;

	public Jogador() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdade() {
		return this.idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Posicao getPosicao() {
		return this.posicao;
	}

	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

}