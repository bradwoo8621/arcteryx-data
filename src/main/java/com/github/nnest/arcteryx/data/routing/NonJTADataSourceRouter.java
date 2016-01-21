/**
 * 
 */
package com.github.nnest.arcteryx.data.routing;

import org.apache.commons.lang3.StringUtils;

/**
 * Non-JTA thread level data source router. </br>
 * <b>Note {@linkplain #switchTo(String)} must in try block and
 * {@linkplain #release()} must in finally block.</b></br>
 * <b>Transaction must be committed before release data source.</b>
 * 
 * @author brad.wu
 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey
 */
public class NonJTADataSourceRouter implements IDataSourceRouter {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.data.routing.IDataSourceRouter#getCurrent()
	 */
	public String getCurrent() {
		return IDataSourceRouter.ThreadRoutingDelegate.getCurrent();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.data.routing.IDataSourceRouter#switchTo(java.lang.String)
	 */
	public void switchTo(String dataSourceIdentity) throws IncorrectDataSourceRoutingException {
		String currentDataSourceIdentity = IDataSourceRouter.ThreadRoutingDelegate.getCurrent();
		if (StringUtils.isEmpty(currentDataSourceIdentity)) {
			// no data source used before
			IDataSourceRouter.ThreadRoutingDelegate.switchTo(dataSourceIdentity);
		} else if (StringUtils.equals(dataSourceIdentity, currentDataSourceIdentity)) {
			// same data source as before, do nothing
		} else {
			throw new IncorrectDataSourceRoutingException(String.format(
					"In non-JTA situation, release the original data source [%s] before switching to another [%s]",
					currentDataSourceIdentity, dataSourceIdentity));
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.arcteryx.data.routing.IDataSourceRouter#release()
	 */
	public void release() {
		IDataSourceRouter.ThreadRoutingDelegate.release();
	}
}
