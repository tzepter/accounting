package berlin.zepter.data.accounting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ValidityLookupServiceTest {

	@Autowired ValidityLookupService cut;
	@Autowired ValidityRepository repository;
	
	private LocalDateTime existence;
	private LocalDateTime effectiveness;

	@BeforeEach
	void setSpecialTimes() {
		existence = LocalDateTime.of(2020,1,1,0,0,0);
		effectiveness = LocalDateTime.of(2019,12,21,8,3,34);
	}
	
	@Test
	public void getNewValidity_ifItDoesNotExists() {
		assertEquals(0, repository.findAll().size());
		long newId = cut.getValidityId(effectiveness, existence);
		assertThat(Long.valueOf(newId), greaterThan(Long.valueOf(0L)));
		assertEquals(1, repository.findAll().size());
	}
	
	@Test
	public void getExistingValidity_ifItDoesExists() {
		assertEquals(0, repository.findAll().size());
		repository.saveAndFlush(new Validity(Timestamp.valueOf(effectiveness), Timestamp.valueOf(existence)));
		assertEquals(1, repository.findAll().size());
		long newId = cut.getValidityId(effectiveness, existence);
		assertThat(Long.valueOf(newId), greaterThan(Long.valueOf(0L)));
		assertEquals(1, repository.findAll().size());
		assertEquals(1, newId);
	}
	
	@Test
	public void getValidityInformation_ifItDoNotExist() {
		assertEquals(0, repository.findAll().size());
		Optional<Validity> validity = cut.getValidity(1);
		assertTrue(validity.isEmpty());
	}
	
	@Test
	public void getValidityInformation_ifItExist() {
		assertEquals(0, repository.findAll().size());
	    long vid = 
	    	repository.saveAndFlush(
	    		new Validity(Timestamp.valueOf(effectiveness), 
	    		Timestamp.valueOf(existence)))
	    	.getVid();
		assertEquals(1, repository.findAll().size());
		Optional<Validity> validity = cut.getValidity(vid);
		assertTrue(validity.isPresent());
		assertEquals(Timestamp.valueOf(effectiveness), validity.get().getEffectiveness());
		assertEquals(Timestamp.valueOf(existence), validity.get().getExistence());
	}	
}
