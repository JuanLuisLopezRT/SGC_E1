package com.example.backend.models.entity;

import java.util.Date;

import javax.validation.constraints.Email;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "usuarios")
public class Usuario {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String dni;

    private String password;

    private String tipo;

    private String nombre;

    private String apellidos;

    private String direccion;

    @Indexed(unique = true)
    private String telefono;

    @Indexed(unique = true)
    private String email;

    private String sexo;

    private String localidad;

    private String centroMedico;

    private String medico;

    private Date fechaNacimiento;

    public Usuario() {
    	
    }
    
    public Usuario(String dni, String password, String tipo, String nombre, String apellidos,
			String direccion, String telefono, String email, String sexo, String localidad, String centroMedico,
			String medico, Date fechaNacimiento) {
		super();
		this.dni = dni;
		this.password = password;
		this.tipo = tipo;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.sexo = sexo;
		this.localidad = localidad;
		this.centroMedico = centroMedico;
		this.medico = medico;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getid() {
	return id.toHexString();
    }

    public void setid(ObjectId id) {
	this.id = id;
    }

    public String getDni() {
	return dni;
    }

    public void setDni(String dni) {
	this.dni = dni;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getTipo() {
	return tipo;
    }

    public void setTipo(String tipo) {
	this.tipo = tipo;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellidos() {
	return apellidos;
    }

    public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
    }

    public String getDireccion() {
	return direccion;
    }

    public void setDireccion(String direccion) {
	this.direccion = direccion;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getSexo() {
	return sexo;
    }

    public void setSexo(String sexo) {
	this.sexo = sexo;
    }

    public String getLocalidad() {
	return localidad;
    }

    public void setLocalidad(String localidad) {
	this.localidad = localidad;
    }

    public String getCentroMedico() {
	return centroMedico;
    }

    public void setCentroMedico(String centroMedico) {
	this.centroMedico = centroMedico;
    }

    public String getMedico() {
	return medico;
    }

    public void setMedico(String medico) {
	this.medico = medico;
    }


    public Date getFechaNacimiento() {
	return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
	this.fechaNacimiento = fechaNacimiento;
    }

}
