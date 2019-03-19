package com.nextel.hotizonalarmes.repository.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nextel.hotizonalarmes.model.Alarme;

public class AlarmesImpl implements AlarmesQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Alarme> filtrar(Alarme filtro, Pageable pageable) {
		
		int paginaatual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaatual * totalRegistrosPorPagina;
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Alarme.class);
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		
		Sort sort = pageable.getSort();
		System.out.println(">>> sort: " + sort);
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
			
		}
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro)); 
	}
	
	private Long total(Alarme filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Alarme.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(Alarme filtro, Criteria criteria) {
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getIncidente())){
				criteria.add(Restrictions.ilike("incidente", filtro.getIncidente(), MatchMode.ANYWHERE));
			}
		}
		
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getSite())){
				criteria.add(Restrictions.ilike("site", filtro.getSite(), MatchMode.ANYWHERE));
			}
		}
		
		if(StringUtils.isEmpty(filtro.getIncidente()) && StringUtils.isEmpty(filtro.getSite())){
			criteria.add(Restrictions.sqlRestriction(null));
		}
		
		/*if (StringUtils.isEmpty(filtro.getIncidente()) || filtro.getIncidente().length() < 4) {
			criteria.add(Restrictions.sqlRestriction(null));
		}*/
			
	}
}
