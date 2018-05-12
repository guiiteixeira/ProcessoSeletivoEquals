package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Cargo;
import models.Usuario;

import play.Play;
import play.mvc.*;

public class Usuarios extends DefaultController {

    public static void listUsuarios() {
        
        List<Usuario> usuarios = Usuario.findAll();
        
        renderJSON(usuarios);        
    }
    
   public static void save(String nome, String cpf, String dataNascimento, String sexo, String cargo){
	   
	   Usuario user = Usuario.find("byCpf", cpf).first();
	   if(user == null){
		   user = new Usuario(nome,cpf,dataNascimento,sexo,cargo).save();
		   renderJSON(user);
	   } else {
		   renderErrorCPF("Já existe um usuário com este cpf");
	   }
	   
    }

	public static void delete(String cpf) {

		Usuario user = Usuario.find("byCpf", cpf).first();
		
		user.removido = true;
		user.save();
	}

	public static void findUsuario(Long id) {
		Usuario user = Usuario.findById(id);
		renderJSON(user);
	}

	public static void edit(Long id, String nome, String cpf, String dataNascimento, String cargo) {
	
		Usuario user = Usuario.findById(id);
		user.nome = nome;
		user.cpf = cpf;
		user.dataNascimento = dataNascimento;

		user.cargo = new Cargo(cargo);
		user.cargo = Cargo.find("byNome", cargo).first();
		if(user.cargo == null){
			user.cargo = new Cargo(cargo);
			user.cargo.save();
		}
		
		user.save();
		
	}

}
