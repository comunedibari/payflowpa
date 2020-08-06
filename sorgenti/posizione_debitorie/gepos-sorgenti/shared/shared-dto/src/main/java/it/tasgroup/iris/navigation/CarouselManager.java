/**
 * 
 */
package it.tasgroup.iris.navigation;



import it.tasgroup.iris.dto.anonymous.payment.AnonymousCustomTributoEnteDTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * @author pazzik
 *
 */
public class CarouselManager {
	
	
	
	public static List<List<CarouselItem>> getCarouselPages(HttpSession session, String localeKey) {
		CarouselConfiguration carouselConf = new CarouselConfiguration();
		List<CarouselItem> cList = new ArrayList<CarouselItem>();
		List<List<CarouselItem>> carouselPages = new ArrayList<List<CarouselItem>>();
		
		if (ConfigurationPropertyLoader.getInstance("iris-fe.properties").getBooleanProperty("homepage.print.carousel")) {
			try {
				ConfigurationPropertyLoader props = ConfigurationPropertyLoader
						.getInstance("public/paytas-public.properties");
				String PUBLIC_CONTEXT_ROOT = props.getProperty("paytas.public.context.root");
				List<AnonymousCustomTributoEnteDTO> list = new CustomNavigationFileCfgParser().parse(CustomNavigationFileCfgParser.getFileNameByIdEnte("00000000000000000000"), PUBLIC_CONTEXT_ROOT);
				Iterator<AnonymousCustomTributoEnteDTO> iter = list.iterator();
				while (iter.hasNext()) {
					AnonymousCustomTributoEnteDTO d = iter.next();
					if (d.isShowInCarousel()) {
						CarouselItem cItem = new CarouselItem();
						cItem.setImg(d.getImg());
						cItem.setIdEnte("00000000000000000000");
						cItem.setCarouselIndex(d.getCarouselOrder() + "");
						cItem.setCaption(d.getTitolo());
						cItem.setLinkCaption("Paga");
						cList.add(cItem);
					}
				}
			} catch (Throwable t) {
//				t.printStackTrace();
			}
		} 
		carouselConf.setItems(cList);
		if (cList.isEmpty()) {
			carouselConf.setEnabled(false);
		} else {
			carouselConf.setEnabled(true);
			// Raggruppo i carouselItems in sotto liste di 4 per pagina.
			int i = 0;
			List<CarouselItem> pageList = new ArrayList<CarouselItem>();
			for (CarouselItem item : carouselConf.getItems()) {
				pageList.add(item);
				i = i + 1;
				if ((i % 4) == 0) {
					carouselPages.add(pageList);
					pageList = new ArrayList<CarouselItem>();
				}
			}
			if (!pageList.isEmpty())
				carouselPages.add(pageList);
		}
		return carouselPages;
	}
}
