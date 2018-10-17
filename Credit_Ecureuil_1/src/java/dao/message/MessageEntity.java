package dao.message;

import dao.utilisateur.UtilisateurEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Romain
 */
@Entity
public class MessageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //@OneToMany
    private UtilisateurEntity userFrom;
    
    //@OneToMany
    private UtilisateurEntity userTo;
   
    private String message;
    
    public MessageEntity(){
        this(null,null,"");
    }
    
    public MessageEntity(UtilisateurEntity from, UtilisateurEntity to, String msg){
        userFrom = from;
        userTo = to;
        message = msg;
    }

    public Long getId() {
        return id;
    }

    public UtilisateurEntity getUserFrom() {
        return userFrom;
    }

    public UtilisateurEntity getUserTo() {
        return userTo;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserFrom(UtilisateurEntity userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(UtilisateurEntity userTo) {
        this.userTo = userTo;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
