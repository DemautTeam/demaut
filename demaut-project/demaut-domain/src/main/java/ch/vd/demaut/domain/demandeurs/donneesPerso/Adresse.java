package ch.vd.demaut.domain.demandeurs.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Created by mourad on 14.09.15.
 */
public class Adresse extends BaseValueObject {

	private final String voie;

	private final NPA npa;

	private final Localite localite;

	private final Pays pays;

	public Adresse(String voie, Localite localite, NPA npa, Pays pays) {
		super();
		this.voie = voie;
		this.localite = localite;
		this.npa = npa;
		this.pays = pays;
	}

	public String getVoie() {
		return voie;
	}

	public Localite getLocalite() {
		return localite;
	}

	public NPA getNpa() {
		return npa;
	}

	public Pays getPays() {
		return pays;
	}
}
