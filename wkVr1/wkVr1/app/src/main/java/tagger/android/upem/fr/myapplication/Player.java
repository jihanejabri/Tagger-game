package tagger.android.upem.fr.myapplication;

import java.io.Serializable;

public class Player implements Serializable {

    private String nickName;
    private String password;
    private int score;
    private int idImage;
    private String tags;

    public Player() {
        // TODO Auto-generated constructor stub
    }

    public Player(String nickName, String password, int score, int idImage, String tags) {
        this.nickName = nickName;
        this.password = password;
        this.score = score;
        this.idImage = idImage;
        this.tags = tags;
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
        return "Player{" +
                "nickName='" + nickName + '\'' +
                ", idImage=" + idImage +
                ", tags='" + tags + '\'' +
                '}';
    }
}
