package berlin.zepter.data.accounting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ValidityTest {
	
	@Test
	public void equalsContract() {
	    EqualsVerifier.forClass(Validity.class).verify();
	}

	@Test
	public void setAndGetId() {
		Validity cut = new Validity();
		cut.setVid(1L);
		assertEquals(1L, cut.getVid());
	}
}
