/**
 * 
 */
package com.github.nnest.arcteryx.data.routing;

/**
 * JTA data source router
 * 
 * @author brad.wu
 */
public class JTADataSourceRouter implements IDataSourceRouter {

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
		IDataSourceRouter.ThreadRoutingDelegate.switchTo(dataSourceIdentity);
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
