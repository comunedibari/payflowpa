group ToXmlRecord;

imports()  ::= <<
import it.nch.eb.converter.ConversionsConsts;
import it.nch.eb.converter.conversioninfo.IncrementValue;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.RecordImpl;
import it.nch.eb.flatx.flatmodel.converters.FromXmlConverters;
>>

compilationUnitDecl(clasDeclModel) ::= <<

package $clasDeclModel.packageName$;

$imports()$

/* generated code */
$classDecl( clasDeclModel = clasDeclModel )$
	
>>

classDecl(clasDeclModel) ::= <<
public class $clasDeclModel.name$ extends RecordImpl implements FromXmlConverters, ConversionsConsts {
	
	public final Converter	incrementRecCount	= create(1, new IncrementValue(REC_COUNT));
	
	$clasDeclModel.fields: { fld |
		$if (fld.initializer)$
			$fld.modifiers; separator=" "$ $fld.type$ $fld.fieldName$ = $fld.initializer$;
		$else$
			$fld.modifiers; separator=" "$ $fld.type$ $fld.fieldName$;
		$endif$  
	}$
	
	public final Converter	lineTerminator		= createFixedValue(767, "\n");
	
}
>>