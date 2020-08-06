package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.FlussoTesoreriaDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoTesoreriaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio.BilancioContainerDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio.BilancioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoTesoreria;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Service("flussoTesoreriaService")
public class FlussoTesoreriaServiceImpl implements FlussoTesoreriaService {

	private static final String selectQueryAccertamento = "select ad.cod_ufficio, etd.de_tipo, ad.cod_capitolo, ad.cod_accertamento, sum(ad.num_importo) as sum_importo";
	private static final String fromQueryAccertamento = "from mygov_accertamento_dettaglio ad, mygov_accertamento a, mygov_anagrafica_stato an, mygov_ente_tipo_dovuto etd";
	private static final String whereQueryAccertamento = "where ad.mygov_accertamento_id = a.mygov_accertamento_id and a.mygov_anagrafica_stato_id = an.mygov_anagrafica_stato_id and a.mygov_ente_tipo_dovuto_id = etd.mygov_ente_tipo_dovuto_id";
	private static final String groupByQueryAccertamento = "group by ad.cod_ufficio, etd.de_tipo, ad.cod_capitolo, ad.cod_accertamento ";
	private static final String orderByQueryAccertamento = "order by ad.cod_ufficio, etd.de_tipo, ad.cod_capitolo, ad.cod_accertamento";

	@Autowired
	private DataSource dataSource;

	private static final Log LOG = LogFactory.getLog(FlussoTesoreriaServiceImpl.class);

	@Autowired
	ModelMapperUtil modelMapperUtil;
	@Autowired
	private FlussoTesoreriaDao flussoTesoreriaDao;

	@Override
	public FlussoTesoreriaTO findByCodIpaEnteDeAnnoBollettaAndCodBolletta(final String codIpaEnte,
			final String deAnnoBolletta, final String codBolletta) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(deAnnoBolletta, "Parametro [ deAnnoBolletta ] vuoto");
		Assert.hasText(codBolletta, "Parametro [ codBolletta ] vuoto");

		LOG.debug("Chiamata al servizio findByCodIpaEnteDeAnnoBollettaAndCodBolletta con i parametri codIpaEnte [ "
				+ codIpaEnte + " ], deAnnoBolletta [ " + deAnnoBolletta + " ] e codBolletta [ " + codBolletta + " ]");

