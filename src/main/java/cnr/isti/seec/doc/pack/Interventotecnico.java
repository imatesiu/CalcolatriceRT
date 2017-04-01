package cnr.isti.seec.doc.pack;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class Interventotecnico {

	
	 private Map<String,String> interventi = new HashMap<String, String>();
	 private Map<String, Integer> codici;
	 private String intervento;
	 private Integer codice;
	 
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
	 
	 
	 
	 
}
