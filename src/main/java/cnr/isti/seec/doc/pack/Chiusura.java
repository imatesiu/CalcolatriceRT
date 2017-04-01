package cnr.isti.seec.doc.pack;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@SessionScoped
public class Chiusura {
	
	private String XmlString;
	
	

	public String getXmlString() {
		return XmlString;
	}

	public void setXmlString(String xmlString) {
		XmlString = xmlString;
		System.out.println("chiusura\n\r\n\r");
	}
	
	

}
