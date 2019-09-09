package com.sampark.PMS.object;

import java.util.ArrayList;
import java.util.List;

import com.sampark.PMS.dto.KraDetails;

public class KraSections {

	private List<KraDetails> sectionAList = new ArrayList<KraDetails>();
	private List<KraDetails> sectionBList = new ArrayList<KraDetails>();
	private List<KraDetails> sectionCList = new ArrayList<KraDetails>();
	private List<KraDetails> sectionDList = new ArrayList<KraDetails>();
	
	public List<KraDetails> getSectionAList() {
		return sectionAList;
	}
	public void setSectionAList(List<KraDetails> sectionAList) {
		this.sectionAList = sectionAList;
	}
	public List<KraDetails> getSectionBList() {
		return sectionBList;
	}
	public void setSectionBList(List<KraDetails> sectionBList) {
		this.sectionBList = sectionBList;
	}
	public List<KraDetails> getSectionCList() {
		return sectionCList;
	}
	public void setSectionCList(List<KraDetails> sectionCList) {
		this.sectionCList = sectionCList;
	}
	public List<KraDetails> getSectionDList() {
		return sectionDList;
	}
	public void setSectionDList(List<KraDetails> sectionDList) {
		this.sectionDList = sectionDList;
	}
	
	
}
