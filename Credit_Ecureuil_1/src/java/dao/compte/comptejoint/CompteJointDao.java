package dao.compte.comptejoint;

/**
 *
 * @author rcharpen
 */
public interface CompteJointDao {
    public void save(CompteJointEntity ce);
    
    public CompteJointEntity find(Long id);
    
    public void update(CompteJointEntity ce);
    
    public void remove(CompteJointEntity ce);
}
