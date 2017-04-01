package cnr.isti.seec.doc.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import cnr.isti.seec.doc.DocumentoCommerciale;
import cnr.isti.seec.util.util;
import cnr.isti.ssec.data.corrispettivi.DatiRegistratoriTelematiciType;

@ManagedBean
@SessionScoped
public class Interventotecnico {


	private Map<String,String> interventi = new HashMap<String, String>();
	private Map<String, Integer> codici;
	private String intervento;
	private Integer codice;


	private int numero;
	private int numc;
	private float iva;
	private float importo;
	private float riscosso;

	private List<DocumentoCommerciale> righe;

	private float sconto;

	private boolean Reso;
	private boolean Annullo;


	Map<Float,DatiRegistratoriTelematiciType>	mapiva = new HashMap<>();
	private DocumentoCommerciale selected;


	private String stringaxml;

	@PostConstruct
	public void init() {
		interventi = new HashMap<String, String>();
		interventi.put("Manutenzione (ordinaria o straordinaria)", "Manutenzione (ordinaria o straordinaria)");
		interventi.put("Sostituzione del DGFE per guasto o esaurimento", "Sostituzione del DGFE per guasto o esaurimento");
		interventi.put("Verificazione periodica con esito positivo", "Verificazione periodica con esito positivo");
		interventi.put("Verificazione periodica con esito negativo", "Verificazione periodica con esito negativo");
		interventi.put("Verifica periodica con ritiro apparecchio", "Verifica periodica con ritiro apparecchio");
		interventi.put("Altro", "Altro");
		codici = new HashMap<String, Integer>();
		codici.put("Manutenzione (ordinaria o straordinaria)",1);
		codici.put("Sostituzione del DGFE per guasto o esaurimento",2);
		codici.put("Verificazione periodica con esito positivo",3);
		codici.put("Verificazione periodica con esito negativo",4);
		codici.put("Verifica periodica con ritiro apparecchio",5);
		codici.put("Altro",6);
	}



	public String getIntervento() {
		return intervento;
	}



	public void setIntervento(String intervento) {
		if(codici.containsKey(intervento))
			codice = codici.get(intervento);
		this.intervento = intervento;
	}



	public Integer getCodice() {
		return codice;
	}



	public void setCodice(Integer codice) {
		this.codice = codice;
	}



	public Map<String, String> getInterventi() {
		return interventi;
	}

	public void setInterventi(Map<String, String> interventi) {
		this.interventi = interventi;
	}

	public Map<String, Integer> getCodici() {
		return codici;
	}

	public void setCodici(Map<String, Integer> codici) {
		this.codici = codici;
	}



	public int getNumero() {
		return numero;
	}



	public void setNumero(int numero) {
		this.numero = numero;
	}



	public int getNumc() {
		return numc;
	}



	public void setNumc(int numc) {
		this.numc = numc;
	}



	public float getIva() {
		return iva;
	}



	public void setIva(float iva) {
		this.iva = iva;
	}



	public float getImporto() {
		return importo;
	}



	public void setImporto(float importo) {
		this.importo = importo;
	}



	public float getRiscosso() {
		return riscosso;
	}



	public void setRiscosso(float riscosso) {
		this.riscosso = riscosso;
	}



	public List<DocumentoCommerciale> getRighe() {
		if(righe==null){
			righe = new ArrayList<>();
		}
		return righe;
	}


	public void remriga(){
		righe.remove(selected);
	}


	public void setRighe(List<DocumentoCommerciale> righe) {
		this.righe = righe;
	}



	public float getSconto() {
		return sconto;
	}



	public void setSconto(float sconto) {
		this.sconto = sconto;
	}



	public boolean isReso() {
		return Reso;
	}



	public void setReso(boolean reso) {
		Reso = reso;
	}



	public boolean isAnnullo() {
		return Annullo;
	}



	public void setAnnullo(boolean annullo) {
		Annullo = annullo;
	}



	public DocumentoCommerciale getSelected() {
		return selected;
	}



	public void setSelected(DocumentoCommerciale selected) {
		
		this.selected = selected;
	}



	public String getStringaxml() {
		return stringaxml;
	}



	public void setStringaxml(String stringaxml) {
		this.stringaxml = stringaxml;
	}
	
	public void addMessage() {
		String summary = Annullo ? "Checked" : "Unchecked";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
	}
	public void addMessageA() {
		String summary = Reso ? "Checked" : "Unchecked";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
	}

	public void addriga() {

		DocumentoCommerciale e = new DocumentoCommerciale(0, 0, iva, importo, riscosso, sconto, Reso, Annullo);
		righe.add(e );
	}



	public void generatexml(ActionEvent actionEvent) {
		util.totalizza(righe,mapiva,Reso,Annullo);
		
		stringaxml = util.writeTo(mapiva,codice);
		init();
		righe = new ArrayList<>();
		importo = 0;
		sconto = 0;
		iva = 0;
		mapiva = new HashMap<>();
	}

	public  String gotochiusura(){

		return "/pages/Chiusura.xhtml";
	}




}
