package com.nextel.hotizonalarmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nextel.hotizonalarmes.model.Alarme;
import com.nextel.hotizonalarmes.repository.helper.AlarmesQueries;

@Repository
public interface Alarmes extends JpaRepository<Alarme, Long>, AlarmesQueries{


}
