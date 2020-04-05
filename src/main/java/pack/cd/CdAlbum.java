package pack.cd;

import lombok.Data;

import java.io.Serializable;

@Data
public class CdAlbum implements Comparable<CdAlbum>, Serializable {
    private String artist;
    private String title;
    private String description;
    private double priceBought;
    private static int id = 1;
    private int cdId;
    private static final long serialVersionUID = 8693256477478073922L;

    public CdAlbum(String artist, String title, String description, double priceBought) {
        this.artist = artist;
        this.title = title;
        this.description = description;
        this.priceBought = priceBought;
        cdId = id++;
    }

    static void setId(int idFromDB) {
        id = idFromDB;
    }

    @Override
    public String toString() {
        return "CdAlbum {" +
                "artist = '" + artist + '\'' +
                ", title = '" + title + '\'' +
                ", description = '" + description + '\'' +
                ", bought for = " + priceBought +
                ", id = " + cdId +
                '}';
    }

    @Override
    public int compareTo(CdAlbum o) {
        return title.compareTo(o.title);
    }
}
