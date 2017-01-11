package net.atos.common.jta;

import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

public class JtaPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {
	private boolean jtaMode = false;
	private DataSource jtaDataSource;
	private PersistenceUnitTransactionType transacType = PersistenceUnitTransactionType.RESOURCE_LOCAL;

	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
		if (jtaMode) {
			transacType = PersistenceUnitTransactionType.JTA;
			mutablePersistenceUnitInfo.setJtaDataSource(this.getJtaDataSource());
		}
		mutablePersistenceUnitInfo.setTransactionType(transacType);
	}

	public boolean isJtaMode() {
		return jtaMode;
	}

	public void setJtaMode(boolean jtaMode) {
		this.jtaMode = jtaMode;
	}

	public DataSource getJtaDataSource() {
		return jtaDataSource;
	}

	public void setJtaDataSource(DataSource jtaDataSource) {
		this.jtaDataSource = jtaDataSource;
	}
}