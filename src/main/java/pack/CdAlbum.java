package pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CdAlbum {
    private String artist;
    private String title;
    private String description;
    private double pricePerDay;
    //private int id;
}
