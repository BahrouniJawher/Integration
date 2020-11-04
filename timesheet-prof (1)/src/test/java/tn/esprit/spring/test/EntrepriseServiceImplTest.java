package tn.esprit.spring.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.EntrepriseServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	
	@Autowired 
	EntrepriseServiceImpl entrepriseservice ;
	@Test
	public void testajouterEntreprise()  {
		Entreprise en= new Entreprise("FIS", "informatique");
		entrepriseservice.ajouterEntreprise(en);
		assertEquals("FIS" , en.getName());
	}
	
	@Test
	public void testajouterDepartement()    {
		
		Departement en = new Departement("HR");
		entrepriseservice.ajouterDepartement(en);
		assertEquals( "HR", en.getName());
	}
	
	@Test
	public void testaffecterDepartementAEntreprise()    {
		
		
		Departement en = new Departement("HR");
		entrepriseservice.affecterDepartementAEntreprise(1, 1);
		assertEquals("HR" ,en.getName());
	}
	@Test
	public void testupdateEntreprise()    {
		
		
		Entreprise en= new Entreprise("FIS", "info");
		entrepriseservice.ajouterEntreprise(en);
		assertEquals("FIS" ,en.getName());
	}
	@Test
	public void testgetAllDepartementsNamesByEntreprises() {
		assertEquals(5, entrepriseservice.getAllDepartementsNamesByEntreprise(1).size());
	}
	
//	@Test
//	public void testdeleteEntrepriseById() {
//		
//		assertTrue(entrepriseservice.deleteEntrepriseById(2));
//	}
	
	
	
	

}
