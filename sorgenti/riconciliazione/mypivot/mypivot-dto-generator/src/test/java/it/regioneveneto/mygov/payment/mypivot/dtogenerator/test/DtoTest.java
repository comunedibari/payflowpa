package it.regioneveneto.mygov.payment.mypivot.dtogenerator.test;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class DtoTest extends DtoTestAbstract {

	private static final String PO_package = "it.regioneveneto.mygov.payment.mypivot.domain.po.";
	private static final String TO_package = "it.regioneveneto.mygov.payment.mypivot.domain.to.";
	private static final String TO_suffisso = "TO";

	@Test
	public void test() {
		// Simplest scenario. Will delegate to Podam all decisions
		PodamFactory factory = new PodamFactoryImpl();

		// This will use constructor with minimum arguments and
		// then setters to populate POJO

		String[] entityNames = { "AnagraficaStato", "Ente", "EnteTipoDovuto", "ExportRendicontazione",
				"ExportRendicontazioneId", "ImportExportRendicontazioneTesoreria",
				"ImportExportRendicontazioneTesoreriaId", "InfoFlussoPosteWeb", "ManageFlusso",
				"OperatoreEnteTipoDovuto", "PrenotazioneFlussoRiconciliazione", "RendicontazioneSubset",
				"RendicontazioneSubsetId", "Segnalazione", "TesoreriaSubset", "TesoreriaSubsetId", "TipoFlusso",
				"Utente", "FlussoRendicontazione", "FlussoExport" };
		try {
			for (String entityName : entityNames) {

				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

				Class poClass = Class.forName(PO_package + entityName);
				Class toClass = Class.forName(TO_package + entityName + TO_suffisso);
				Object po = factory.manufacturePojo(poClass);
				Object to = modelMapper.map(po, toClass);
				modelMapper.validate();
				//				Object to = ConstructorUtils.invokeConstructor(toClass, null);
				//				Object to = toClass.getConstructors()[0].newInstance();
//				PropertyUtils.copyProperties(to, po);

				//				BeanUtilsBean bub = new BeanUtilsBean();
				//				bub.copyProperties(to, po);
				//System.out.println(segnalazioneTO.toString());

				List<String> changes = compareBeans(to, po);
				if (changes != null && changes.size() > 0) {
					StringBuilder failMsg = new StringBuilder(+changes.size() + " modifiche riscontrate a [" + entityName + "]:  ");
					for (String change : changes) {
						failMsg.append("[");
						failMsg.append(change);
						failMsg.append("], ");
					}
					System.out.println( failMsg.toString());
					fail(failMsg.toString());
				} else {
					System.out.println("ok[" + entityName + "]");
				}
			}

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			fail("Impossibile completare la comparazione [" + e.getMessage() + "]");
		} catch (ClassNotFoundException e1) {
			fail("classe non esistente " + e1.getMessage());
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
