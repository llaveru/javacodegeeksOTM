package com.javacodegeeks.otm;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.javacodegeeks.otm.entidades.MarksDetails;
import com.javacodegeeks.otm.entidades.Student;

public class AppMain {

	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		//ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		//sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		sessionFactoryObj= new Configuration().configure().buildSessionFactory();
		
		return sessionFactoryObj;
	}

	public static void main(String[] args) {
		System.out.println(".......Hibernate One To Many Mapping Example.......\n");
		try {
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();

			Student studentObj = new Student("Java", "Geek",  "javageek@javacodegeeks.com", "0123456789");
			sessionObj.save(studentObj);

			MarksDetails marksObj1 = new MarksDetails("English", "100", "90",  "Pass");  
			marksObj1.setStudent(studentObj);  
			sessionObj.save(marksObj1);

			MarksDetails marksObj2 = new MarksDetails("Maths", "100", "99",  "Pass");  
			marksObj2.setStudent(studentObj);
			sessionObj.save(marksObj2);

			MarksDetails marksObj3 = new MarksDetails("Science", "100", "94",  "Pass");  
			marksObj3.setStudent(studentObj);
			sessionObj.save(marksObj3);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();

			System.out.println("\n.......Records Saved Successfully To The Database.......");
		} catch(Exception sqlException) {
			System.out.println(sqlException.getMessage());
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
}