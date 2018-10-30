package services.compte;

import dao.compte.CompteEntity;
import dao.transaction.TransactionDao;
import dao.transaction.TransactionEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import dao.compte.CompteDao;
import dao.compte.comptejoint.CompteJointDao;
import dao.compte.comptejoint.CompteJointEntity;
import dao.compte.livret.LivretDao;
import dao.compte.livret.LivretEntity;
import exceptions.ServiceException;

@Service
public class CompteServiceImpl implements CompteService{
   
    @Resource
    CompteDao dao;
    
    @Resource
    LivretDao livretDao;
    
    @Resource
    CompteJointDao compteJointDao;
    
    @Resource
    UtilisateurDao userDao;
    
    @Resource 
    TransactionDao transactionDao;
    
    @Override
    public void virement(Long src, Long dest, Double montant) throws ServiceException {
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if (cesrc==null) throw new ServiceException("Compte source introuvable.");
	if (cedst==null) throw new ServiceException("Compte destinataire introuvable.");
        if (montant<=0d) throw new ServiceException("Montant invalide (inférieur ou égal à 0).");
        if (cesrc.getSolde()<montant) throw new ServiceException("Solde du compte source insuffisant.");
        TransactionEntity te = new TransactionEntity(cesrc, cedst, montant);
        cedst.setSolde(cedst.getSolde() + montant);
        cedst.getTransactions_in().add(te);
        dao.update(cedst);
        cesrc.setSolde(cesrc.getSolde() - montant);
        cesrc.getTransactions_out().add(te);
        dao.update(cesrc);
    }

    @Override
    public List<CompteEntity> consultation(String username) {
        UtilisateurEntity ant = userDao.find(username);
        if (ant!=null){
            return ant.getAllAccounts();
        }
        return new ArrayList<>();
    }
    
    @Override
    public void ajoutLivret(String nomCompte, String nomUtilisateur) throws ServiceException {
	UtilisateurEntity ue = userDao.find(nomUtilisateur);
        if (ue==null) throw new ServiceException("Utilisateur introuvable.");
        if (ue.hasAccountName(nomCompte)) throw new ServiceException("Nom de livret déjà pris.");
        LivretEntity ce = new LivretEntity(nomCompte, ue);
        ue.addAccount(ce);
        userDao.update(ue);
    }

    @Override
    public void supprimerLivret(Long id) throws ServiceException {
	LivretEntity ce = livretDao.find(id);
	if(ce == null) throw new ServiceException("Livret introuvable.");
        if(ce.getSolde()!=0d) throw new ServiceException("Le solde du livret à supprimer n'est pas nul (0).");
        List<TransactionEntity> tmp = new ArrayList<>(ce.getTransactions_in());
        for (TransactionEntity transaction : tmp){
            ce.getTransactions_in().remove(transaction);
            livretDao.update(ce);
            transactionDao.remove(transaction);
        }
        tmp = new ArrayList<>(ce.getTransactions_out());
        for (TransactionEntity transaction : tmp){
            ce.getTransactions_out().remove(transaction);
            livretDao.update(ce);
            transactionDao.remove(transaction);
        }
        ce.getProprietaire().removeAccount(ce);
        userDao.update(ce.getProprietaire());
        livretDao.remove(ce);
    }
    
    @Override
    public void ajoutCompteJoint(String nomCompte, String nomUtilisateur, List<String> co_proprietaires) throws ServiceException {
	if (co_proprietaires.isEmpty()) throw new ServiceException("Pas de co-propriétaires.");
        UtilisateurEntity ue = userDao.find(nomUtilisateur);
        if (ue==null) throw new ServiceException("Utilisateur introuvable.");
        // On recherche les co proprietaires dans la base
        List<UtilisateurEntity> users = new ArrayList<>();
        for (String prop : co_proprietaires){
            UtilisateurEntity user = userDao.find(prop);
            if (user==null) throw new ServiceException("Utilisateur introuvable.");
            users.add(user);
        }
        if (ue.hasAccountName(nomCompte)) throw new ServiceException("Nom de compte déjà pris.");
        CompteJointEntity ce = new CompteJointEntity(nomCompte, ue, users);
        for (UtilisateurEntity user : users){
            user.addCompteJoint(ce);
            userDao.update(user);
        }
        ue.addAccount(ce);
        userDao.update(ue);
    }

    @Override
    public void supprimerCompteJoint(Long id) throws ServiceException {
	CompteJointEntity ce = compteJointDao.find(id);
	if(ce == null) throw new ServiceException("Compte joint introuvable.");
        if(ce.getSolde()!=0d) throw new ServiceException("Le solde du compte à supprimer n'est pas nul (0).");
        for (UtilisateurEntity user : ce.getCo_proprietaires()){
            user.removeCompteJoint(ce);
            userDao.update(user);
        }
        ce.getProprietaire().removeAccount(ce);
        userDao.update(ce.getProprietaire());
        compteJointDao.remove(ce);
    }

    @Override
    public CompteEntity getAccount(Long id) {
	return dao.find(id);
    }
}
