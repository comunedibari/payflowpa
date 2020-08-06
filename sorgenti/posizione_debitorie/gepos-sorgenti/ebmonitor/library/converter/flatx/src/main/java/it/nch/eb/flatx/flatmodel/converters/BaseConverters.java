/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.flatmodel.converters;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.flatx.bean.types.DateValidateConverter;
import it.nch.eb.flatx.bean.types.EnumConverter;
import it.nch.eb.flatx.bean.types.FillerConverter;


/**
 * @author gdefacci
 */
public interface BaseConverters {

	Conversions types = new Conversions();

	FillerConverter fill1 = types.createSizedType(1);
	FillerConverter fill117=types.createSizedType(117);
	FillerConverter fill107NoTrim=types.createSizedType(types.AS_IS, 107, "", Alignments.LEFT);
	FillerConverter fill78 = types.createSizedType(78);
    	FillerConverter fill104 = types.createSizedType(104);
	FillerConverter fill105  = types.createSizedType(105);
	FillerConverter fill1025  = types.createSizedType(1025);
	FillerConverter fill140  = types.createSizedType(140);
	FillerConverter fill175  = types.createSizedType(175);
	FillerConverter fill1895  = types.createSizedType(1895);
	FillerConverter fill1896  = types.createSizedType(1896);
	FillerConverter fill1913  = types.createSizedType(1913);
	FillerConverter fill1915  = types.createSizedType(1915);
	FillerConverter fill53 = types.createSizedType(53);
	FillerConverter fill69 = types.createSizedType(69);
	
	FillerConverter fill10 = types.createSizedType(10);
	FillerConverter fill11 = types.createSizedType(11);
	FillerConverter fill12 = types.createSizedType(12);
	FillerConverter fill13 = types.createSizedType(13);
	FillerConverter fill14 = types.createSizedType(14);
	FillerConverter fill15 = types.createSizedType(15);
	FillerConverter fill16 = types.createSizedType(16);
	FillerConverter fill17 = types.createSizedType(17);
	FillerConverter fill18 = types.createSizedType(18);
	FillerConverter fill19 = types.createSizedType(19);
	FillerConverter fill2 = types.createSizedType(2);
	FillerConverter fill20 = types.createSizedType(20);

	FillerConverter fill206 = types.createSizedType(206);

	FillerConverter fill21 = types.createSizedType(21);
	FillerConverter fill23 = types.createSizedType(23);
	FillerConverter fill24 = types.createSizedType(24);
	FillerConverter fill25 = types.createSizedType(25);
	FillerConverter fill26 = types.createSizedType(26);
	FillerConverter fill27 = types.createSizedType(27);
	FillerConverter fill28 = types.createSizedType(28);
	FillerConverter fill29 = types.createSizedType(29);
	FillerConverter fill256 = types.createSizedType(256);
	FillerConverter fill3 = types.createSizedType(3);
	FillerConverter fill3NoTrim = types.createSizedType(3, " ", Alignments.RIGHT);
	FillerConverter fill30 = types.createSizedType(30);
	FillerConverter fill31 = types.createSizedType(31);
	FillerConverter fill32 = types.createSizedType(32);
	FillerConverter fill34 = types.createSizedType(34);
	FillerConverter fill35 = types.createSizedType(35);
	FillerConverter fill38 = types.createSizedType(38);
	FillerConverter fill39 = types.createSizedType(39);
	FillerConverter fill350 = types.createSizedType(350);
	FillerConverter fill4 = types.createSizedType(4);
	FillerConverter fill40 = types.createSizedType(40);
	FillerConverter fill41 = types.createSizedType(41);
	FillerConverter fill42 = types.createSizedType(42);
	FillerConverter fill43 = types.createSizedType(43);
	FillerConverter fill44 = types.createSizedType(44);
	FillerConverter fill45 = types.createSizedType(45);
	FillerConverter fill46 = types.createSizedType(46);
	FillerConverter fill47 = types.createSizedType(47);
	FillerConverter fill48 = types.createSizedType(48);
	FillerConverter fill5 = types.createSizedType(5);
	FillerConverter fill50 = types.createSizedType(50);
	FillerConverter fill51 = types.createSizedType(51);
	FillerConverter fill58 = types.createSizedType(58);
	FillerConverter fill500 = types.createSizedType(500);
	FillerConverter fill59 = types.createSizedType(59);
	FillerConverter fill6 = types.createSizedType(6);
	FillerConverter fill60 = types.createSizedType(60);
	FillerConverter fill61 = types.createSizedType(61);
	FillerConverter fill62 = types.createSizedType(62);
	FillerConverter fill54 = types.createSizedType(65);
	FillerConverter fill67 = types.createSizedType(67);
	FillerConverter fill68 = types.createSizedType(68);
	FillerConverter fill7 = types.createSizedType(7);
	FillerConverter fill70 = types.createSizedType(70);
	FillerConverter fill75 = types.createSizedType(75);
	FillerConverter fill76 = types.createSizedType(76);
	FillerConverter fill80 = types.createSizedType(80);
	FillerConverter fill81 = types.createSizedType(81);
	FillerConverter fill83 = types.createSizedType(83);
	FillerConverter fill8 = types.createSizedType(8);
	FillerConverter fill9 = types.createSizedType(9);
	FillerConverter fill90 = types.createSizedType(90);
	FillerConverter fill98 = types.createSizedType(98);
	FillerConverter fill110 = types.createSizedType(110);
	FillerConverter fill120 = types.createSizedType(120);
	FillerConverter rfill12 = types.createSizedType(12, " ", Alignments.RIGHT);

