package com.example.backend.models.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "roles")
public class Rol {

	@Id
	private ObjectId id;
	private String nombre;
	private List<String> privilegios;

	public String getId() {
		return id.toHexString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<String> getPrivilegios() {
		return privilegios;
	}
	public void setPrivilegios(List<String> privilegios) {
		this.privilegios = privilegios;
	}
}
