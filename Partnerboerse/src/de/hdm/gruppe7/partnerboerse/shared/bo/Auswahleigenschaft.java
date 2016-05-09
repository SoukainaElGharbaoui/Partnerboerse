package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.List;

public class Auswahleigenschaft extends Eigenschaft {
	
	private static final long serialVersionUID = 1L;
	
	public String auswahltext;
	//private List <Auswahloption> auswahloption;
	
	
	private List <Auswahloption> auswahloption;
	
	public List <Auswahloption> zugehoerigeAuswahloptionen;
	
	public void setAuswahltext(String auswahltext){
		this.auswahltext = auswahltext;
	}
	
	public String getAuswahltext(){
		return auswahltext;
	}
	
	public void setZugehoerigeAuswahloptionen(List<Auswahloption> auswahloption){
		this.auswahloption = auswahloption;
	}
	
	public List <Auswahloption> getZugehoerigeAuswahloptionen(){
		return auswahloption;
		
	}
	
}


