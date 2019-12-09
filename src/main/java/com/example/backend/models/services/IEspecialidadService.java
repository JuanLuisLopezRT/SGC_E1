package com.example.backend.models.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.mongodb.repository.Query;

import com.example.backend.models.entity.Especialidad;

public interface IEspecialidadService {
	
	public List<Especialidad> findAll();
	public Especialidad findEspecialidadById(String id);
	public Especialidad findEspecialidadByNombre(String nombre);

}
