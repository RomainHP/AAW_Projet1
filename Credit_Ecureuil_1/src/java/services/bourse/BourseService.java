package services.bourse;

/**
 *
 * @author rcharpen
 */
public interface BourseService {
    void achat(String entreprise, double valeur);
    void vente(String entreprise, double valeur);
}
