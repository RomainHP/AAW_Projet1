package dao.message;

import dao.utilisateur.UtilisateurEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Romain
 */
@Entity
@Table(name="Message")
public class MessageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne()
    @JoinColumn(name="messages_envoyes")
    private UtilisateurEntity userFrom = null;
    
    @ManyToOne()
    @JoinColumn(name="messages_recus")
    private UtilisateurEntity userTo = null;
   
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
