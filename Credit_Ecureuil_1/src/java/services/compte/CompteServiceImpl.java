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

    public void setLivretDao(LivretDao livretDao) {
        this.livretDao = livretDao;
    }

    public void setCompteJointDao(CompteJointDao compteJointDao) {
        this.compteJointDao = compteJointDao;
    }

    public LivretDao getLivretDao() {
        return livretDao;
    }

    public CompteJointDao getCompteJointDao() {
        return compteJointDao;
    }

    public void setDao(CompteDao dao) {
        this.dao = dao;
    }

    public void setUserDao(UtilisateurDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public CompteDao getDao() {
        return dao;
    }

    public UtilisateurDao getUserDao() {
        return userDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }
    
    @Override
    public void virement(Long src, Long dest, Double montant) throws ServiceException {
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if (cesrc==null) throw new ServiceException("Compte source introuvable.");
	if (cedst==null) throw new ServiceException("Compte destinataire introuvable.");
        if (montant<=0d) throw new ServiceException("Montant invalide (inférieur ou égal à 0).");
        if (cesrc.getSolde()<montant) throw new ServiceException("Solde du compte source insuffisant.");
        TransactionEntity te = new TransactionEntity(cesrc, cedst, montant);
        transactionDao.save(te);
        cedst.setSolde(cedst.getSolde() + montant);
        cesrc.setSolde(cesrc.getSolde() - montant);
        cesrc.getTransactions_out().add(te);
        cedst.getTransactions_in().add(te);
        dao.update(cedst);
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
        dao.save(ce);
        ue.addAccount(ce);
        userDao.update(ue);
    }

    @Override
    public void supprimerLivret(Long id) throws ServiceException {
	LivretEntity ce = livretDao.find(id);
	if(ce == null) throw new ServiceException("Livret introuvable.");
        if(ce.getSolde()!=0d) throw new ServiceException("Le solde du livret à supprimer n'est pas nul (0).");
        ce.getProprietaire().removeAccount(ce);
        userDao.update(ce.getProprietaire());
        livretDao.remove(ce);
    }
    
    @Override
    public void ajoutCompteJoint(String nomCompte, String nomUtilisateur, List<String> co_proprietaires) throws ServiceException {
	if (co_proprietaires.isEmpty()) throw new ServiceException("Pas de co-propriétaires.");
        UtilisateurEntity ue = userDao.find(nomUtilisateur);
        if (ue==null) throw new ServiceException("Utilisateur introuvable.");
        List<UtilisateurEntity> users = new ArrayList<>();
        for (String prop : co_proprietaires){
            UtilisateurEntity user = userDao.find(prop);
            if (user==null) throw new ServiceException("Utilisateur introuvable.");
            users.add(user);
        }
        if (ue.hasAccountName(nomCompte)) throw new ServiceException("Nom de compte déjà pris.");
        CompteJointEntity ce = new CompteJointEntity(nomCompte, ue, users);
        dao.save(ce);
        ue.addAccount(ce);
        userDao.update(ue);
        for (UtilisateurEntity user : users){
            user.addCompteJoint(ce);
            userDao.update(user);
        }
    }

    @Override
    public void supprimerCompteJoint(Long id) throws ServiceException {
	CompteJointEntity ce = compteJointDao.find(id);
	if(ce == null) throw new ServiceException("Compte joint introuvable.");
        if(ce.getSolde()!=0d) throw new ServiceException("Le solde du compte à supprimer n'est pas nul (0).");
        ce.getProprietaire().removeAccount(ce);
        userDao.update(ce.getProprietaire());
        for (UtilisateurEntity user : ce.getCo_proprietaires()){
            user.removeCompteJoint(ce);
            userDao.update(user);
        }
        compteJointDao.remove(ce);
    }

    @Override
    public CompteEntity getAccount(Long id) {
	return dao.find(id);
    }
}
