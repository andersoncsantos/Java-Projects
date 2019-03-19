package com.nextel.hotizonalarmes.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nextel.hotizonalarmes.model.Alarme;

public interface AlarmesQueries {
	
	public Page<Alarme> filtrar(Alarme filtro, Pageable pageable);
}
	
	
	
