package it.tasgroup.idp.cart.core.quadratura.pdf;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsCustomizer;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;

import java.awt.Color;
import java.io.InputStream;
import java.util.Locale;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

public class Templates {
	public static final String TITOLO_REGIONE_TOSCANA = "Regione Toscana";
	public static final String TITOLO_IRIS = "IRIS";
	public static final String TITOLO_PAGAMENTI = "Pagamenti online Regione Toscana";
	public static final StyleBuilder rootStyle;
	public static final StyleBuilder fontStyle12;
	public static final StyleBuilder fontStyle18;
	public static final StyleBuilder fontStyleRed18;
	public static final StyleBuilder fontStyleRed22;
	public static final StyleBuilder fontStyleRedCentered22;
	public static final StyleBuilder boldStyle;
	public static final StyleBuilder italicStyle;
	public static final StyleBuilder boldCenteredStyle;
	public static final StyleBuilder bold12CenteredStyle;
	public static final StyleBuilder bold18CenteredStyle;
	public static final StyleBuilder bold22CenteredStyle;
	public static final StyleBuilder boldLeftStyle;
	public static final StyleBuilder bold12LeftStyle;
	public static final StyleBuilder bold18LeftStyle;
	public static final StyleBuilder bold22LeftStyle;
	public static final StyleBuilder columnStyle;
	public static final StyleBuilder columnTitleStyle;
	public static final StyleBuilder groupStyle;
	public static final StyleBuilder subtotalStyle;
	public static final StyleBuilder centeredStyle;
	
	public static final FontBuilder categoryAxisFont;
	public static final FontBuilder rootFont;

	public static final ReportTemplateBuilder reportTemplate;
	public static final CurrencyType currencyType;
	public static final ComponentBuilder<?, ?> dynamicReportsComponent;
	public static final ComponentBuilder<?, ?> footerComponent;

	static {
		rootFont = stl.font().setFontSize(10);
		rootStyle = stl.style().setPadding(2).setFont(rootFont);
		fontStyle12 = stl.style(rootStyle).setFontSize(12);
		fontStyle18 = stl.style(rootStyle).setFontSize(18);
		fontStyleRed18 = stl.style(rootStyle).setFontSize(18).setForegroundColor(Color.RED); 
		fontStyleRed22 = stl.style(rootStyle).setFontSize(22).setForegroundColor(Color.RED);
		fontStyleRedCentered22 = stl.style(fontStyleRed22).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
		boldStyle           = stl.style(rootStyle).bold();
		italicStyle         = stl.style(rootStyle).italic();
		centeredStyle   = stl.style(rootStyle).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
		boldCenteredStyle   = stl.style(boldStyle)
				.setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
		bold12CenteredStyle = stl.style(boldCenteredStyle)
				.setFontSize(12);
		bold18CenteredStyle = stl.style(boldCenteredStyle)
				.setFontSize(18);
		bold22CenteredStyle = stl.style(boldCenteredStyle)
				.setFontSize(22);
		boldLeftStyle   = stl.style(boldStyle)
				.setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
		bold12LeftStyle = stl.style(boldLeftStyle)
				.setFontSize(12);
		bold18LeftStyle = stl.style(boldLeftStyle)
				.setFontSize(18);
		bold22LeftStyle = stl.style(boldLeftStyle)
				.setFontSize(20);
		columnStyle         = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
		columnTitleStyle    = stl.style(columnStyle)
				.setBorder(stl.pen1Point())
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
				.setBackgroundColor(Color.LIGHT_GRAY)
				.bold();
		groupStyle          = stl.style(boldStyle)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
		subtotalStyle       = stl.style(boldStyle)
				.setTopBorder(stl.pen1Point());

		StyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle);
		StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle)
				.setBackgroundColor(new Color(170, 170, 170));
		StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle)
				.setBackgroundColor(new Color(140, 140, 140));
		StyleBuilder crosstabCellStyle       = stl.style(columnStyle)
				.setBorder(stl.pen1Point());

		TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
				.setHeadingStyle(0, stl.style(rootStyle).bold());

		reportTemplate = template()
				.setLocale(Locale.ITALIAN)
				.setColumnStyle(columnStyle)
				.setColumnTitleStyle(columnTitleStyle)
				.setGroupStyle(groupStyle)
				.setGroupTitleStyle(groupStyle)
				.setSubtotalStyle(subtotalStyle)
				.highlightDetailEvenRows()
				.crosstabHighlightEvenRows()
				.setCrosstabGroupStyle(crosstabGroupStyle)
				.setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
				.setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
				.setCrosstabCellStyle(crosstabCellStyle)
				.setTableOfContentsCustomizer(tableOfContentsCustomizer);

		currencyType = new CurrencyType();

		InputStream immagineRegioneToscana = Templates.class.getResourceAsStream("/images/regione.gif");
		dynamicReportsComponent =
				cmp.horizontalList(
						cmp.horizontalList(
								cmp.image(immagineRegioneToscana).setFixedDimension(60,60),
								cmp.horizontalGap(10),
								cmp.text(TITOLO_REGIONE_TOSCANA).setStyle(bold22CenteredStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
								),
						cmp.verticalList(
								cmp.text(TITOLO_IRIS).setStyle(fontStyleRedCentered22),
								cmp.text(TITOLO_PAGAMENTI).setStyle(fontStyle12).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
								))
				.newRow()
				.add(cmp.line())
				.newRow()
				.add(cmp.verticalGap(10));
				;

		footerComponent = cmp.pageXofY()
				.setStyle(
						stl.style(boldCenteredStyle)
						.setTopBorder(stl.pen1Point()));
		
		categoryAxisFont = stl.font().setFontSize(10);
	}

	public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
		return new CurrencyValueFormatter(label);
	}

	public static class CurrencyType extends BigDecimalType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "€ #,###.00";
		}
	}

	private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;

		private String label;

		public CurrencyValueFormatter(String label) {
			this.label = label;
		}

		@Override
		public String format(Number value, ReportParameters reportParameters) {
			return this.label + currencyType.valueToString(value, reportParameters.getLocale());
		}
	}
}
