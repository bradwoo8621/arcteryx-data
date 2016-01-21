/**
 * 
 */
package com.github.nnest.arcteryx.data;

/**
 * data service
 * 
 * @author brad.wu
 */
public interface IDataService {
	/**
	 * save given bean. create or update
	 * 
	 * @param bean
	 * @return
	 */
	<T> T save(T bean);

	/**
	 * delete given bean
	 * 
	 * @param bean
	 */
	<T> void delete(T bean);

	/**
	 * load given bean
	 * 
	 * @param bean
	 * @return
	 */
	<T> T load(IPrimaryKey bean);
}
