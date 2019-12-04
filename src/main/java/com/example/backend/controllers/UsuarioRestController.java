package com.example.backend.controllers;

import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.dao.IMedicoDAO;
import com.example.backend.models.dao.IPacienteMedicoDAO;
import com.example.backend.models.entity.Especialidad;
import com.example.backend.models.entity.Medico;
import com.example.backend.models.entity.PacienteMedico;
import com.example.backend.models.entity.Rol;
import com.example.backend.models.entity.Usuario;
import com.example.backend.models.respuesta.RespuestaLogin;
import com.example.backend.models.services.IEspecialidadService;
import com.example.backend.models.services.IRolService;
import com.example.backend.models.services.IUsuarioService;
import com.example.backend.models.utiles.Encriptador;

//@CrossOrigin(value = "https://sgcequipo1.herokuapp.com") 
@CrossOrigin(value = "*") // PARA DESARROLLO
@RestController
@RequestMapping("api")
public class UsuarioRestController {
	
	@Autowired
	private IPacienteMedicoDAO pacienteMedicoRepo;
	
	@Autowired
	private IMedicoDAO medicoRepo;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private IEspecialidadService especialidadService;

	private SecretKey key;
	private Cipher cipher;
	private String algoritmo = "AES";
	private int keysize = 16;
	private String clave = "seguridad";

	private Encriptador encriptador = new Encriptador(key, cipher, algoritmo, keysize, clave);

	@GetMapping("/usuarios")
	public List<Usuario> getAllUsers() throws UnsupportedEncodingException {
		List<Usuario> listaUsuarios = usuarioService.findAll();
		List<Usuario> listaUsuariosDesencriptados = new ArrayList<Usuario>();
		for (Usuario u : listaUsuarios) {
			u = encriptador.desencriptarUsuario(u);
			listaUsuariosDesencriptados.add(u);
		}
		return listaUsuariosDesencriptados;
	}

	@GetMapping("/usuarios/{id}")
	public Usuario getUserById(@PathVariable("id") String id) {
		return usuarioService.findUserById(id);
	}

