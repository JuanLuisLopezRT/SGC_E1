package com.example.backend.models.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "medicos")
public class Medico extends Usuario {

    private String especialidad;
    private String horaI;
    private String horaF;
    //calendario
    
	public Medico() {
	}

	public Medico(String dni, String password, String tipo, String nombre, String apellidos,
			String direccion, String telefono, String email, String sexo, String localidad, String centroMedico,
			String medico, Date fechaNacimiento, String especialidad, String horaI, String horaF) {
		super(dni, password, tipo, nombre, apellidos, direccion, telefono, email, sexo, localidad, centroMedico, medico,
				fechaNacimiento);
		this.especialidad = especialidad;
		this.horaI = horaI;
		this.horaF = horaF;
	}


	public String getHoraI() {
		return horaI;
	}

	public void setHoraI(String horaI) {
		this.horaI = horaI;
	}

	public String getHoraF() {
		return horaF;
	}

	public void setHoraF(String horaF) {
		this.horaF = horaF;
	}

	public String getEspecialidad() {
	return especialidad;
    }

    public void setEspecialidad(String especialidad) {
	this.especialidad = especialidad;
    }

   
}
