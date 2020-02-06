package com.logiqube.basketqube.dataimport.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.common.service.SequenceGeneratorService;
import com.logiqube.basketqube.model.Match;

@Component
public class MatchModelListener extends AbstractMongoEventListener<Match> {

	@Autowired
	private SequenceGeneratorService sequenceGenerator;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Match> event) {
		if (event.getSource().getMatchId() < 1) {
			event.getSource().setMatchId(sequenceGenerator.generateSequence(Match.SEQUENCE_NAME));
		}
	}

}
