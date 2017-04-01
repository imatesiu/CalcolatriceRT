package cnr.isti.seec.doc.pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import cnr.isti.seec.doc.DocumentoCommerciale;
import cnr.isti.ssec.data.corrispettivi.DatiCorrispettiviType;
import cnr.isti.ssec.data.corrispettivi.DatiRegistratoriTelematiciType;
import cnr.isti.ssec.data.corrispettivi.ElencoCorrispettiviType;
import cnr.isti.ssec.data.corrispettivi.IVAType;
import cnr.isti.ssec.data.corrispettivi.NaturaType;

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

	Map<Float,DatiRegistratoriTelematiciType>	mapiva = new HashMap<>();
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
	
	


	public Map<Float, DatiRegistratoriTelematiciType> getMapiva() {
		return mapiva;
	}


	public void setMapiva(Map<Float, DatiRegistratoriTelematiciType> mapiva) {
		this.mapiva = mapiva;
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
	
	public  String generatexml(){
		totalizza();
		writeTo();
		init();
		righe = new ArrayList<>();
		importo = 0;
		sconto = 0;
		iva = 0;
		return "/pages/Chiusura.xhtml";
	}


	private void totalizza() {
		

		for (DocumentoCommerciale documentoCommerciale : righe) {
			if(mapiva.containsKey(documentoCommerciale.getIva())){
				DatiRegistratoriTelematiciType Rt = mapiva.get(documentoCommerciale.getIva());
				if(!Reso & !Annullo){
				if(Rt.getIVA()!=null)
					Rt.getIVA().setImposta(Rt.getIVA().getImposta().add(new BigDecimal(documentoCommerciale.getImposta())));
				//if(!Reso & !Annullo)
					Rt.setAmmontare(Rt.getAmmontare().add(new BigDecimal(documentoCommerciale.getImponibile())));
				}
				if(Annullo)
					Rt.setTotaleAmmontareAnnulli(Rt.getTotaleAmmontareAnnulli().add(new BigDecimal(documentoCommerciale.getRiscosso())));
				if(Reso)
					Rt.setTotaleAmmontareResi(Rt.getTotaleAmmontareResi().add(new BigDecimal(documentoCommerciale.getRiscosso())));
			}else{
				DatiRegistratoriTelematiciType Rt = new DatiRegistratoriTelematiciType();
				
				if(iva>0){
					IVAType ivat = new IVAType();
					ivat.setAliquotaIVA(new BigDecimal(iva));
					if(!Reso & !Annullo)
					ivat.setImposta(new BigDecimal(documentoCommerciale.getImposta()));
					Rt.setIVA(ivat);
				}else{
					Rt.setNatura(NaturaType.N_2);
				}
				if(!Reso & !Annullo)
				Rt.setAmmontare(new BigDecimal(documentoCommerciale.getImponibile()));
				if(Annullo)
				Rt.setTotaleAmmontareAnnulli(new BigDecimal(documentoCommerciale.getRiscosso()));
				if(Reso)
				Rt.setTotaleAmmontareResi(new BigDecimal(documentoCommerciale.getRiscosso()));
				mapiva.put(iva, Rt);
			}
		}

	}
	
	private void writeTo(){
		DatiCorrispettiviType DCT = new DatiCorrispettiviType();
		ElencoCorrispettiviType elenco = new ElencoCorrispettiviType();
		DCT.setDatiRT(elenco);
		for ( DatiRegistratoriTelematiciType datiRT : mapiva.values()) {
			elenco.setRiepilogo(datiRT);
		}
		
		JAXBContext jaxbCtx;
		try {
			jaxbCtx = javax.xml.bind.JAXBContext.newInstance(DatiCorrispettiviType.class);

			Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			//marshaller.marshal(annotatedCollaborativeContentAnalysis, System.out);
			String timeStamp = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss").format(new Date());
			
			File theDir = new File("received_");
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				theDir.mkdir();
			}

			OutputStream os = new FileOutputStream( "received_/RT_"+timeStamp+"_.xml" );
			
			marshaller.marshal( DCT, System.out );

			marshaller.marshal( DCT, os );
			StringWriter sw = new StringWriter();
			marshaller.marshal( DCT, sw);
			 sw.toString();
			
		} catch (JAXBException | FileNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			

		}
	}


}
