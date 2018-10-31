package dao.utilisateur;

import dao.compte.CompteEntity;
import dao.compte.comptejoint.CompteJointEntity;
import dao.message.MessageEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
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
    
    @Column
    private String motDePasse;
    
    @Column
    private String nom;
    
    @Column
    private String prenom;
    
    @Column(columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="proprietaire")
    private List<CompteEntity> comptes;
    
    @ManyToMany
    private List<CompteJointEntity> comptes_joints;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="userTo")
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
    
    public List<CompteEntity> getAllAccounts(){
        List<CompteEntity> result = new ArrayList<>(comptes);
        result.addAll(comptes_joints);
        result.sort(new Comparator<CompteEntity>(){
            @Override
            public int compare(CompteEntity o1, CompteEntity o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }
    
    public void addCompteJoint(CompteJointEntity compte){
        comptes_joints.add(compte);
    }
    
    public void removeCompteJoint(CompteJointEntity compte){
	if (this.comptes_joints.contains(compte)){
            this.comptes_joints.remove(compte);
        }
    }

    public void setComptes_joints(List<CompteJointEntity> comptes_joints) {
        this.comptes_joints = comptes_joints;
    }

    public List<CompteJointEntity> getComptes_joints() {
        return comptes_joints;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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
    
    @Override
    public String toString(){
        if (prenom.isEmpty() && nom.isEmpty()) return email;
        return email + " (" + prenom + " " + nom +")";
    }
}
