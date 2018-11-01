package dao.utilisateur.admin;

import dao.utilisateur.UtilisateurEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author romain
 */
@Entity
@Table(name="Admin")
public class AdminEntity extends UtilisateurEntity {
    
    public AdminEntity(){
        super();
    }
    
    public AdminEntity(String id, String mdp){
        super(id,mdp);
    }
    
    @Override
    public String toString(){
        return "ADMIN - " + super.toString();
    }
}
