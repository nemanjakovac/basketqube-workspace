package com.logiqube.basketqube.dataimport.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.common.service.SequenceGeneratorService;
import com.logiqube.basketqube.model.Player;

@Component
public class PlayerModelListener extends AbstractMongoEventListener<Player> {

	@Autowired
	private SequenceGeneratorService sequenceGenerator;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Player> event) {
		if (event.getSource().getPlayerId() < 1) {
			event.getSource().setPlayerId(sequenceGenerator.generateSequence(Player.SEQUENCE_NAME));
		}
	}

}
