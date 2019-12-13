package com.example.backend.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="horarios")
public class Horario {

	@Id
	private ObjectId id;
	private String dniMedico;
	private int dia;
	private int mes;
	private int ano;
	private List<Date> listaCitas = new ArrayList<Date>();
	
	public String getid() {
		return id.toHexString();
	}

	public void setid(ObjectId id) {
		this.id = id;
	}

	public String getDniMedico() {
		return dniMedico;
	}

	public void setDniMedico(String dniMedico) {
		this.dniMedico = dniMedico;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public List<Date> getListaCitas() {
		return listaCitas;
	}

	public void setListaCitas(List<Date> listaCitas) {
		this.listaCitas =  listaCitas;
	}

}