	/**
	 * validar el login del usuario
	 * 
	 * @param dni
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/usuarios/{dni}/{password}")
	public RespuestaLogin validateLogin(@PathVariable("dni") String dni, @PathVariable("password") String password)
			throws UnsupportedEncodingException {
		RespuestaLogin respuestaLogin = new RespuestaLogin();
		List<Usuario> listaUsuarios = usuarioService.findAll();
		Rol rol = new Rol();

		dni = encriptador.encriptarDni(dni);
		password = encriptador.encriptarPassword(password);

		for (Usuario u : listaUsuarios) {
			if (u.getDni().equals(dni) && u.getPassword().equals(password)) {
				u = encriptador.desencriptarUsuario(u);
				rol = rolService.findRolByNombre(u.getTipo());
				respuestaLogin.setUsuario(u);
				respuestaLogin.setRol(rol);
				respuestaLogin.setLoginPasado(true);
				break;
			} else {
				respuestaLogin.setLoginPasado(false);
			}
		}
		return respuestaLogin;
	}

	/**
	 * registrar al usuario
	 * 
	 * @param usuario
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	// TODO Encriptar los dotos que faltan
	@PostMapping("/usuarios")
	public Usuario registrarUsuario(@Valid @RequestBody Usuario usuario) throws UnsupportedEncodingException {
		usuario.set_id(ObjectId.get());
		usuario = encriptador.encriptarUsuario(usuario);
		usuarioService.saveUser(usuario);
		return usuario;
	}
	
	@PostMapping("/eliminarPacienteMedico")
	public PacienteMedico eliminarPacienteMedico(@Valid @RequestBody Map<String, String> jso) throws Exception {
		String dniPaciente = jso.get("dniPaciente");
		String dniMedico = jso.get("dniMedico");
		PacienteMedico pacMed = pacienteMedicoRepo.findByCustom(dniPaciente, dniMedico);
		pacienteMedicoRepo.delete(pacMed);
		return pacMed;
	}
	
	@PostMapping("/pacienteMedico")
	public PacienteMedico crearPacienteMedico(@Valid @RequestBody Map<String, String> jso ) throws Exception {
		String dniPaciente = jso.get("dniPaciente");
		String dniMedico = jso.get("dniMedico");
		Medico med = medicoRepo.findByDni(dniMedico);
		String dniPaciente2 = encriptador.encriptar(dniPaciente);
		Usuario user = usuarioService.findUserByDni(dniPaciente2);
		if(med == null) {
			throw new Exception("El medico asignado no existe");
		}
		if(user == null) {	
			throw new Exception("El usuario asignado no existe");
		}
		PacienteMedico pacMed = new PacienteMedico(encriptador.desencriptar(dniPaciente2), dniMedico, med.getEspecialidad());
		pacienteMedicoRepo.insert(pacMed);
		return pacMed;
	}

	@PostMapping("/medico")
	public Medico registrarMedico(@Valid @RequestBody Map<String, String> jso) throws Exception {
		String dniMedico = jso.get("dniMedico");
		List<Usuario> listaUsuarios = getAllUsers();
		Usuario user = new Usuario();
		boolean encontrado = true;
		for(int i = 0; i < listaUsuarios.size(); i++) {
			if(dniMedico.equals(listaUsuarios.get(i).getDni())) {
				user = listaUsuarios.get(i);
				encontrado = false;
			}
		}
		if(encontrado) {
			throw new Exception("El usuario no existe");
		} else {
			String especialidad = jso.get("especialidad");
			Medico med = new Medico(dniMedico, user.getPassword(), user.getTipo(), user.getNombre(), user.getApellidos(), user.getDireccion(), user.getTelefono(), user.getEmail(), user.getSexo(), user.getLocalidad(), user.getCentroMedico(), user.getMedico(), user.getFechaNacimiento(), especialidad);
			medicoRepo.insert(med);
			return med;
		}
	}
	/**
	 * Modificar un usuario para hacerlo médico
	 * 
	 * @param
	 * @throws Exception
	 */
	@PutMapping("/usuarios/registrarMedico/{id}")
	public Medico registrarMedico(@PathVariable("id") ObjectId id, @Valid @RequestBody Medico medico)
			throws UnsupportedEncodingException {
		medico.set_id(id);
		medico.setTipo("MEDICO");
		medico.setEspecialidad(medico.getEspecialidad());
		medico = encriptador.encriptarMedico(medico);
		usuarioService.saveUser(medico);

		return encriptador.desencriptarMedico(medico);

	}

	/**
	 * Modificar la contraseña del usuario
	 * 
	 * @param password
	 * @throws UnsupportedEncodingException
	 */
	@PutMapping("/usuarios/password/{id}")
	public Usuario modificarPassword(@PathVariable("id") ObjectId id, @Valid @RequestBody Usuario usuario)
			throws UnsupportedEncodingException {
		usuario.set_id(id);
		usuario = encriptador.encriptarUsuario(usuario);
		usuario.setPassword(encriptador.encriptarPassword(usuario.getPassword()));
		usuarioService.saveUser(usuario);
		return usuario;

	}

	/**
	 * Modificar los datos de contacto del usuario
	 * 
	 * @param teléfono
	 * @param modificarDatosContacto
	 * @throws UnsupportedEncodingException
	 */
	@PutMapping("/usuarios/datosContacto/{id}")
	public Usuario modificarDatosContacto(@PathVariable("id") ObjectId id, @Valid @RequestBody Usuario usuario)
			throws UnsupportedEncodingException {
		usuario.set_id(id);
		usuario = encriptador.encriptarUsuario(usuario);
		usuarioService.saveUser(usuario);
		return usuario;
	}

	/**
	 * Modificar los datos de personales del usuario
	 * 
	 * @param modificarDatosPersonales
	 * @throws UnsupportedEncodingException
	 */
	@PutMapping("/usuarios/datospersonales/{id}")
	public Usuario modificarDatosPersonales(@PathVariable("id") ObjectId id, @Valid @RequestBody Usuario usuario)
			throws UnsupportedEncodingException {
		usuario = encriptador.encriptarUsuario(usuario);
		usuarioService.saveUser(usuario);
		return usuario;

	}
	
	@GetMapping("/especialidades")
	public List<Especialidad> getAllEspecialidades() {
		System.out.println();
		return especialidadService.findAll();
	}

}
