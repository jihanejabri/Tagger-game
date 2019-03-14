package fr.upem.android.tagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaggerDao {
	
	public final static String URL_FILES = "D:\\WkSpaceAndroid\\Vr4Android\\Tagger\\WebContent\\\\WEB-INF\\database\\";
	
	public TaggerDao() {
		// TODO Auto-generated constructor stub
	}
	
	public void WriteImageTagger(ImageTagger imageTagger) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(URL_FILES+"tags.txt"));
			BufferedReader brPlayer = new BufferedReader(new FileReader(URL_FILES+"players.txt"));
			String sCurrentLine;
			String tags="";
			String players="";
			while ((sCurrentLine = br.readLine()) != null) {
				String sTab[] = sCurrentLine.split("\t");
				if(!sTab[1].trim().equals(imageTagger.getImageId()+"")) {
					tags += sCurrentLine+"\n";
				}
			}
			br.close();

			PrintWriter in = new PrintWriter(new FileWriter(URL_FILES+"tags.txt"));
			in.print(tags);
				for(Map.Entry< String,Integer> e : imageTagger.getTags().entrySet()) {
					in.println("T\t"+imageTagger.getImageId()+"\t"+ e.getKey() + "\t" + e.getValue()+"\t");
				}
				
				for (String s : imageTagger.getBlackTags()) {
					in.println("B\t"+imageTagger.getImageId()+"\t"+s);
				}

				in.close();
			while ((sCurrentLine = brPlayer.readLine()) != null) {
				String sTab[] = sCurrentLine.split("\t");
				if(!sTab[0].trim().equals(imageTagger.getPlayerOne().getNickName()) && !sTab[0].trim().equals(imageTagger.getPlayerTwo().getNickName())) {
					players += sCurrentLine+"\n";
				}
			}

			brPlayer.close();
			PrintWriter inPlayer = new PrintWriter(new FileWriter(URL_FILES+"players.txt"));
			inPlayer.print(players);
				Player p = imageTagger.getPlayerOne();
				inPlayer.println(p.getNickName()+"\t"+p.getPassword()+"\t"+p.getScore()+"\t"+p.getTags()+"\t"+p.getIdImage());
				p = imageTagger.getPlayerTwo();
				inPlayer.println(p.getNickName()+"\t"+p.getPassword()+"\t"+p.getScore()+"\t"+p.getTags()+"\t"+p.getIdImage());
				
			
			inPlayer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public List<ImageTagger> getAllImageTagger(){
		
		List<ImageTagger> imageTaggers = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(URL_FILES+"tags.txt"));
			//
			String sCurrentLine ="";
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println("sCurrentLine : "+sCurrentLine);		
				String sTab[] = sCurrentLine.split("\t");
				System.out.println("sTab[1] : " + sTab[1]);
			}
			PrintWriter in = new PrintWriter(new FileWriter(URL_FILES+"tags.txt"));
		} catch (Exception e) {
				System.out.println(e.getMessage());
		}
		return imageTaggers;
	}
	public void WritePlayer(Player player) {
		try {
			FileWriter filePlayers = new FileWriter(URL_FILES+"players.txt",true);
			PrintWriter inPlayer = new PrintWriter(filePlayers);
			inPlayer.println(player.getNickName()+"\t"+player.getPassword()+"\t"+0);
			inPlayer.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	public ImageTagger getImageTagger (int idImage){
		ImageTagger imageTagger = null;
		HashMap<String,Integer> tags = new HashMap<>();
		List<String> blackTags = new ArrayList<>();
		
			try{
				BufferedReader br = new BufferedReader(new FileReader(URL_FILES+"tags.txt"));
				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					String sTab[] = sCurrentLine.split("\t");
					if(sTab[1].trim().equals(idImage+"")) {
						
						if(sTab[0].trim().equals("T")) {
							tags.put(sTab[2], Integer.parseInt(sTab[3]));
						}else if(sTab[0].trim().equals("B")) {
							blackTags.add(sTab[2]);
						}

					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return new ImageTagger(idImage, tags, blackTags);
	}
	public boolean checkImageTagger(Integer idImage) {
		try{
			BufferedReader br = new BufferedReader(new FileReader(URL_FILES+"tags.txt"));
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String sTab[] = sCurrentLine.split("\t");
				if(sTab[1].trim().equals(idImage+"")) {
					return true;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Player getPlayer (String nickName){
		Player p = null;
			try{
				BufferedReader br = new BufferedReader(new FileReader(URL_FILES+"players.txt"));
				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					String sTab[] = sCurrentLine.split("\t");
					if(sTab[0].trim().equals(nickName)) {
						p = new Player(sTab);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return p;
	}
	
	public List<Player> getAllPlayers (){
		List<Player> listPlayers = new ArrayList<>();
			try{
				BufferedReader br = new BufferedReader(new FileReader(URL_FILES+"players.txt"));
				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					String sTab[] = sCurrentLine.split("\t");
						listPlayers.add(new Player(sTab));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return listPlayers;
	}


}
