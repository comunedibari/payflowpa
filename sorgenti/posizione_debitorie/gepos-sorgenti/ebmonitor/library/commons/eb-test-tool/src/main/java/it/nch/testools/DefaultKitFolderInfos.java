/**
 * Created on 02/ott/08
 */
package it.nch.testools;

import it.nch.TestProperties;

import java.io.File;


/**
 * @author gdefacci
 */
public class DefaultKitFolderInfos {
	
	public final KitFolderInfo pmtrecBTKit;
	public final KitFolderInfo pmtrecUbiKit;
	public final KitFolderInfo advinfKit;
	public final KitFolderInfo advistrKit;
	public final KitFolderInfo esitiWIPKit;
	public final KitFolderInfo esitiCDTRKit;
	
	public final SinglePassConversions singlePassConversions;
	
	public static class SinglePassConversions {
		public final KitFolderInfo advistrKit;
		public final KitFolderInfo pmtrecBTKit;
		public final KitFolderInfo pmtrecUbiKit;
		
		private SinglePassConversions(File startteamRoot) {
			advistrKit = new KitFolderInfo(startteamRoot, "Avvisatura Bonifico/Dispositivi/AVV-ISTR", "source-xml", "target-flat-no-counters");
			pmtrecBTKit = new KitFolderInfo(startteamRoot, "Disposizioni Pagamento XML/Dispositivi/PMTREC-BT", "source-xml", "target-flat-no-counters");
			pmtrecUbiKit = new KitFolderInfo(startteamRoot, "Disposizioni Pagamento XML/Dispositivi/PMTREC-UBI", "source-xml", "target-flat-no-counters");
		};
	}
	
	public DefaultKitFolderInfos(File startteamRoot) {
		pmtrecBTKit = new KitFolderInfo(startteamRoot, "Disposizioni Pagamento XML/Dispositivi/PMTREC-BT", "source-xml", "target-flat-v");
		pmtrecUbiKit = new KitFolderInfo(startteamRoot, "Disposizioni Pagamento XML/Dispositivi/PMTREC-UBI", "source-xml", "target-flat");
		
		esitiWIPKit = new KitFolderInfo(startteamRoot, "Disposizioni Pagamento XML/Esiti/WIP", "source-flat", "target-xml");
		esitiCDTRKit = new KitFolderInfo(startteamRoot, "Disposizioni Pagamento XML/Esiti/CDTR", "source-flat", "target-xml");
		
		advinfKit = new KitFolderInfo(startteamRoot, "Avvisatura Bonifico/Informativi/AVV-BON-IN", "source-flat", "target-xml");
		advistrKit = new KitFolderInfo(startteamRoot, "Avvisatura Bonifico/Dispositivi/AVV-ISTR", "source-xml", "target-flat");
		singlePassConversions = new SinglePassConversions(startteamRoot);
	}
	
	public DefaultKitFolderInfos() {
		this(TestProperties.kit.getKitBaseFolder());
	}
	
}
