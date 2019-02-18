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
import java.util.LinkedHashSet;

@Service
public class CompteServiceImpl implements CompteService{
   
    @Resource
    CompteDao dao;
    
    @Resource
    LivretDao livretDao;
    
    @Resource
    CompteJointDao compteJointDao;
    
    @Resource 
    TransactionDao transactionDao;
    
    @Resource
    UtilisateurDao userDao;
    
    @Override
    public void virement(Long src, Long dest, Double montant, boolean testSolde) throws ServiceException {
        if (src==dest) throw new ServiceException("Comptes source et destinataire identiques.");
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if (cesrc==null) throw new ServiceException("Compte source introuvable.");
	if (cedst==null) throw new ServiceException("Compte destinataire introuvable.");
        if (cesrc.isCloture()) throw new ServiceException("Compte source cloture.");
        if (cedst.isCloture()) throw new ServiceException("Compte destinataire cloture.");
        if (montant<=0d) throw new ServiceException("Montant invalide (inferieur ou egal a 0).");
        if (testSolde && cesrc.getSolde()<montant) throw new ServiceException("Solde du compte source insuffisant.");
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
            return ant.getAllOpenAccounts();
        }
        return new ArrayList<>();
    }
    
    @Override
    public void ajoutLivret(String nomCompte, String nomUtilisateur) throws ServiceException {
	UtilisateurEntity ue = userDao.find(nomUtilisateur);
        if (ue==null) throw new ServiceException("Utilisateur introuvable.");
        if (ue.hasAccountName(nomCompte)){
            CompteEntity ce = ue.getAccount(nomCompte);
            if (ce!=null && ce.isCloture()){
                ce.setCloture(false);
                dao.update(ce);
                return;
            } else {
                throw new ServiceException("Nom de livret deja pris.");
            }
        }
        LivretEntity ce = new LivretEntity(nomCompte, ue);
        ue.addAccount(ce);
        userDao.update(ue);
    }

    @Override
    public void supprimerLivret(Long id, boolean testSolde) throws ServiceException {
	LivretEntity ce = livretDao.find(id);
	if(ce == null) throw new ServiceException("Livret introuvable.");
        if(testSolde && ce.getSolde()!=0d) throw new ServiceException("Le solde du livret a supprimer n'est pas nul (0).");
        ce.setCloture(true);
        livretDao.update(ce);
    }
    
    @Override
    public void ajoutCompteJoint(String nomCompte, String nomUtilisateur, LinkedHashSet<String> co_proprietaires) throws ServiceException {
	if (co_proprietaires.isEmpty()) throw new ServiceException("Pas de co-proprietaires.");
        if (co_proprietaires.contains(nomUtilisateur)) throw new ServiceException("Le proprietaire ne peut pas être egalement co-proprietaire.");
        UtilisateurEntity ue = userDao.find(nomUtilisateur);
        if (ue==null) throw new ServiceException("Utilisateur introuvable.");
        // On recherche les co proprietaires dans la base
        List<UtilisateurEntity> users = new ArrayList<>();
        for (String prop : co_proprietaires){
            UtilisateurEntity user = userDao.find(prop);
            if (user==null) throw new ServiceException("Utilisateur introuvable.");
            users.add(user);
        }
        if (ue.hasAccountName(nomCompte)) throw new ServiceException("Nom de compte deja pris.");
        CompteJointEntity ce = new CompteJointEntity(nomCompte, ue, users);
        for (UtilisateurEntity user : users){
            user.addCompteJoint(ce);
            userDao.update(user);
        }
        ue.addAccount(ce);
        userDao.update(ue);
    }

    @Override
    public void supprimerCompteJoint(Long id, boolean testSolde) throws ServiceException {
	CompteJointEntity ce = compteJointDao.find(id);
	if(ce == null) throw new ServiceException("Compte joint introuvable.");
        if(testSolde && ce.getSolde()!=0d) throw new ServiceException("Le solde du compte a supprimer n'est pas nul (0).");
        ce.setCloture(true);
        compteJointDao.update(ce);
    }

    @Override
    public CompteEntity getAccount(Long id) {
	return dao.find(id);
    }
    
    @Override
    public List<CompteEntity> getAllOpenAccounts(){
        return dao.findAllOpenAccounts();
    }
}
