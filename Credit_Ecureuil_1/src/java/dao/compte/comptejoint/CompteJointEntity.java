package dao.compte.comptejoint;

import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="CompteJoint")
public class CompteJointEntity extends CompteEntity implements Serializable {  
    
    @ManyToMany
    private List<UtilisateurEntity> co_proprietaires;
    
    private String nom;
        
    public CompteJointEntity(){
	this("",null,new ArrayList<UtilisateurEntity>());
    }
    
    public CompteJointEntity(UtilisateurEntity prop, List<UtilisateurEntity> coProp){
        this("",prop,coProp);
    }
    
    public CompteJointEntity(String nom, UtilisateurEntity prop, List<UtilisateurEntity> coProp){
        super(prop);
        this.co_proprietaires=coProp;
        this.nom=nom;
    }

    public void setCo_proprietaires(List<UtilisateurEntity> co_proprietaires) {
        this.co_proprietaires = co_proprietaires;
    }

    public List<UtilisateurEntity> getCo_proprietaires() {
        return co_proprietaires;
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
