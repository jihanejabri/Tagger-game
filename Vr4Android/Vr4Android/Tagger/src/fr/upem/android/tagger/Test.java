package fr.upem.android.tagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	private HashMap<String,Integer> tags;
	private List<String> blackTags;
	public Test() {
		tags = new HashMap<>();
		tags.put( "Hear",5);
		tags.put("Cown",23 );
		tags.put( "comedia",15);
		tags.put( "Earphone",200);
		blackTags = new ArrayList<>();
		blackTags.add("Black");
		blackTags.add("Yellow");
		blackTags.add("soft");
		blackTags.add("Squichy");
		p04();
	}
	private void p04() {
		System.out.println(new TaggerDao().getPlayer("toto"));
	}
	public String transforme(List<String> tags) {
		String currentTag="";
        for(int i  = 0; i < blackTags.size() ; i++){
            if(i+1 == blackTags.size()) {
            	currentTag += blackTags.get(i);
            }else {
            	currentTag += blackTags.get(i) +"#-#";
            }
        }
        return currentTag;
	}
	private void p03() {
		Player p1 = new Player("Toumalo", "toto", 188);
		Player p2 = new Player("S0high", "toto", 158);
		ImageTagger imageTagger = new ImageTagger(3, p1, p2, tags, blackTags);
		new TaggerDao().WriteImageTagger(imageTagger);
	}
	public void p02() {
		String s11 = "Hear#-#Scoop#-#Comedia#-#Earphone";
		String s22 ="Cown#-#FUck#-#Lover#-#Hear#-#Earphone#-#comedia";
		String listP1[] = s11.split("#-#");
		String listP2[] = s22.split("#-#");
		List<String> tmp = new ArrayList<>();
		for (String s1 : listP1) {
			for (String s2 : listP2) {
				if(s1.toLowerCase().equals(s2.toLowerCase())) {
					tmp.add(s2.toLowerCase());
				}
			}
		}
		System.out.println("tmp : " + tmp.toString());
		adjustTags(tmp);
	}
	public void adjustTags(List<String> tmp) {
		for (String s : tmp) {
			for(Map.Entry< String,Integer> e : tags.entrySet()) {
				if(e.getKey().toLowerCase().equals(s.toLowerCase())) {
					e.setValue(e.getValue()+1);
				}
			}
		}
		for(Map.Entry< String,Integer> e : tags.entrySet()) {
			System.out.println("value : " + e.getValue()   + " Key : " + e.getKey());
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
	public void p01() {
		System.out.println(getNbTag("Earphone"));
	}
	public static void main(String[] args) {
		new Test();
	}

}
