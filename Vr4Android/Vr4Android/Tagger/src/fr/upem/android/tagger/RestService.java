package fr.upem.android.tagger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/MyRestService")
@ApplicationPath("/resources")
@Singleton
public class RestService extends Application {
	public static final String RESOURCES_URL = "D:\\WkSpaceAndroid\\Vr4Android\\Tagger\\WebContent\\WEB-INF\\resources\\";
	private int playerQueue;
	private Player playerOne;
	private Player playerTwo;
	private List<Player> listPlayers;
	private TaggerDao taggerDao;
	private int fileName = 1;
	private int gameEnd = 1;
	
	public RestService() {
		taggerDao = new TaggerDao();
		listPlayers = taggerDao.getAllPlayers();
	}
	
	@GET
	@Path("/getInQueue")
	public Player getInQueue(@QueryParam("nickName") String nickName) {
		Player p = checkIfExist(nickName);
		System.out.println("status 1 : " + p.getStatus());
		if(playerQueue == 2) {
			playerQueue=0;
		}
		System.out.println("status 2 : " + p.getStatus());
		if(p == null) {
			return null;
		}else {
			addPlayer(p);
			return p;
		}

	}
	@GET
	@Path("/getPlayerOne")
	public Player setPlayerOne() {
		return playerOne;
	}

	@GET
	@Path("/getPlayerTwo")
	public Player setPlayerTwo() {
		return playerTwo;
	}
	@GET
	@Path("/getPlayer")
	public Player setPlayer(@QueryParam("nickName") String nickName) {
		return checkIfExist(nickName);
	}
	@GET
	@Path("/getPlayerFromDao")
	public Player setPlayerFromDao(@QueryParam("nickName") String nickName) {
		return taggerDao.getPlayer(nickName);
	}
	@GET
	@Path("/getImageTagger")
	public String setImageTagger(@QueryParam("idImage") Integer idImage) {
		String s = "";
		ImageTagger imageTagger;
		if(taggerDao.checkImageTagger(idImage))
			imageTagger = taggerDao.getImageTagger(idImage);
		else
			imageTagger = new ImageTagger(idImage);
		
		for(Map.Entry< String,Integer> e : imageTagger.getTags().entrySet()) {
			s += e.getKey() + "-" + e.getValue() +"_";
		}
		try {
			s = s.substring(0, s.length()-1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return s;
	}
	@GET
	@Path("/Disonnected")
	public Response Disonnected(@QueryParam("player") String player) {
		Player p=checkIfExist(player);
		changeStatus(p, false);
		return Response.ok().build();
	}
	@GET
	@Path("/getImageID")
	public Integer setImageID() {
		return fileName;
	}
	
	@GET
	@Path("/checkPassword")
	public Boolean getInQueue(@QueryParam("nickName") String nickName,@QueryParam("password") String password) {
		return checkIfExist(nickName, password);
	}
	
	@GET
	@Path("/Inscription")
	public Boolean Inscription(@QueryParam("nickName") String nickName,@QueryParam("password") String password) {
		if(checkIfExist(nickName) != null) {
			return false;
		}else {
			taggerDao.WritePlayer(new Player(nickName, password));
			listPlayers = taggerDao.getAllPlayers();
			return true;
		}
	}
	@GET
	@Path("/CheckPlayer")
	public boolean CheckPlayer() {
		if(playerQueue == 2) {
			return true;
		}else {
			return false;
		}
	}
	@GET
	@Path("/EndGame")
	synchronized public Response EndGame(@QueryParam ("currentPlayer") String currentPlayer
			,@QueryParam ("tagsCurrentPlayer") String tagsCurrentPlayer) 
	{
		if(gameEnd == 1) {
			playerOne = checkIfExist(currentPlayer);
			playerOne.setTags(tagsCurrentPlayer);
			gameEnd++;
		}else if(gameEnd == 2) {
			playerTwo = checkIfExist(currentPlayer);
			playerTwo.setTags(tagsCurrentPlayer);
			
			ImageTagger imageTagger;
			if(taggerDao.checkImageTagger(playerOne.getIdImage())) {
				imageTagger = taggerDao.getImageTagger(playerOne.getIdImage());
			}else {
				imageTagger = new ImageTagger(playerOne.getIdImage());
			}
			imageTagger.setPlayerOne(playerOne);
			imageTagger.setPlayerTwo(playerTwo);
			imageTagger.calculeScore();
			taggerDao.WriteImageTagger(imageTagger);
			System.out.println(imageTagger);
			System.out.println(playerOne);
			System.out.println(playerTwo);
			gameEnd = 1;
		}
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/CheckStatus")
	public boolean CheckStatus(@QueryParam("nickName") String nickName) {
		if(checkIfExist(nickName).getStatus())
			return true;
		return false;
	}
	@GET
	@Path("/getImage")
	@Produces("image/png")
	public Response sendImage() {
		File file = new File(RESOURCES_URL+fileName+".png");
		ResponseBuilder response =  Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename="+fileName+".png");
		return response.build();
	}
	
	public Boolean checkIfExist(String nickName, String password) {
		for (Player p : listPlayers) {
			if(p.getNickName().toLowerCase().equals(nickName.toLowerCase()) && p.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	public Player checkIfExist(String nickName) {
		for (Player p : listPlayers) {
			if(p.getNickName().toLowerCase().equals(nickName.toLowerCase()) ) {
				return p;
			}
		}
		return null;
	}
	private void addPlayer(Player p) {
		if(playerQueue==0) {
			playerOne = p;
			changeStatus(playerOne,true);
			playerQueue++;
		}else if(playerQueue == 1 && playerOne != p) {
			playerTwo = p;
			changeStatus(playerTwo,true);
			playerQueue++;
			fileName = 1 + (int)(Math.random() * 25);
			playerOne.setIdImage(fileName);
			playerTwo.setIdImage(fileName);
		}
	}
	public void changeStatus(Player p,Boolean status) {
		listPlayers.remove(p);
		p.setStatus(status);
		listPlayers.add(p);
	}	

	
}
