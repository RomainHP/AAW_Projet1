package services.compte;

import dao.compte.CompteDao;
import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CompteServiceImpl implements CompteService{
   
    @Resource
    CompteDao dao;
    
    @Resource
    UtilisateurDao userDao;

    public void setDao(CompteDao dao) {
        this.dao = dao;
    }

    public CompteDao getDao() {
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
        UtilisateurEntity ant = userDao.find(username);
        if (ant!=null){
            return ant.getComptes();
        }
        return new ArrayList<>();
    }
    
}
