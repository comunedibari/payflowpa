/**
 * Created on 03/lug/2008
 */
package it.nch.eb.flatx.xml;

import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.RecordImpl;
import it.nch.eb.flatx.flatmodel.converters.BaseConverters;


/**
 * @author gdefacci
 */
public class ItemRecord extends RecordImpl implements BaseConverters {
	
	private static final long	serialVersionUID	= 1L;

	public ItemRecord() {
		super("/sample/doc/items/item");
	}
	
	public final Converter docName = createXPath(fill7, "../../name");
	public final Converter itemsName = createXPath(fill7, "../name");
	public final Converter product = createXPath(fill7, "product");
	public final Converter priceOrQuantity = create(fill10, or(new String[] {
		"quantity", "price"	
	}));
	
	public final Converter total = createXPath(fill10, "../../data/total");
	
	public final Converter note = create(fill10, or( new String[] {
		"../../data/description", 
		"../../data/note"
	}));
	
	public final Converter itemDescr = create(fill30, 
			or().or("idescr")
				.or("../../data/description") 
				.or("../../data/note") );
	
}

