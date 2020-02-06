package com.logiqube.basketqube.dataimport.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.common.service.SequenceGeneratorService;
import com.logiqube.basketqube.model.Team;

@Component
public class TeamModelListener extends AbstractMongoEventListener<Team> {
	
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Team> event) {
		if (event.getSource().getTeamId() < 1) {
			event.getSource().setTeamId(sequenceGenerator.generateSequence(Team.SEQUENCE_NAME));
		}
	}

}
