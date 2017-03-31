package cnr.isti.seec.doc.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import cnr.isti.seec.doc.DocumentoCommerciale;

@ManagedBean
@SessionScoped
public class DocComm {
	
	
	private List<DocumentoCommerciale> righe;
	
	
	private int numero;
	private int numc;
	private float iva;
	private float importo;
	private float riscosso;
	
	private float sconto;
	
	private boolean Reso;
	private boolean Annullo;


	private DocumentoCommerciale selected;
	
	
	@PostConstruct
	public void init() {
		
		numero = 0;
		numc = 0;
		
		riscosso = 0;
		sconto = 0;
		Reso = false;
		Annullo = false;
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
		riscosso = importo;
	}


	public float getRiscosso() {
		return riscosso;
	}


	public void setRiscosso(float riscosso) {
		this.riscosso = riscosso;
	}


	public float getSconto() {
		return sconto;
	}

	private void ricalcolaiva(float sconto) {
		for ( DocumentoCommerciale iterable_element : righe) {
			iterable_element.setSconto(sconto);
		}
	}

	public void setSconto(float sconto) {
		//ricalcolaiva(sconto);
		riscosso  = (importo*(100-sconto)/100);
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


	public List<DocumentoCommerciale> getRighe() {
		if(righe==null){
			righe = new ArrayList<>();
		}
		return righe;
	}
	
	public DocumentoCommerciale getSelected() {
		return getSelected();
	}


	public void remriga(){
		righe.remove(selected);
	}

	public void setSelected(DocumentoCommerciale selectedProve) {
		this.selected = selectedProve;
	}


	public void setRighe(List<DocumentoCommerciale> righe) {
		this.righe = righe;
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
	 
	 public String gonext() {
		 
		 
		 totalizza();
		 init();
		 righe = new ArrayList<>();
		 importo = 0;
		 sconto = 0;
		 iva = 0;
		 return "/pages/DocumentoComm.xhtml";
	 }


	private void totalizza() {
		
		
	}

}
