package com.example.backend.models.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "pacienteMedico")
public class PacienteMedico {

    
	private String dniPaciente;
	private String dniMedico;
	private String especialidad;
	
	public PacienteMedico(String dniPaciente, String dniMedico, String especialidad) {
		super();
		this.dniPaciente = dniPaciente;
		this.dniMedico = dniMedico;
		this.especialidad = especialidad;
	}

	public String getDniPaciente() {
		return dniPaciente;
	}

	public void setDniPaciente(String dniPaciente) {
		this.dniPaciente = dniPaciente;
	}

	public String getDniMedico() {
		return dniMedico;
	}

	public void setDniMedico(String dniMedico) {
		this.dniMedico = dniMedico;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	
}
