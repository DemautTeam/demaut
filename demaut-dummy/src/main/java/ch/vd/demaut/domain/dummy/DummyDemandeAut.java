package ch.vd.demaut.domain.dummy;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandeurs.Demandeur;

@Aggregate
public class DummyDemandeAut extends DummyDemande {
	
	private ProfessionDeLaSante professionDeLaSante;

	private Demandeur demandeur;

    private StatutDemandeAutorisation statutDemandeAutorisation;
    
    private List<Annexe> annexes = new ArrayList<Annexe>();

    private long nonPersistField;
	
    private transient ConfigDemaut config;

    public DummyDemandeAut() {
    	super();
		this.annexes = new ArrayList<Annexe>();
		this.statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
    }
    
	public DummyDemandeAut(ProfessionDeLaSante profession, Demandeur demandeur) {
		this();
		this.professionDeLaSante = profession;
		this.demandeur = demandeur;
	}

	public long getNonPersistField() {
		return nonPersistField;
	}
	
	@NotNull
	public ProfessionDeLaSante getProfessionDeLaSante() {
		return professionDeLaSante;
	}

	@NotNull
	public Demandeur getDemandeur() {
		return demandeur;
	}
	
	public List<Annexe> getAnnexes() {
		return annexes;
	}
	
	public void ajouterAnnexe(Annexe a) {
		annexes.add(a);
	}
	
	@NotNull
	public StatutDemandeAutorisation getStatutDemandeAutorisation() {
		return statutDemandeAutorisation;
	}
	
	@Override
	public DummyDemandeAutFK getFunctionalKey() {
		return new DummyDemandeAutFK(this);
	}
	
	public ConfigDemaut getConfig() {
		return config;
	}
	
	public void setConfig(ConfigDemaut config) {
		this.config = config;
	}

}
 