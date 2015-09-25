package ch.vd.demaut.domain.annexes;

/**
 * Représente le type d'une annexe venant de ProgreSOA
 *
 */
public enum TypeAnnexeProgres {
    DiplomeOuCertificat(50283740, "Diplôme ou certificat"),
    CertificatDeTravail(50283742, "Certificat de travail"),
    ExtraitCasierJudiciaire(50283744, "Extrait de casier judiciaire"),
    ReconnaissanceEquivalence(50283747, "Reconnaissance équivalence"),
    CurriculumVitae(50283749, "Curriculum vitae"),
    AttestationTravail(50283751, "Attestation de travail"),
    CopieAPAutreCanton(50283753, "Copie AP autre canton"),
    SpecimenDeSignature(50283755, "Spécimen de signature"),
    CertificatMedical(50283757, "Certificat médical"),
    PreavisPositif(50283759, "Préavis positif"),
    DemandeAutorisation(50283761, "Demande d'autorisation"),
    AutrePiece(51401131, "Autre pièce"),
    CopieDiplomeOuCertificat(53026879, "Copie diplôme ou certificat"),
    CopieTitreSpecialiste(53026890, "Copie titre spécialiste"),
    TitreDeSpecialiste(98247240, "Titre de spécialiste"),
    FormationContinue(239977844, "Formation continue"),
    CertifAmbulancier(304890606, "Certif. ambulancier IAS"),
    TitreEtranger(304890636, "Titre étranger"),
    CertifBLSAED(304890682, "Certif. BLS/AED"),
    CertifFormationCFM(304890769, "Certif. formation CFM"),
    PostGradeInfirmier(304890879, "Post-grade infirmier"),
    DegradationRefus(304890929, "Dérogation refus"),
    FormationMedecinConseil(304890950, "Formation médecin conseil"),
    FormationCEFOCA(304890954, "Formation CEFOCA"),
    FormationSSMUS(304890956, "Formation SSMUS"),
    Cahierdescharges(304890962, "Cahier des charges");
    
    private TypePieceProgresID progresId;
    
    private String libelle;

    private TypeAnnexeProgres(Integer id, String libelle) {
        this.progresId = new  TypePieceProgresID(id);
        
        this.libelle = libelle;
    }
    
    public TypePieceProgresID getProgresId() {
        return progresId;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
}
