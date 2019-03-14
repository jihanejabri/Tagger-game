package fr.upem.android.tagger;

public class Player {
	
	private String nickName;
	private String password;
	private int score;
	private String tags;
	private int idImage;
	private boolean status;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Player(String nickName, String password, int score, String tags, int idImage, boolean status) {
		super();
		this.nickName = nickName;
		this.password = password;
		this.score = score;
		this.tags = tags;
		this.idImage = idImage;
		this.status = status;
	}
	public Player(String data[]) {
		super();
		this.nickName = data[0];
		this.password = data[1];
		this.score = Integer.parseInt(data[2]);
		this.tags = data[3];
		this.idImage = Integer.parseInt(data[4]);
	}

	public Player(String nickName, String password, int score) {
		super();
		this.nickName = nickName;
		this.password = password;
		this.score = score;
		this.tags = "";
	}

	public Player(String nickName, String password) {
		super();
		this.nickName = nickName;
		this.password = password;
		this.score = 0;
	}
	
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getIdImage() {
		return idImage;
	}

	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}


	@Override
	public String toString() {
		return "Player [nickName=" + nickName + ", score=" + score + ", tags=" + tags + ", idImage=" + idImage + "]";
	}



	
	
	
}
