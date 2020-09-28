package berlin.zepter.data.accounting;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>The class Validity is the base class for account-like database persistence. 
 * It represents the two time axes of effectiveness and existence, which apply to
 * all account-based data. Insofar as such a combination of time data exists, 
 * reference can be made to it on the user data by means of a foreign key relationship.
 * For the definition of "effectiveness" and "existence" see the javadocs 
 * for the attributes named in this way.
 * 
 * @author Tobias Zepter
 * @since 1.0.0
 */
@Entity
@Table(
	name = "validity", 
	schema = "public",
	uniqueConstraints = 
	  @UniqueConstraint( 
		name = "uix_effectiveness_existence", 
		columnNames = {"effectiveness", "existence"})
	)
@Data
public class Validity implements Serializable{
	
	/**
	 * <p>Convenience constructor for Validity class objects to set the payload</p>
	 * @param effectiveness please look at private attribute effectiveness
	 * @param existence please look at private attribute existence
	 */
	public Validity(Timestamp effectiveness, Timestamp existence) {
		setEffectiveness(effectiveness);
		setExistence(existence);
	}
	/**
	 * <p>Default constructor is needed by Spring data.</p>
	 */
	public Validity(){
	}
	/**
	 * <p>The Validity Identifier is equivalent to the primary table key.</p>
	 */
	@Id
	@SequenceGenerator(name = "seq-gen", sequenceName = "vid_gen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq-gen")
	@Column(name = "vid")
	@EqualsAndHashCode.Exclude
    private Long vid;
	/**
	 * <p>The <b>effectiveness</b> is an indication of time, which, according to its name,
	 * indicates whether dependent data must be taken into account within a specific factual context.
	 * It is an "inclusive start" ("effective from inclusive") in the form of a date, time or timestamp.
	 * At the moment it is not yet clear what granularity is required.</p>
	 */
	@Column(name = "effectiveness", nullable = false)
	private Timestamp effectiveness;
	/**
	 * <p>The <b>existence</b> is an indication of time in the form of a time stamp that is as
	 * finely granulated as possible.It tells us from when dependent data have existed.
	 * In other words, it is the point in time when an employee, a customer, 
	 * a program etc. created this data record.</p>
	 */
	@Column(name = "existence", nullable = false)
	private Timestamp existence; 
	/**
	 * Serializable Identifier
	 */
	private static final long serialVersionUID = 1L;
}
