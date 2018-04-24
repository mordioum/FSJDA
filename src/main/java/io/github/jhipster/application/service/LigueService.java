package io.github.jhipster.application.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.application.domain.DojoClub;
import io.github.jhipster.application.domain.Ligue;
import io.github.jhipster.application.repository.DojoClubRepository;
import io.github.jhipster.application.repository.LigueRepository;

@Service
@Transactional
public class LigueService {

    private final Logger log = LoggerFactory.getLogger(LigueService.class);
    
    @Inject
    private LigueRepository ligueRepository;
    
    public long getIdLigue() {
		Ligue ligue = ligueRepository.findByUserIsCurrentUserService();
		//log.info("Id DOJO User connecter est : "+ dojoclub.getId()  +  dojoclub.getName()); 
		return ligue.getId();
	}

}
