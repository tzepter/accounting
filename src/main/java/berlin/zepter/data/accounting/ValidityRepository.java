package berlin.zepter.data.accounting;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ValidityRepository extends JpaRepository<Validity, Long>{
	/**
	 * <p>Lookup method to find the id of a {@link Validity} persistence row 
	 * given special effectiveness and existence time stamp.</p>
	 * @param effectiveness {@link Validity#getEffectiveness()}
	 * @param existence {@link Validity#getExistence()}
	 * @return the found {@link Validity} Object
	 */
	public Validity findByEffectivenessAndExistence(Timestamp effectiveness, Timestamp existence);
}
                                                                                      