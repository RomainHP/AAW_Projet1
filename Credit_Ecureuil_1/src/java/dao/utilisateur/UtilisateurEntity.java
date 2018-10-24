package dao.utilisateur;

import dao.compte.CompteEntity;
import dao.compte.livret.LivretEntity;
import dao.message.MessageEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rcharpen
 */
@Entity
@Table(name="Utilisateur")
@Inheritance(
    strategy = InheritanceType.JOINED
)
public class UtilisateurEntity implements Serializable {
    
    @Id
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;

    @OneToMany(mappedBy = "proprietaire")
    private List<CompteEntity> comptes;
    
    @OneToMany(mappedBy="userTo")
    private List<MessageEntity> messagesRecus;
    
    public UtilisateurEntity(){
        this("","","","");
    }
    
    public UtilisateurEntity(String id, String mdp){
        this(id,mdp,"","");
    }

    public UtilisateurEntity(String email, String password, String nom, String prenom) {
        this.email=email;
        this.motDePasse=password;
        this.nom=nom;
        this.prenom=prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    
    public List<CompteEntity> getComptes(){
	return this.comptes;
    }
    
    public void setEmail(String id){
	this.email = id;
    }
    
    public void setMotDePasse(String mdp){
	this.motDePasse = mdp;
    }
    
    public void setComptes(List<CompteEntity> comptes){
	this.comptes = comptes;
    }
    
    public void addAccount(CompteEntity compte){
	this.comptes.add(compte);
    }
    
    public void removeAccount(CompteEntity compte){
	if (this.comptes.contains(compte)){
            this.comptes.remove(compte);
        }
    }

    public List<MessageEntity> getMessagesRecus() {
        return messagesRecus;
    }

    public void setMessagesRecus(List<MessageEntity> messagesRecus) {
        this.messagesRecus = messagesRecus;
    }
    
    public void addMessageRecu(MessageEntity m){
        messagesRecus.add(m);
    }
    
    public void removeMessageRecu(MessageEntity m){
        messagesRecus.remove(m);
    }
    
    /**
     * Cherche dans la liste des comptes si un compte possède le nom demandé
     * @param name nom demandé
     * @return si la liste des comptes si un compte possède le nom demandé vrai sinon faux
     */
    public boolean hasAccountName(String name){
        for (CompteEntity ce : this.getComptes()){
            if (ce.toString().equals(name)) return true;
        }
        return false;
    }
    
}
