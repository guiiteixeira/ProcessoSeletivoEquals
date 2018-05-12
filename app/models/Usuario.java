package models;

import play.*;
import play.data.validation.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.text.DateFormat;
import java.util.*;

@Entity
public class Usuario extends Model {
    
	@Required
	public String nome;
    
    @Required
    @Unique
    public String cpf;
    
    public String dataNascimento;
    public String sexo;
    
    @ManyToOne
    public Cargo cargo;
    
    public boolean removido;
    
    public Usuario (String nome, String cpf, String dataNascimento, String sexo, String cargo){
    	this.nome = nome;
    	this.cpf = cpf;
    	this.sexo = sexo;
    	this.cargo = Cargo.find("byNome", cargo).first();
		if(this.cargo == null){
			this.cargo = new Cargo(cargo).save();
		}
    	this.dataNascimento = dataNascimento;
    	this.removido= false;
    	
    }
}


