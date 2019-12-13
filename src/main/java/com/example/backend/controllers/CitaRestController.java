package com.example.backend.controllers;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.dao.IEspecialidadDAO;
import com.example.backend.models.dao.IPacienteMedicoDAO;
import com.example.backend.models.entity.Cita;
import com.example.backend.models.entity.Especialidad;
import com.example.backend.models.entity.Horario;
import com.example.backend.models.entity.Medico;
import com.example.backend.models.entity.PacienteMedico;
import com.example.backend.models.entity.Usuario;
import com.example.backend.models.services.ICitaService;
import com.example.backend.models.services.IEspecialidadService;
import com.example.backend.models.services.IHorarioService;
import com.example.backend.models.services.IUsuarioService;
import com.example.backend.models.utiles.Encriptador;

//@CrossOrigin(value = "https://sgcequipo1.herokuapp.com") 
@CrossOrigin("*") // PARA DESARROLLO
@RestController
@RequestMapping("api")
public class CitaRestController {
	
	@Autowired
	private IPacienteMedicoDAO pacienteMedicoRepo;
	
	@Autowired
	private ICitaService citaService;
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IHorarioService horarioService;
	

	private SecretKey key;
	private Cipher cipher;
	private String algoritmo = "AES";
	private int keysize = 16;
	private String clave = "seguridad";
	

	private Encriptador encriptador = new Encriptador(key, cipher, algoritmo, keysize, clave);

	@Autowired 
	private IEspecialidadDAO especialidadService;
	
	/**
	 * obtener todas las citas
	 * @return citas
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/citas")
	public List<Cita> getAllCitas() throws UnsupportedEncodingException {
		List<Cita> listaCitas = citaService.findAll();
		List<Cita> listaCitasDesencriptadas = new ArrayList<>();
		for (int i = 0; i < listaCitas.size(); i++) {
			listaCitasDesencriptadas.add(encriptador.desencriptarCita(listaCitas.get(i)));
		}
		return listaCitasDesencriptadas;
	}

	/**
	 * obtener las citas del usuario en concreto
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/citas/fecha/{id}")
	public Cita getCitaByid(@PathVariable("id") String id) {
		return citaService.findCitaById(id);
	}

	/**
	 * obtener las citas del usuario en concreto
	 * 
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/citas/paciente/{dni}")
	public List<Cita> getCitasPacienteByid(@PathVariable("dni") String dni) throws UnsupportedEncodingException {
		List<Cita> listaCitas = new ArrayList<Cita>();
		dni = encriptador.encriptarDni(dni);
		listaCitas = citaService.findCitasByDniPaciente(dni);
		for (Cita cita : listaCitas) {
			encriptador.desencriptarCita(cita);
		}
		return listaCitas;
	}

	@GetMapping("/citas/medico/{dni}")
	public List<Cita> getCitasMedicoByid(@PathVariable("dni") String dni) {
		return citaService.findCitasByDniMedico(dni);
	}

	/**
	 * add citas a un usuario
	 * 
	 * @param cita
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/citas")
	public Cita addCita(@Valid @RequestBody Cita cita) throws UnsupportedEncodingException {
		cita.setid(ObjectId.get());
		encriptador.encriptarCita(cita);
		citaService.saveCita(cita);
		return cita;
	}

	/**
	 * borra citas a un usuario
	 * 
	 * @param cita
	 */
	@DeleteMapping("/citas/{id}")
	public void deleteCita(@PathVariable("id") String id) {
		citaService.deleteCita(id);
	}

	/**
	 * Modificar la fecha de la cita
	 * 
	 * @param teléfono
	 * @param modificarDatosContacto
	 * @throws UnsupportedEncodingException
	 */
	@PutMapping("/citas/{id}")
	public Cita modificarFechaCita(@PathVariable("id") ObjectId id, @Valid @RequestBody Cita cita)
			throws UnsupportedEncodingException {
		cita.setid(id);
		cita = encriptador.encriptarCita(cita);
		citaService.saveCita(cita);
		return cita;

	}

	/*
	 * //RespuestaHuecos?? List<String/int/date> getHuecos(dniMedico, dia, mes,
	 * año)//fecha separada o completa{ List huecosLibres;
	 * medico=medicoService.findByDni(dniMedico) //si se pone la duración de la cita
	 * en Medico o en especialidad
	 * 
	 * horario=horarioService.findByDniAnd....(dniMedico, etc...)
	 * duracionCita=horario.getDuracionCita() String[]/ArrayList<Date>
	 * listaHoras=horario.getListaCitas()
	 * 
	 * for(i=hora_minima; i<=hora_maxima-duracioncita; i+=duracionCita){
	 * if(!listaCitas.contains(i)){ huecosLibres.add(i); } }
	 * 
	 * return huecosLibres;
	 * 
	 * }
	 * 
	 * 
	 */
	
	///Comprobar getMapping
	@GetMapping("/citas/huecoslibres/{dniMedico}")
	public ArrayList<String> getHuecos(@PathVariable("dniMedico") String dniMedico, int dia, int mes, int ano) {

		ArrayList<String> listaHuecosLibres = new ArrayList<String>();
		Medico medico = usuarioService.findMedicoByDni(dniMedico);
		Horario horario = horarioService.findHorarioByDnimedicoAndDiaAndMesAndAno(dniMedico, dia, mes, ano);
		Especialidad especialidad=especialidadService.findByNombre(medico.getEspecialidad());
		int duracionCita = especialidad.getDuracionCita();
		
		ArrayList<Date> listaCitas = (ArrayList<Date>) horario.getListaCitas();
		
		LocalTime horaInicio = LocalTime.of(7, 00);
		LocalTime horaFin = LocalTime.of(13, 00);	
		horaFin = horaFin.minus(duracionCita, ChronoUnit.MINUTES);
		
		for (LocalTime i = horaInicio; i.equals(horaFin); i.plus(duracionCita, ChronoUnit.MINUTES)) {
			for(int j=0; j<listaCitas.size(); j++) {
		    	if(!((listaCitas.get(j).getHours()==i.getHour()) && (listaCitas.get(j).getMinutes()==i.getMinute()))) {
		    		listaHuecosLibres.add(i.toString());	    		
		    	}
		    }
		}
		return listaHuecosLibres;
	}

	  
    
    /**
     * Obtener todas las especialidades
     * @return especialidades
     */
    @GetMapping("/citas/especialidades/{dniPaciente}")
	public List<Especialidad> getAllEspecialidades(@PathVariable ("dniPaciente") String dniPaciente) {
    	List<PacienteMedico> pacMed = pacienteMedicoRepo.findByPaciente(dniPaciente);
    	List<Especialidad> lista = new ArrayList<>();
    	for(int i = 0; i < pacMed.size(); i++) {
    		lista.add(especialidadService.findByNombre(pacMed.get(i).getEspecialidad()));
    	}
		return lista;
	}
	
	/**
     * obtener los medicos de una especialidad
     * @param id
     * @return
	 * @throws Exception 
     */
	@GetMapping("/citas/especialidades/{nombreEspecialidad}/{dni}")
	public String[] getEspecialidadesByid(@PathVariable ("nombreEspecialidad") String nombreEspecialidad, @PathVariable ("dni") String dniPaciente) throws Exception{
		PacienteMedico pacMed = pacienteMedicoRepo.findByPacienteEspecialidad(dniPaciente, nombreEspecialidad);
		Usuario med = usuarioService.findUserByDni(encriptador.encriptar(pacMed.getDniMedico()));
		String[] lista = new String[1];
		lista[0] = encriptador.desencriptar(med.getDni());
		return lista;
	}
	
}
