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
    UtilisateurDao userDao;
    
    @Resource 
    TransactionDao transactionDao;

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
        if (montant<=0f) throw new ServiceException("Montant invalide (inférieur ou égal à 0).");
        if (cesrc.getSolde()<montant) throw new ServiceException("Solde du compte source insuffisant.");
        cedst.setSolde(cedst.getSolde() + montant);
        cesrc.setSolde(cesrc.getSolde() - montant);
        dao.save(cedst);
        dao.save(cesrc);		
        TransactionEntity te = new TransactionEntity(cesrc, cedst, montant);
        cesrc.getTransactions().add(te);
        cedst.getTransactions().add(te);
        dao.save(cedst);
        dao.save(cesrc);
    }

    @Override
    public List<CompteEntity> consultation(String username) {
        UtilisateurEntity ant = userDao.find(username);
        if (ant!=null){
            return ant.getComptes();
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
        userDao.save(ue);
    }

    @Override
    public void supprimerLivret(Long id) throws ServiceException {
	LivretEntity ce = livretDao.find(id);
	if(ce == null) throw new ServiceException("Livret introuvable.");
        if(ce.getSolde()!=0d) throw new ServiceException("Le solde du livret à supprimer n'est pas nulle (0).");
        ce.getProprietaire().removeAccount(ce);
        userDao.save(ce.getProprietaire());
        livretDao.remove(ce);
    }

    @Override
    public CompteEntity getAccount(Long id) {
	return dao.find(id);
    }
}