	FillerConverter date6 = types.createSizedType(types.DATE6, 6);
	FillerConverter date8 = types.createSizedType(types.DATE8, 8);
	FillerConverter date10 = types.createSizedType(types.DATE10, 10);
	FillerConverter date10It = types.createSizedType(types.DATE10_IT, 10);

	FillerConverter dateTime19 = types.createSizedType(types.ISO_DATE_TIME, 19);
	FillerConverter dateTime19ToXml = types.createDateSizedType(types.ISO_DATE_TIME_TO_XML);

	FillerConverter xmlFill70 = types.createSizedType(new RestoreXmlEntitiesConverter(), 70);

	FillerConverter xsDateTo_ddMMyy = types.createSizedType(types.fromXsDateTo("ddMMyy"), 6);

	FillerConverter dateTo_ddMMyy = types.createSizedType(types.fromSimpleDateTo("ddMMyy"), 6);
	FillerConverter ddMMyy = types.createSizedType(new DateValidateConverter("ddMMyy"), 6);

	FillerConverter fd_number11_10 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[10], 11, "0", Alignments.RIGHT);
	FillerConverter fd_number13_2 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[2], 13, "0", Alignments.RIGHT);
	FillerConverter fd_number15_2 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[2], 15, "0", Alignments.RIGHT);
	FillerConverter fd_number17_2 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[2], 17, "0", Alignments.RIGHT);
	FillerConverter fd_number18_2 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[2], 18, "0", Alignments.RIGHT);
	FillerConverter fd_number18_3 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[3], 18, "0", Alignments.RIGHT);
	FillerConverter fd_number18_5 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[5], 18, "0", Alignments.RIGHT);
	FillerConverter fd_number18_8 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[8], 18, "0", Alignments.RIGHT);
	FillerConverter fd_number20_2 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[2], 20, "0", Alignments.RIGHT);
	FillerConverter fd_number21_10 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[10], 21, "0", Alignments.RIGHT);
	FillerConverter fd_number23_5 = types.createSizedType(types.NUMBER_FIXED_DECIMALS[5], 23, "0", Alignments.RIGHT);

	FillerConverter	numb2	= types.createSizedType(types.LONG, 2, "0",Alignments.RIGHT);
	FillerConverter	numb3	= types.createSizedType(types.LONG, 3, "0",Alignments.RIGHT);
	FillerConverter numb4 = types.createSizedType(types.LONG, 4, "0",Alignments.RIGHT);
	FillerConverter numb5 = types.createSizedType(types.LONG, 5, "0",Alignments.RIGHT);
	FillerConverter numb6 = types.createSizedType(types.LONG, 6, "0",Alignments.RIGHT);
	FillerConverter numb7 = types.createSizedType(types.LONG, 7, "0",Alignments.RIGHT);
	FillerConverter numb9 = types.createSizedType(types.LONG, 9, "0",Alignments.RIGHT);
	FillerConverter numb13 = types.createSizedType(types.LONG, 13, "0",Alignments.RIGHT);
	FillerConverter numb15 = types.createSizedType(types.LONG, 15, "0",Alignments.RIGHT);
	FillerConverter numb20 = types.createSizedType(types.LONG, 20, "0",Alignments.RIGHT);
	FillerConverter numb35 = types.createSizedType(types.LONG, 35, "0",Alignments.RIGHT);


}
