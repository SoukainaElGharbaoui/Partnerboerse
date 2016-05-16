package de.hdm.gruppe7.partnerboerse.server.db;

import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.PartnervorschlaegeNp;

public class PartnervorschlaegeNpMapper {

	private static PartnervorschlaegeNpMapper partnervorschlaegeNpMapper = null;
	
	protected PartnervorschlaegeNpMapper() {

	}
	
	public static PartnervorschlaegeNpMapper partnervorschlaegeNpMapper() {
		if (partnervorschlaegeNpMapper == null) {
			partnervorschlaegeNpMapper = new PartnervorschlaegeNpMapper();
		}

		return partnervorschlaegeNpMapper;
	}

	

	
}
