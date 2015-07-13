package ch.vd.demaut.domain.model.spools;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Generator for Spool Name
 *
 */
public class SpoolNameGenerator {

	// ********************************************************* Implemented Services
	static public String generateSpoolName(Spool spool, String heading) {
		StringBuffer spoolName = new StringBuffer();
		spoolName.append(heading);
		spoolName.append(printSize(spool));
		spoolName.append(printDispatchCode(spool));
		spoolName.append("_");
		spoolName.append(printRegionCode(spool));
		spoolName.append("_");
		spoolName.append(printIncrement(spool));
		spoolName.append("_");
		spoolName.append(printDate(spool.getCreationDateTime()));
		spoolName.append("_");
		spoolName.append(printTime(spool.getCreationDateTime()));
		return spoolName.toString(); 

	}

	// ********************************************************* Private methods
	static private String printRegionCode(Spool spool) {
		return spool.getRegionCode().getCode();
	}

	static private String printDispatchCode(Spool spool) {
		return spool.getDispatchCode().getCode();
	}
	
	static private String printSize(Spool spool) {
		switch (spool.getSize()) {
		case C4:
			return "4";
		case C5:
			return "5";
		case X:
			return "X";
		default:
			throw new AssertionError();
		}
	}
	
	static private String printIncrement(Spool spool) {
		NumberFormat formatter = new DecimalFormat("000");
		String incrementStr = formatter.format(spool.getIncrement());
		return incrementStr;
	}
	
	
	static private String printDate(DateTime creationDate) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		String dateStr = fmt.print(creationDate);
		return dateStr;
	}

	static private String printTime(DateTime creationDate) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("HHmmss");
		String timeStr = fmt.print(creationDate);
		return timeStr;
	}
	


}
