package models;

import play.*;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cargo extends Model {
    
	@Required
	public String nome;
    
    public Cargo (String nome){
    	this.nome = nome;
    }
}
