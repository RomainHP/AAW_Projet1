package dao.compte.livret;

import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Livret")
public class LivretEntity extends CompteEntity implements Serializable {  
    
    private String nom;
        
    public LivretEntity(){
	this("",null);
    }
    
    public LivretEntity(UtilisateurEntity prop){
        super(prop);
        this.nom="default";
    }
    
    public LivretEntity(String nom, UtilisateurEntity prop){
        super(prop);
        this.nom=nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    @Override
    public String toString(){
        return this.nom;
    }
}
