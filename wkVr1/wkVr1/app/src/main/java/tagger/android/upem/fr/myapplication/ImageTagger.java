package tagger.android.upem.fr.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageTagger {
	
	private int imageId;
	private HashMap<String,Integer> tags;
	private List<String> blackTags;
	private List<String> result;
	private Player playerOne;
	private Player playerTwo;

	public ImageTagger() {
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
