package berlin.zepter.data.accounting;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * The Service-Class ValidityLookupService forms the module interface 
 * to the consumers which requires a validity specification for their business data. 
 * 
 * @author Tobias Zepter
 * @since 1.0.0
 */
@Service
public class ValidityLookupService {
	
	@Autowired ValidityRepository validityRepository;
	
	/**
	 * The method returns the database id of a validity object that represents the
	 * given attributes effectiveness and existence. If a database row with the 
	 * characteristics of these attributes exists the id of that row is returned.
	 * Otherwise a new row is created and its id is returned.
	 * 
	 * @param effectiveness the effectiveness time stamp of the business data
	 * @param existence the existence time stamp of the business data
	 * @return the id which represents that combination of the attributes
	 * @throws DataIntegrityViolationException should not happened because exact
	 * 		this constellation is asked for before. Maybe of concurrency?
	 */
	public long getValidityId(LocalDateTime effectiveness, LocalDateTime existence) 
		   throws DataIntegrityViolationException {
		Timestamp effectivenessTimestamp = Timestamp.valueOf(effectiveness); 
		Timestamp existenceTimestamp = Timestamp.valueOf(existence);
		Validity validity = 
				validityRepository.findByEffectivenessAndExistence(
					effectivenessTimestamp, 
					existenceTimestamp
				);
		if (validity == null) {
			validity = 
				validityRepository.saveAndFlush(
					new Validity(effectivenessTimestamp, existenceTimestamp)
				);
		}
		return validity.getVid();
	}
	/**
	 * The method returns an optionally persisted Validity object corresponding
	 * with an artificial identifier. Is is made for getting the relevant
	 * validity time stamps of business keys and data.
	 * @param validityId the identifier of the persisted validity object
	 * @return an Optional of the validity object
	 */
	public Optional<Validity> getValidity(long validityId) {
		return validityRepository.findById(validityId);
	}
	
}
