package it.tasgroup.services.iuvgenerator;


import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.nch.is.fo.tributi.IUVSequence;
import it.nch.is.fo.tributi.IUVSequencePK;
import it.nch.is.fo.tributi.TributoEnte;




@Stateless
public class IUVGenerator implements IUVGeneratorLocal {

	private static final BigInteger BIG_INT_93 = new BigInteger("93");
	
	@PersistenceContext(unitName="IrisPU")
	public EntityManager manager;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<String> IUVGeneratorByTributoEnte(TributoEnte trib,int numToBook) {
		
		logger.debug("IUVGenerator::IUVGeneratorByTributoEnte BEGIN");
		

		// Check condizioni limite
		if (trib == null) {
			throw new NullPointerException("TributiEnti");
		} else if (numToBook < 0) {
			throw new IllegalArgumentException("numToBook");
		} else if (numToBook == 0) {
			return new ArrayList<String>();
		}
		
		// Prenotazione range di IUV e acquisizione del primo disponibile per ente tributo
		final String idEnte = trib.getIdEnte();
		String cdTrbEnte = new String(trib.getCdTrbEnte());
	
		if ("E".equals(trib.getIuvGenSeqType())) {
			cdTrbEnte = new String("*");
		}
		
		final BigInteger firstAvailableSequenceNo = reserveCoreIUVsForTributoEnteAndGetFirstAvailable(idEnte, cdTrbEnte, numToBook);

		// Generazione pacchetto di IUV
		String ndpIUVStartNum = trib.getNdpIuvStartNum().toString();
		
		final String ndpCodStazPa = normalizeAsTwoDigitsString(trib.getNdpCodStazPa());
		final String ndpCodSegregaz = normalizeAsTwoDigitsString(trib.getNdpCodSegr());
		final String ndpAuxDigit = normalizeAsOneDigitString(trib.getNdpAuxDigit());
		List<String> iuvList = composeIUVList(new BigInteger(ndpIUVStartNum), ndpCodStazPa, ndpAuxDigit, ndpCodSegregaz, firstAvailableSequenceNo, numToBook);

		logger.debug("IUVGenerator::IUVGeneratorByTributoEnte END");
		return iuvList;
	}
	
	private BigInteger reserveCoreIUVsForTributoEnteAndGetFirstAvailable(String idEnte, String cdTrbEnte, int numToBook) {
		logger.debug("IUVGenerator::reserveCoreIUVsForTributoEnte BEGIN");

		Query qUpd = manager.createNamedQuery("PrenotaIUVByIdEnteAndTributo");
		qUpd.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
		qUpd.setParameter("opAggiornamento", "LOADER");
		qUpd.setParameter("numToBook", new BigInteger(numToBook + ""));
		qUpd.setParameter("idEnte", idEnte);
		qUpd.setParameter("cdTrbEnte", cdTrbEnte);
		int numUpdate = qUpd.executeUpdate();
		if (numUpdate <= 0) {
			logger.error("IUVGenerator::reserveCoreIUVsForTributoEnteAndGetFirstAvailable executeUpdate() nessuna riga aggiornata !!! ");
			logger.error("IUVGenerator::reserveCoreIUVsForTributoEnteAndGetFirstAvailable controllare la configurazione di SEQ_IUV !!! ");
			logger.error("IUVGenerator::reserveCoreIUVsForTributoEnteAndGetFirstAvailable idEnte = " + idEnte + ", cdTrbEnte = " + cdTrbEnte);
			throw new RuntimeException("reserveCoreIUVsForTributoEnteAndGetFirstAvailable - Update failed");
		}
		manager.flush();

		IUVSequencePK iuvSeqPk = new IUVSequencePK(idEnte, cdTrbEnte);
		IUVSequence iuvSeq = manager.find(IUVSequence.class, iuvSeqPk);
		BigInteger firstCoreIuv = iuvSeq.getLastSeqIuv().subtract(new BigInteger(numToBook + "")).add(BigInteger.ONE);

		logger.debug("IUVGenerator::reserveCoreIUVsForTributoEnte END");
		return firstCoreIuv;
	}

	// Metodi di utilita'

