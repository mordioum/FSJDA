package io.github.jhipster.application.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.application.domain.DojoClub;
import io.github.jhipster.application.repository.DojoClubRepository;

@Service
@Transactional
public class LicenceService {

    private final Logger log = LoggerFactory.getLogger(LicenceService.class);
    
    @Inject
    private DojoClubRepository dojoclubRepository;
    
    public long getIdDojoClub() {
		DojoClub dojoclub = dojoclubRepository.findByUserIsCurrentUserService();
		//log.info("Id DOJO User connecter est : "+ dojoclub.getId()  +  dojoclub.getName()); 
		return dojoclub.getId();
	}

}
