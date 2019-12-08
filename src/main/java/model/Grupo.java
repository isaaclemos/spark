package model;

import java.io.Serializable;
import javax.persistence.*;

import modelDB.ActiveRecord;

import java.util.List;


/**
 * The persistent class for the grupo database table.
 * 
 */
@Entity
@NamedQuery(name="Grupo.findAll", query="SELECT g FROM Grupo g")
public class Grupo extends ActiveRecord<Grupo> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String cidade;

	private String nome;

	//bi-directional many-to-one association to Time
	@OneToMany(mappedBy="grupo")
	private List<Time> times;

	public Grupo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Time> getTimes() {
		return this.times;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}

	public Time addTime(Time time) {
		getTimes().add(time);
		time.setGrupo(this);

		return time;
	}

	public Time removeTime(Time time) {
		getTimes().remove(time);
		time.setGrupo(null);

		return time;
	}

}