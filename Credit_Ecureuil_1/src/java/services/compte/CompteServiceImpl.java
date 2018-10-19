package services.compte;

import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import services.utilisateur.UtilisateurService;
import services.utilisateur.UtilisateurServiceImpl;

@Service
public class CompteServiceImpl implements CompteService{
   
    @Resource
    private CompteDaoImpl dao;

    public void setDao(CompteDaoImpl dao) {
        this.dao = dao;
    }

    public CompteDaoImpl getDao() {
        return dao;
    }
    
    @Override
    public boolean virement(Long src, Long dest, Double montant) {
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if(cesrc!=null && cedst!=null){
	    if(cesrc.getSolde()>=montant){
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
    
}
