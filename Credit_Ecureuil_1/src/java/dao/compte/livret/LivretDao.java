package dao.compte.livret;

/**
 *
 * @author rcharpen
 */
public interface LivretDao {
    public void save(LivretEntity ce);
    
    public LivretEntity find(Long id);
    
    public void update(LivretEntity ce);
    
    public void remove(LivretEntity ce);
}
