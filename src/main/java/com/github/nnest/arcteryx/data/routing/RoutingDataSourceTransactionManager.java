/**
 * 
 */
package com.github.nnest.arcteryx.data.routing;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/**
 * routing data source transaction manager.</br>
 * using the data source router which have same behavior as routing data source
 * or exactly same one.
 * 
 * @author brad.wu
 * @see RoutingDataSource
 */
public class RoutingDataSourceTransactionManager implements PlatformTransactionManager {
	private IDataSourceRouter dataSourceRouter = null;
	private PlatformTransactionManager transactionManager = null;

	/**
	 * @return the dataSourceRouter
	 */
	public IDataSourceRouter getDataSourceRouter() {
		return dataSourceRouter;
	}

	/**
	 * data source router must have same behavior as which in
	 * {@linkplain RoutingDataSource}, or exactly the same one
	 * 
	 * @param dataSourceRouter
	 *            the dataSourceRouter to set
	 * @see RoutingDataSource
	 */
	public void setDataSourceRouter(IDataSourceRouter dataSourceRouter) {
		this.dataSourceRouter = dataSourceRouter;
	}

	/**
	 * @return the transactionManager
	 */
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager
	 *            the transactionManager to set
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.transaction.PlatformTransactionManager#getTransaction(org.springframework.transaction.TransactionDefinition)
	 */
	public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
		return this.getTransactionManager().getTransaction(definition);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.transaction.PlatformTransactionManager#commit(org.springframework.transaction.TransactionStatus)
	 */
	public void commit(TransactionStatus status) throws TransactionException {
		try {
			this.getTransactionManager().commit(status);
		} finally {
			this.getDataSourceRouter().release();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.transaction.PlatformTransactionManager#rollback(org.springframework.transaction.TransactionStatus)
	 */
	public void rollback(TransactionStatus status) throws TransactionException {
		try {
			this.getTransactionManager().rollback(status);
		} finally {
			this.getDataSourceRouter().release();
		}
	}
}
