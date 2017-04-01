package cnr.isti.seec.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import cnr.isti.seec.doc.DocumentoCommerciale;
import cnr.isti.ssec.data.corrispettivi.DatiCorrispettiviType;
import cnr.isti.ssec.data.corrispettivi.DatiRegistratoriTelematiciType;
import cnr.isti.ssec.data.corrispettivi.ElencoCorrispettiviType;
import cnr.isti.ssec.data.corrispettivi.IVAType;
import cnr.isti.ssec.data.corrispettivi.IdFiscaleType;
import cnr.isti.ssec.data.corrispettivi.InterventoTecnicoType;
import cnr.isti.ssec.data.corrispettivi.NaturaType;

public class util {

	public static void totalizza(List<DocumentoCommerciale> righe, Map<Float, DatiRegistratoriTelematiciType> mapiva,
			boolean Reso, boolean Annullo) {

		for (DocumentoCommerciale documentoCommerciale : righe) {
			float iva = documentoCommerciale.getIva();
			if (mapiva.containsKey(iva)) {
				DatiRegistratoriTelematiciType Rt = mapiva.get(documentoCommerciale.getIva());
				if (!Reso & !Annullo) {
					if (Rt.getIVA() != null)
						Rt.getIVA().setImposta(
								Rt.getIVA().getImposta().add(new BigDecimal(documentoCommerciale.getImposta())));
					// if(!Reso & !Annullo)
					Rt.setAmmontare(Rt.getAmmontare().add(new BigDecimal(documentoCommerciale.getImponibile())));
				}
				if (Annullo)
					Rt.setTotaleAmmontareAnnulli(
							Rt.getTotaleAmmontareAnnulli().add(new BigDecimal(documentoCommerciale.getRiscosso())));
				if (Reso)
					Rt.setTotaleAmmontareResi(
							Rt.getTotaleAmmontareResi().add(new BigDecimal(documentoCommerciale.getRiscosso())));
			} else {
				DatiRegistratoriTelematiciType Rt = new DatiRegistratoriTelematiciType();
				if (iva > 0) {
					IVAType ivat = new IVAType();
					ivat.setAliquotaIVA(new BigDecimal(iva));
					if (!Reso & !Annullo)
						ivat.setImposta(new BigDecimal(documentoCommerciale.getImposta()));
					Rt.setIVA(ivat);
				} else {
					Rt.setNatura(NaturaType.N_2);
				}
				if (!Reso & !Annullo)
					Rt.setAmmontare(new BigDecimal(documentoCommerciale.getImponibile()));
				if (Annullo)
					Rt.setTotaleAmmontareAnnulli(new BigDecimal(documentoCommerciale.getRiscosso()));
				if (Reso)
					Rt.setTotaleAmmontareResi(new BigDecimal(documentoCommerciale.getRiscosso()));
				mapiva.put(iva, Rt);
			}
		}

	}


	public static String writeTo(Map<Float, DatiRegistratoriTelematiciType> mapiva, Integer codice){
		DatiCorrispettiviType DCT = new DatiCorrispettiviType();
		ElencoCorrispettiviType elenco = new ElencoCorrispettiviType();
		if(!mapiva.isEmpty())
		DCT.setDatiRT(elenco);
		for ( DatiRegistratoriTelematiciType datiRT : mapiva.values()) {
			elenco.setRiepilogo(datiRT);
		}

		XMLGregorianCalendar value=  getXMLGregorianCalendarNow()  ;
		DCT.setDataOraRilevazione(value);

		if(codice!=null){
			if(codice>0){
				DCT.setSimulazione("true");
				InterventoTecnicoType inter = new InterventoTecnicoType();
				inter.setCFTecnico("CF");
				inter.setCodice(codice.toString());
				IdFiscaleType icdft = new IdFiscaleType();
				icdft.setIdCodice("codice");
				icdft.setIdPaese("paese");
				inter.setIdIVALaboratorio(icdft);
				inter.setNote("note");
				DCT.getInterventoTecnico().add(inter);;
			}
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
			return  sw.toString();

		} catch (JAXBException | FileNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "";
		}
	}


	private static XMLGregorianCalendar getXMLGregorianCalendarNow() 

	{
		GregorianCalendar gregorianCalendar = new GregorianCalendar();

		try {
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

			XMLGregorianCalendar now = 
					datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);

			return now;
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
