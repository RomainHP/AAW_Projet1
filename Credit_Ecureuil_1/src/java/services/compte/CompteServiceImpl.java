package services.compte;

import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import services.utilisateur.UtilisateurService;
import services.utilisateur.UtilisateurServiceImpl;


public class CompteServiceImpl implements CompteService{
   
    @Autowired
    private CompteDaoImpl dao;
    
    @Autowired
    private UtilisateurDaoImpl udi;

    public CompteServiceImpl(){
	this.dao = new CompteDaoImpl();
	this.udi = new UtilisateurDaoImpl();
    }
    
    @Override
    public boolean virement(Long src, Long dest, Double montant) {
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if(cesrc!=null && cedst!=null){
	    if(cesrc.getSolde()>=montant && montant>0f){
		cedst.setSolde(cedst.getSolde() + montant);
		cesrc.setSolde(cesrc.getSolde() - montant);
		dao.save(cedst);
		dao.save(cesrc);
		return true;
	    }
	}
	
	return false;
    }

    @Override
    public List<CompteEntity> consultation(String username) {
	UtilisateurService service = new UtilisateurServiceImpl();
        UtilisateurEntity ant = service.getUtilisateur(username);
        if (ant!=null){
            return ant.getComptes();
        }
        return new ArrayList<>();
    }
    
    @Override
    public boolean creeCompte(String nomCompte, String nomUtilisateur){
	UtilisateurEntity ue = this.udi.find(nomUtilisateur);
	if(ue != null){
	    CompteEntity ce = new CompteEntity(nomCompte, ue);
	    dao.save(ce);
	    ue.addSingleAccount(ce);
	    this.udi.save(ue);
	    return true;
	}
	return false;
    }

    @Override
    public boolean supprCompte(Long id) {
	CompteEntity ce = this.dao.find(id);
	CompteEntity ceToDel = null;
	if(ce != null){
	    if(ce.getSolde()==0d){
		this.dao.remove(ce);
		for(CompteEntity ceTmp : ce.getProprietaire().getComptes()){
		    if(ceTmp == ce){
			ceToDel = ceTmp;
		    }
		}
		ce.getProprietaire().getComptes().remove(ceToDel);
		return true;
	    }
	}
	return false;
    }
}