		FlussoTesoreria flussoTesoreriaPO = flussoTesoreriaDao.findByCodIpaEnteDeAnnoBollettaAndCodBolletta(codIpaEnte,
				deAnnoBolletta, codBolletta);
		if (flussoTesoreriaPO != null) {
			FlussoTesoreriaTO flussoTesoreriaTO = modelMapperUtil.map(flussoTesoreriaPO, FlussoTesoreriaTO.class);
			return flussoTesoreriaTO;
		}
		return null;
	}

	@Override
	public PageDto<FlussoTesoreriaDto> getFlussoTesoreriaPage(String codIpaEnte, Date dt_data_valuta_da,
			Date dt_data_valuta_a, Date dt_data_contabile_da, Date dt_data_contabile_a, String deAnnoBolletta,
			String codBolletta, String importo, String conto, String codOr1, String iuv, String iuf, int page,
			int pageSize, Sort sort) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoTesoreria> flussoTesoreriaPage = getTesoreriaPageList(codIpaEnte, dt_data_valuta_da,
				dt_data_valuta_a, dt_data_contabile_da, dt_data_contabile_a, deAnnoBolletta, codBolletta, importo,
				conto, codOr1, iuv, iuf, pageable);

		List<FlussoTesoreria> flussoTesoreriaList = flussoTesoreriaPage.getContent();

		List<FlussoTesoreriaDto> flussoTesoreriaDtoList = mapPoInDto(flussoTesoreriaList);

		PageDto<FlussoTesoreriaDto> flussoTesoreriaDtoPage = new PageDto<FlussoTesoreriaDto>();
		flussoTesoreriaDtoPage.setList(flussoTesoreriaDtoList);

		flussoTesoreriaDtoPage.setNextPage(flussoTesoreriaPage.hasNextPage());
		flussoTesoreriaDtoPage.setPage(flussoTesoreriaPage.getNumber() + 1);
		flussoTesoreriaDtoPage.setPageSize(flussoTesoreriaPage.getSize());
		flussoTesoreriaDtoPage.setPreviousPage(flussoTesoreriaPage.hasPreviousPage());
		flussoTesoreriaDtoPage.setTotalPages(flussoTesoreriaPage.getTotalPages());
		flussoTesoreriaDtoPage.setTotalRecords(flussoTesoreriaPage.getTotalElements());

		return flussoTesoreriaDtoPage;

	}

	@Override
	public PageDto<FlussoTesoreriaDto> getFlussoTesoreriaPage(String codIpaEnte, Date dt_data_valuta_da,
			Date dt_data_valuta_a, Date dt_data_contabile_da, Date dt_data_contabile_a, String deAnnoBolletta,
			String codBolletta, String importo, String conto, String codOr1, String iuv, String iuf, 
			Pageable pageable) {

//		int pageToGet = 0;
//		if (page > 0) {
//			pageToGet = page - 1;
//		}
//
//		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoTesoreria> flussoTesoreriaPage = getTesoreriaPageList(codIpaEnte, dt_data_valuta_da,
				dt_data_valuta_a, dt_data_contabile_da, dt_data_contabile_a, deAnnoBolletta, codBolletta, importo,
				conto, codOr1, iuv, iuf, pageable);

		List<FlussoTesoreria> flussoTesoreriaList = flussoTesoreriaPage.getContent();

		List<FlussoTesoreriaDto> flussoTesoreriaDtoList = mapPoInDto(flussoTesoreriaList);

		PageDto<FlussoTesoreriaDto> flussoTesoreriaDtoPage = new PageDto<FlussoTesoreriaDto>();
		flussoTesoreriaDtoPage.setList(flussoTesoreriaDtoList);
	
		flussoTesoreriaDtoPage.setNextPage(flussoTesoreriaPage.getNumber() + 1 < flussoTesoreriaPage.getTotalPages());
		flussoTesoreriaDtoPage.setPage(flussoTesoreriaPage.getNumber() + 1);
		flussoTesoreriaDtoPage.setPageSize(flussoTesoreriaPage.getSize());
		flussoTesoreriaDtoPage.setPreviousPage(flussoTesoreriaPage.getNumber() > 0);
		flussoTesoreriaDtoPage.setTotalPages(flussoTesoreriaPage.getTotalPages());
		flussoTesoreriaDtoPage.setTotalRecords(flussoTesoreriaPage.getTotalElements());

		return flussoTesoreriaDtoPage;

	}

	private Page<FlussoTesoreria> getTesoreriaPageList(String codIpaEnte, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, String deAnnoBolletta, String codBolletta,
			String importo, String conto, String codOr1, String iuv, String iuf, Pageable pageable) {

		Specifications<FlussoTesoreria> specification = getSpecifications(codIpaEnte, dt_data_contabile_da,
				dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a, deAnnoBolletta, codBolletta, importo, conto,
				codOr1, iuv, iuf);

		Page<FlussoTesoreria> tesoreriaPage = null;
		if (specification != null) {
			tesoreriaPage = flussoTesoreriaDao.findAll(specification, pageable);
		} else {
			tesoreriaPage = flussoTesoreriaDao.findAll(pageable);
		}
		return tesoreriaPage;
	}

	private Specifications<FlussoTesoreria> getSpecifications(String codIpaEnte, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a, String deAnnoBolletta,
			String codBolletta, String importo, String conto, String codOr1, String iuv, String iuf) {

		Specifications<FlussoTesoreria> specifications = null;

		if (StringUtils.isNotBlank(codIpaEnte)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("codIpaEnte", "=", codIpaEnte.trim(), "ente"));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_contabile_da != null) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("dtBolletta", ">=", dt_data_contabile_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_contabile_a != null) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("dtBolletta", "<=", dt_data_contabile_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_valuta_da != null) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("dtDataValutaRegione", ">=", dt_data_valuta_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_valuta_a != null) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("dtDataValutaRegione", "<=", dt_data_valuta_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(deAnnoBolletta)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("deAnnoBolletta", "=", deAnnoBolletta.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codBolletta)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("codBolletta", "=", codBolletta.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(importo)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("deImporto", "=", importo.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(conto)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("codConto", "=", conto.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codOr1)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("codOr1", "like", codOr1.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(iuv)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("codIdUnivocoVersamento", "=", iuv.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(iuf)) {
			MyJpaSpecification<FlussoTesoreria> myJpaSpecification = new MyJpaSpecification<FlussoTesoreria>(
					new SearchCriteria("codIdUnivocoFlusso", "like", iuf.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		return specifications;
	}

	public List<FlussoTesoreriaDto> mapPoInDto(List<FlussoTesoreria> flussoTesoreriaList) {
		List<FlussoTesoreriaDto> flussoTesoreriaDtoList = new ArrayList<FlussoTesoreriaDto>();
		for (FlussoTesoreria flussoTesoreria : flussoTesoreriaList) {
			FlussoTesoreriaTO flussoTesoreriaTO = modelMapperUtil.map(flussoTesoreria, FlussoTesoreriaTO.class);
			FlussoTesoreriaDto dto = new FlussoTesoreriaDto();

			dto.setCodiceIpaEnte(flussoTesoreriaTO.getEnte().getCodIpaEnte());
			dto.setDeAnnoBolletta(flussoTesoreriaTO.getDeAnnoBolletta());
			dto.setCodBolletta(flussoTesoreriaTO.getCodBolletta());
			dto.setDeImporto(Utilities.parseImportoString(flussoTesoreriaTO.getNumIpBolletta()));
			dto.setFlussoTesoreriaTO(flussoTesoreriaTO);
			flussoTesoreriaDtoList.add(dto);
		}
		return flussoTesoreriaDtoList;
	}

	@Override
	public BilancioContainerDto estraiListaAccertamenti(final String codIpaEnte,
			final List<FlussoExportTO> listaFlussiExportTO) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		BilancioContainerDto bilancioContainerDto2 = new BilancioContainerDto();

		PreparedStatementCreator preparedStatementCreator_queryAccertamento = null;

		PivotSILChiediAccertamentoRispostaExtractor pivotSILChiediAccertamentoRispostaExtractor = new PivotSILChiediAccertamentoRispostaExtractor();

		preparedStatementCreator_queryAccertamento = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				String statement = selectQueryAccertamento + " " + fromQueryAccertamento + " " + whereQueryAccertamento;

				statement += " and ad.cod_ipa_ente = ?";

				statement += " and an.cod_stato = ? and an.de_tipo_stato = ?";

				statement += " and ad.cod_iud IN ";
				statement += "(";
				for (FlussoExportTO flussoExportTO : listaFlussiExportTO) {
					if (listaFlussiExportTO.indexOf(flussoExportTO) == 0)
						statement += "?";
					else
						statement += ",?";
				}
				statement += ") ";

				statement += groupByQueryAccertamento;

				statement += orderByQueryAccertamento;

				PreparedStatement ps = con.prepareStatement(statement);

				ps.setString(1, codIpaEnte);
				ps.setString(2, Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO);
				ps.setString(3, Constants.DE_TIPO_STATO_ACCERTAMENTO);

				for (FlussoExportTO flussoExportTO : listaFlussiExportTO) {
					ps.setObject(4 + listaFlussiExportTO.indexOf(flussoExportTO), flussoExportTO.getCodIud());
				}

				return ps;
			}
		};

		bilancioContainerDto2 = jdbcTemplate.query(preparedStatementCreator_queryAccertamento,
				pivotSILChiediAccertamentoRispostaExtractor);

		return bilancioContainerDto2;
	}

	private static final class PivotSILChiediAccertamentoRispostaExtractor
			implements ResultSetExtractor<BilancioContainerDto> {

		@Override
		public BilancioContainerDto extractData(ResultSet rs) throws SQLException, DataAccessException {
			LOG.info("pivotSILChiediAccertamento: Ricevuto ResultSet");
			LOG.info("pivotSILChiediAccertamento: INIZIO MAPPING RISPOSTA");

			BilancioContainerDto bilancioContainerDto = new BilancioContainerDto();

			while (rs.next()) {
				String codUfficio = rs.getString("cod_ufficio");
				String deTipoDovuto = rs.getString("de_tipo");
				String codCapitolo = rs.getString("cod_capitolo");
				String codAccertamento = rs.getString("cod_accertamento");
				BigDecimal sumImporto = rs.getBigDecimal("sum_importo");

				BilancioDto dto = new BilancioDto();
				dto.setCodUfficio(codUfficio.trim());
				dto.setCodTipoDovuto(deTipoDovuto.trim());
				dto.setCodCapitolo(codCapitolo.trim());
				dto.setCodAccertamento(codAccertamento.trim());
				dto.setImporto(Utilities.parseImportoString(sumImporto) + " \u20ac");
				bilancioContainerDto.getListaBilancioDto().add(dto);
			}
			LOG.info("FINE MAPPING RISPOSTA");
			return bilancioContainerDto;
		}
	}
}
