/**
 * 
 */
package com.github.nnest.arcteryx.data.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * routing data source
 * 
 * @author brad.wu
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	private IDataSourceRouter dataSourceRouter = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return this.getDataSourceRouter().getCurrent();
	}

	/**
	 * @return the dataSourceRouter
	 */
	public IDataSourceRouter getDataSourceRouter() {
		return dataSourceRouter;
	}

	/**
	 * @param dataSourceRouter
	 *            the dataSourceRouter to set
	 */
	public void setDataSourceRouter(IDataSourceRouter dataSourceRouter) {
		this.dataSourceRouter = dataSourceRouter;
	}
}