	private static List<String> composeIUVList(BigInteger ndpIUVStartNum, String ndpCodStazPa, String ndpAuxDigit, String codSegreg, BigInteger firstAvailableSequenceNo, int numToBook) {
		final Format formatter13Zeroes = new DecimalFormat("0000000000000");
		final Format formatter15Zeroes = new DecimalFormat("000000000000000");
		final Format formatter17Zeroes = new DecimalFormat("00000000000000000");
		final Format formatter2Zeroes = new DecimalFormat("00");

		List<String> iuvList = new ArrayList<String>();
		BigInteger currentIUVCheckLess = adaptNdpIUVStartNum(ndpIUVStartNum, ndpAuxDigit).add(firstAvailableSequenceNo);
		for (int i = 0; i < numToBook; i++) {
			String currentIUVCheckLessAsString = null;
			//
			if (ndpAuxDigit.equals("0") || ndpAuxDigit.equals("3")) {
				currentIUVCheckLessAsString = formatter13Zeroes.format(currentIUVCheckLess);
			} else if (ndpAuxDigit.equals("1")) {
				currentIUVCheckLessAsString = formatter17Zeroes.format(currentIUVCheckLess);
			} else if (ndpAuxDigit.equals("2")) {
				currentIUVCheckLessAsString = formatter15Zeroes.format(currentIUVCheckLess);
			}
			String baseForMod93String = null;
			String mod93AsString = null;
			BigInteger mod93 = null;
			if (ndpAuxDigit.equals("0")) {
				baseForMod93String = ndpAuxDigit + ndpCodStazPa + currentIUVCheckLessAsString;
				mod93 = (new BigInteger(baseForMod93String)).mod(BIG_INT_93);
				mod93AsString = formatter2Zeroes.format(mod93); // Controllo modulo 93
			} else if (ndpAuxDigit.equals("1")) {
				mod93AsString = "";
			} else if (ndpAuxDigit.equals("2")) {
				baseForMod93String = ndpAuxDigit + currentIUVCheckLessAsString;
				mod93 = (new BigInteger(baseForMod93String)).mod(BIG_INT_93);
				mod93AsString = formatter2Zeroes.format(mod93); // Controllo modulo 93
			} else if (ndpAuxDigit.equals("3")) {
				baseForMod93String = ndpAuxDigit + codSegreg + currentIUVCheckLessAsString;
				mod93 = (new BigInteger(baseForMod93String)).mod(BIG_INT_93);
				mod93AsString = formatter2Zeroes.format(mod93); // Controllo modulo 93
				currentIUVCheckLessAsString = codSegreg + currentIUVCheckLessAsString;
			}
			String currentIUV = currentIUVCheckLessAsString + mod93AsString;
			iuvList.add(currentIUV);
			currentIUVCheckLess = currentIUVCheckLess.add(BigInteger.ONE);
		}
		return iuvList;
	}

	private static BigInteger adaptNdpIUVStartNum(BigInteger ndpIUVStartNum, String ndpAuxDigit) {
		if (ndpAuxDigit.equals("0") || ndpAuxDigit.equals("3")) {
			return ndpIUVStartNum;
		} else if (ndpAuxDigit.equals("1")) {
			return ndpIUVStartNum.multiply(new BigInteger("10000"));
		} else if (ndpAuxDigit.equals("2")) {
			return ndpIUVStartNum.multiply(new BigInteger("100"));
		}
		return ndpIUVStartNum;
	}

	private static String normalizeAsOneDigitString(String string) {
		if (StringUtils.isBlank(string)) {
			return "0";
		}
		String trimmedString = string.trim();
		int len = trimmedString.length();
		if (len == 1) {
			if (Character.isDigit(trimmedString.charAt(0))) {
				return trimmedString;
			}
		}
		throw new IllegalArgumentException("\"" + string + "\"");
	}

	private static String normalizeAsTwoDigitsString(String string) {
		if (StringUtils.isBlank(string)) {
			return "00";
		}
		String trimmedString = string.trim();
		int len = trimmedString.length();
		if (len == 1) {
			if (Character.isDigit(trimmedString.charAt(0))) {
				return "0" + trimmedString;
			}
		} else if (len == 2) {
			if (Character.isDigit(trimmedString.charAt(1)) && Character.isDigit(trimmedString.charAt(0))) {
				return trimmedString;
			}
		}
		throw new IllegalArgumentException("\"" + string + "\"");
	}

}
