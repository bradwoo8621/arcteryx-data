/**
 * 
 */
package com.github.nnest.arcteryx.data.routing;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * data source router
 * 
 * @author brad.wu
 */
public interface IDataSourceRouter {
	/**
	 * get current data source identity
	 * 
	 * @return
	 */
	String getCurrent();

	/**
	 * switch to data source by given identity
	 * 
	 * @param dataSourceIdentity
	 * @throws IncorrectDataSourceRoutingException
	 */
	void switchTo(String dataSourceIdentity) throws IncorrectDataSourceRoutingException;

	/**
	 * release current data source
	 */
	void release();

	/**
	 * routing delegate, store the current data source identity in thread local.
	 * 
	 * @author brad.wu
	 *
	 */
	public static final class ThreadRoutingDelegate {
		private static Logger LOGGER = LoggerFactory.getLogger(ThreadRoutingDelegate.class);
		private static final ThreadLocal<String> ROUTING = new ThreadLocal<String>();

		/**
		 * get current routing
		 * 
		 * @return
		 */
		public static String getCurrent() {
			return ROUTING.get();
		}

		/**
		 * switch to data source by given id
		 * 
		 * @param dataSourceIdentity
		 * @see IncorrectDataSourceRoutingException throws when the original
		 *      data source exists and not same as given id
		 */
		public static void switchTo(String dataSourceIdentity) {
			if (StringUtils.isEmpty(dataSourceIdentity)) {
				throw new IllegalArgumentException("Data source identity cannot be empty");
			}

			LOGGER.info("Switched to data source [{}]", dataSourceIdentity);
			ROUTING.set(dataSourceIdentity);
		}

		/**
		 * release the current data source
		 */
		public static void release() {
			LOGGER.info("Release data source [{}]", getCurrent());
			ROUTING.remove();
		}
	}
}
