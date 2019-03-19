package com.nextel.hotizonalarmes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nextel.hotizonalarmes.controller.page.PageWrapper;
import com.nextel.hotizonalarmes.model.Alarme;
import com.nextel.hotizonalarmes.repository.Alarmes;

@Controller
public class AlarmesController {
	
	@Autowired
	private Alarmes alarmes;
	
	@RequestMapping("/consulta")
	public ModelAndView pesquisar(Alarme alarme, BindingResult result,@PageableDefault(size = 7) Pageable pageable, 
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("/ConsultarAlarmes"); 
		
		PageWrapper<Alarme> paginaWrapper = new PageWrapper<>(alarmes.filtrar(alarme, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	
	
	
}
