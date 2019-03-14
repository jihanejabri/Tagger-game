package fr.upem.android.tagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

public class ImageTagger {
	
	private int imageId;
	private HashMap<String,Integer> tags;
	private List<String> blackTags;
	private List<String> result;
	private Player playerOne;
	private Player playerTwo;
	public final static String SEPARATOR = "_";
	public ImageTagger(int imageId) {
		this.imageId = imageId;
		blackTags = new ArrayList<>();
		tags = new HashMap<>();
		result = new ArrayList<>();
	}
	public ImageTagger(int imageId,Player playerOne, Player playerTwo,HashMap<String,Integer> tags,List<String> blackTags) {
		this.imageId = imageId;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.tags = tags;
		this.blackTags = blackTags;
	}
	public ImageTagger(int imageId,HashMap<String,Integer> tags,List<String> blackTags) {
		this.imageId = imageId;
		this.tags = tags;
		this.blackTags = blackTags;
	}
	public ImageTagger(int imageId,Player playerOne, Player playerTwo) {
		this.imageId = imageId;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo; 
	}

	public void calculeScore(){
		String listP1[] = playerOne.getTags().split(SEPARATOR);
		String listP2[] = playerTwo.getTags().split(SEPARATOR);
		result = new ArrayList<>();
		for (String s1 : listP1) {
			for (String s2 : listP2) {
				if(s1.toLowerCase().equals(s2.toLowerCase())) {
					result.add(s2.toLowerCase());
				}
			}
		}
		
			adjustTags(result);
		
		for (String res : result) {
			playerOne.setScore(playerOne.getScore()+(getNbTag(res)*10));
			playerTwo.setScore(playerTwo.getScore()+(getNbTag(res)*10));
		}
	}
	public void adjustTags(List<String> tmp) {
		for (String s : tmp) {
			if(tags.containsKey(s)) {
				for(Map.Entry< String,Integer> e : tags.entrySet()) {
					if(e.getKey().toLowerCase().equals(s.toLowerCase())) {
						e.setValue(e.getValue()+1);
					}
				}
			}else {
				tags.put(s, 1);
			}
			
		}
	}
	public int getNbTag(String tag) {
		for(Map.Entry< String,Integer> e : tags.entrySet()) {
			if(e.getKey().toLowerCase().equals(tag.toLowerCase())) {
				return e.getValue();
			}
		}
		return 0;
	}
	
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public HashMap<String, Integer> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, Integer> tags) {
		this.tags = tags;
	}
	public List<String> getBlackTags() {
		return blackTags;
	}
	public void setBlackTags(List<String> blackTags) {
		this.blackTags = blackTags;
	}
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
	public Player getPlayerOne() {
		return playerOne;
	}
	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}
	public Player getPlayerTwo() {
		return playerTwo;
	}
	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}
	@Override
	public String toString() {
		return "ImageTagger [imageId=" + imageId + ", tags=" + tags + ", blackTags=" + blackTags + "]";
	}
	
	
}
