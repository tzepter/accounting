package berlin.zepter.data.accounting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ValidationRepositoryTest {

	private LocalDateTime effectiveness;
	private LocalDateTime earlyExistence;
	private LocalDateTime lateExistence;
	
	@Autowired ValidityRepository validityRepository;

	@BeforeEach
	void setSpecialTimes() {
		effectiveness = LocalDateTime.of(2020,1,1,0,0,0);
		earlyExistence = LocalDateTime.of(2019,12,21,8,3,34);
		lateExistence = LocalDateTime.of(2020,1,15,22,32,55);
	}
	
	@Test
	void injectedComponentsAreNotNull(){
		assertNotNull(validityRepository);
	}
	
	@Test
	public void findNothing_whenDatabaseIsEmpty() {
		Iterable<Validity> validities = validityRepository.findAll();
		assertEquals(false, validities.iterator().hasNext());
	}
	
	@Test
	public void findAllRows_whenInsertedBefore() {
		fillWithData();
		validityRepository.findAll().forEach(System.out::println);
		assertEquals(2, validityRepository.count());
	}
	
	@Test
	public void findRowById_whenInsertedBefore() {
		fillWithData();
		Optional<Validity> optValidity = validityRepository.findById(1L);
		assertTrue(optValidity.isPresent());
		assertEquals(Timestamp.valueOf(earlyExistence), optValidity.get().getExistence());
	}	

	@Test
	public void findRowByData_whenInsertedBefore() {
		fillWithData();
		assertEquals(2,
			validityRepository
			   .findByEffectivenessAndExistence(
				   Timestamp.valueOf(effectiveness), 
				   Timestamp.valueOf(lateExistence)).getVid()
		);
	}

	@Test
	public void insertDataTwice_YieldsInException() {
		fillWithData();
		List<Exception> exceptions = fillWithData();
		exceptions.forEach(ex -> System.out.println(ex));
		assertEquals(2, exceptions.size());
	}

	private List<Exception> fillWithData() {
		List<Exception> returner = new ArrayList<Exception>();
		List<Validity> data = new ArrayList<>();
		data.add(
			new Validity(
				Timestamp.valueOf(effectiveness),
				Timestamp.valueOf(earlyExistence)
			));
		data.add(
			new Validity(
				Timestamp.valueOf(effectiveness),
				Timestamp.valueOf(lateExistence)
			));
	    data.stream().forEach(item -> {
	    	 try {
	    		 validityRepository.saveAndFlush(item);
	    	 } catch(DataIntegrityViolationException e) {
	    		 returner.add(e);
	    	 }
	    });
	    if (returner.size() == 0) {
	    	 validityRepository.findAll().forEach(System.out::println);
	    }
	    return returner;
	}
}