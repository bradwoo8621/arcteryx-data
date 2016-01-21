/**
 * 
 */
package com.github.nnest.arcteryx.data.release;

/**
 * @author brad.wu
 *
 */
// @Entity
// @Table(name = "T_USER")
// @SequenceGenerator(name = "S_USER", sequenceName = "S_USER")
public class User {
	// @Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_USER")
	// @Column(name = "USER_ID")
	private Long id = null;
	// @Column(name = "USER_NAME")
	private String name = null;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
