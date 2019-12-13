package com.example.backend.models.entity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "especialidades")
public class Especialidad {

    @Id
    private ObjectId id;
    private String nombre;
    private int duracionCita;
    private String[] listaMedicos;

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

    public int getDuracionCita() {
        return duracionCita;
    }

    public void setDuracionCita(int duracionCita) {
        this.duracionCita = duracionCita;
    }

    public String[] getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(String[] listaMedicos) {
        this.listaMedicos = listaMedicos;
    }


}
