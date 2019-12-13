package com.example.backend.models.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "privilegios")
public class Privilegio {
	@Id
	private ObjectId id;
	private String nombre;
	
	public String getid() {
		return id.toHexString();
	}

	public void setid(ObjectId id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
