package controllers;

import java.util.List;

import models.Cargo;
import play.mvc.*;

public class Cargos extends Controller {

	public static void retornaCargos() {
		List<Cargo> cargos = Cargo.findAll();
		
		renderJSON(cargos);
	}

	public static void save() {
		String nome = params.get("cargo");
		Cargo cargo = Cargo.find("byNome", nome).first();
		if(cargo == null){
			cargo = new Cargo(nome);
			cargo.save();
		}
		renderJSON(cargo);
	}

	public static void retornaCargo(Long id) {
		Cargo cargo = Cargo.findById(id);
		renderJSON(cargo);
	}

	public static void edit(Long id) {
		Cargo cargo = Cargo.findById(id);
		cargo.nome = params.get("cargo");
		cargo.save();
	}

	
}
