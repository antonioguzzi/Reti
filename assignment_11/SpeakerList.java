package assignment_11;

import java.util.ArrayList;

public class SpeakerList {
	private ArrayList<String> speakers;
	
	public SpeakerList() {
		this.speakers = new ArrayList<String>(5);
	}
	
	public int getSize() {return this.speakers.size();}
	public boolean appendSpeaker(String name) {return this.speakers.add(name);}
	public boolean isEsmpty() {return this.speakers.isEmpty();}
	public String getSpeaker(int i) {return this.speakers.get(i);}
	public boolean isIn(String name) {return this.speakers.contains(name);}
	public void printList() {
		for(int i = 0; i < this.speakers.size(); i++) {
			System.out.print(this.speakers.get(i) + " ");
		}
	}
}
