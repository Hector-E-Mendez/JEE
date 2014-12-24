package main;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import domain.Player;
import domain.Team;

public class SportTeamsAppListener implements ServletContextListener{

	private static Logger log = Logger.getLogger(SportTeamsAppListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		BasicConfigurator.configure();
		log.info("Context initialized for app SportTeams");
		Configuration config = new Configuration().configure(sc.getInitParameter("hibernateConfig"));
		config.addAnnotatedClass(Player.class);
		config.addAnnotatedClass(Team.class);
		new SchemaExport(config).create(true, true);
		log.info("Schema created");
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory factory = config.buildSessionFactory(serviceRegistry);
		populateDB(factory);
		sc.setAttribute("hibFactory", factory);
	}

	public void populateDB(SessionFactory sf){
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		Player playerA = new Player();
		Player playerB = new Player();
		Player playerC = new Player();
		
		Team teamA = new Team();
		Team teamB = new Team();
		
		teamA.setName("Team A");
		teamB.setName("Team B");
		
		playerA.setName("Hector");
		playerA.setTeam(teamA);
		playerB.setName("Priscilla");
		playerB.setTeam(teamA);
		playerC.setName("Angelo");
		playerC.setTeam(teamB);
		
		session.save(teamA);
		session.save(teamB);
		
		session.save(playerA);
		session.save(playerB);
		session.save(playerC);
		
		session.getTransaction().commit();
	}
}
