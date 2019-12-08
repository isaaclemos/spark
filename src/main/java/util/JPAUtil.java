package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private JPAUtil() {}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("TimePU");
	}
}
