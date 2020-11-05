package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@Scope(value = "session")
@Controller(value = "employeController")
@ELBeanName(value = "employeController")
@Join(path = "/", to = "/login.jsf")
public class IControllerEmployeImpl {
	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;
	@Autowired
	EmployeRepository employeRepository;
    
	private String login;
	private String email;
	private String prenom;
	private String nom;
	private boolean actif;
	private Role role;
	private String password;
	private Employe employe;
	private Boolean loggedIn;
	private Employe authenticatedUser = null;

	public Role[] getRoles() {
		return Role.values();
	}

	private List<Employe> employes;

	public List<Employe> getEmployes() {
		employes = iemployeservice.getAllEmployes();
		return employes;
	}

	public Employe getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(Employe authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public String doLogin() {
		String navigateTo = "null";
	   authenticatedUser = iemployeservice.authenticate(login, password);
		if (authenticatedUser != null && authenticatedUser.getRole() == Role.ADMINISTRATEUR) {
			navigateTo = "welcome.xhtml?faces-redirect=true";
			loggedIn = true;
		} else {
			FacesMessage facesMessage = new FacesMessage(
					"Login Failed: please check your username/password and try again.");
			FacesContext.getCurrentInstance().addMessage("form:btn", facesMessage);
		}
		return navigateTo;
	}

	public void addEmploye() {
		iemployeservice.addOrUpdateEmploye(new Employe(nom, prenom, email, actif, password, role));
	}

	private Integer employeIdToBeUpdated; 
	

	public Integer getEmployeIdToBeUpdated() {
		return employeIdToBeUpdated;
	}

	public void setEmployeIdToBeUpdated(Integer employeIdToBeUpdated) {
		this.employeIdToBeUpdated = employeIdToBeUpdated;
	}

	public void displayEmploye(Employe empl) { this.setPrenom(empl.getPrenom()); this.setNom(empl.getNom()); this.setActif(empl.isActif()); this.setEmail(empl.getEmail()); this.setRole(empl.getRole()); this.setPassword(empl.getPassword()); this.setEmployeIdToBeUpdated(empl.getId());
	}
	public void updateEmploye() {
		iemployeservice
				.addOrUpdateEmploye(new Employe(employeIdToBeUpdated, nom, prenom, email, actif, password, role));
	}

	public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public void setEmployes(List<Employe> employes) {
		this.employes = employes;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int ajouterEmploye(Employe employe) {
		iemployeservice.ajouterEmploye(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		iemployeservice.mettreAjourEmailByEmployeId(email, employeId);

	}

	public void affecterEmployeADepartement(int employeId, int depId) {
		iemployeservice.affecterEmployeADepartement(employeId, depId);

	}

	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		iemployeservice.desaffecterEmployeDuDepartement(employeId, depId);
	}

	public int ajouterContrat(Contrat contrat) {
		iemployeservice.ajouterContrat(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		iemployeservice.affecterContratAEmploye(contratId, employeId);
	}

	public String getEmployePrenomById(int employeId) {
		return iemployeservice.getEmployePrenomById(employeId);
	}

	public void deleteEmployeById(int employeId) {
		iemployeservice.deleteEmployeById(employeId);

	}

	public void deleteContratById(int contratId) {
		iemployeservice.deleteContratById(contratId);
	}

	public int getNombreEmployeJPQL() {

		return iemployeservice.getNombreEmployeJPQL();
	}

	public List<String> getAllEmployeNamesJPQL() {

		return iemployeservice.getAllEmployeNamesJPQL();
	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return iemployeservice.getAllEmployeByEntreprise(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public void deleteAllContratJPQL() {
		iemployeservice.deleteAllContratJPQL();

	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		
		return iemployeservice.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		
		return iemployeservice.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return iemployeservice.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {

		return iemployeservice.getAllEmployes();
	}

}
