package isti.cnr.seec.doc;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class DocumentoCommerciale {

	private int numero;
	private int numc;
	private float iva;
	private float importo;
	private float riscosso;

	private float sconto;
	private float isconto;

	private boolean isReso;
	private boolean isAnnullo;
	private float imponibile;
	private float imposta;

	DocumentoCommerciale(){
		numero = 0;
		numc = 0;
		iva = 0;
		importo = 0;
		riscosso = 0;
		imponibile = 0;
		sconto = 0;
		isconto = 0;
		isReso = false;
		isAnnullo = false;
	}


	public DocumentoCommerciale(int numero, int numc, float iva, float importo, float riscosso, float sconto,
			boolean isReso, boolean isAnnullo) {
		super();
		this.numero = numero;
		this.numc = numc;
		this.iva = iva;
		this.importo = importo;
		this.riscosso = riscosso;
		this.sconto = sconto;
		this.isReso = isReso;
		this.isAnnullo = isAnnullo;
		//float temp = (importo*(100-sconto)/100);
		isconto = importo - riscosso;
		this.imponibile = ((riscosso)*100)/(100+iva);
		this.imposta = riscosso-imponibile;
		
	}




	public float getImposta() {
		return imposta;
	}


	public void setImposta(float imposta) {
		this.imposta = imposta;
	}


	public float getImponibile() {
		return imponibile;
	}


	public void setImponibile(float imponibile) {
		this.imponibile = imponibile;
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

	public float getSconto() {
		return sconto;
	}

	public void setSconto(float sconto) {

		this.sconto = sconto;
		if(sconto>0){
			float temp = (riscosso*(100-sconto)/100);
			isconto = riscosso - temp;
			this.riscosso = (riscosso*(100-sconto)/100);
			
			this.imponibile = (riscosso*100)/(100+iva);
			this.imposta = imponibile*iva/100;
		}else{
			this.riscosso = (importo*(100-sconto)/100);
			
			this.imponibile = (importo*100)/(100+iva);
			this.imposta = imponibile*iva/100;
		}
	}



	

	public float getIsconto() {
		return isconto;
	}


	public void setIsconto(float isconto) {
		this.isconto = isconto;
	}


	public boolean isReso() {
		return isReso;
	}

	public void setReso(boolean isReso) {
		this.isReso = isReso;
	}

	public boolean isAnnullo() {
		return isAnnullo;
	}

	public void setAnnullo(boolean isAnnullo) {
		this.isAnnullo = isAnnullo;
	}




}
