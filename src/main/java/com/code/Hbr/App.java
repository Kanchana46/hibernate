package com.code.Hbr;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {
    public static void main( String[] args ) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        
        //Embedded object save
       /* Address address = (Address)context.getBean("address");
        address.setNo("50");
        address.setCity("London");
        address.setStreetName("Middle Street");*/
        
        // Mapping relations
        Laptop laptop1 = new Laptop(); //(Laptop)context.getBean("laptop");
        laptop1.setId(1);
        laptop1.setName("HP");
        
        Laptop laptop2 = new Laptop(); //(Laptop)context.getBean("laptop");
        laptop1.setId(2);
        laptop2.setName("Dell");
        
    	//Save data
    	Student student1 = new Student(); //(Student)context.getBean("student");
    	Student student2 = new Student();
    	
        student1.setId(104);
        student1.setRollno("103");
        student1.setName("Mike");
        student1.setMarks(85);
        
        student2.setId(105);
        student2.setRollno("104");
        student2.setName("Anna");
        student2.setMarks(76);
       // student.setAddress(address);
        //student.setLaptop(laptop);
        student1.getLaptop().add(laptop1);
        student1.getLaptop().add(laptop2);
        student2.getLaptop().add(laptop2);
        
        laptop1.getStudent().add(student1);
        laptop1.getStudent().add(student2);
        laptop2.getStudent().add(student1);
        laptop2.getStudent().add(student2);
        
       // laptop1.setStudent(student1);
       // laptop2.setStudent(student1);
        
        //laptop2.setStudent(student);

       
        //Configuration con = new Configuration().configure().addAnnotatedClass(Student.class);
        Configuration con = new Configuration().configure().addAnnotatedClass(Student.class).
        		addAnnotatedClass(Laptop.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
        SessionFactory sessionFactory = con.buildSessionFactory(serviceRegistry);       
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
       
       
        for (Laptop lp: student1.getLaptop()) {
        	System.out.println(lp);
        	session.persist(lp);
        }
        
        for (Laptop lp: student2.getLaptop()) {
        	System.out.println(lp);
        	session.persist(lp);
        }
        
        session.persist(student1);
        session.persist(student2);
        tx.commit();
        
        //Fetch data
        //student = (Student)session.get(Student.class, 104);
        //System.out.println(student);
         
    }
}
