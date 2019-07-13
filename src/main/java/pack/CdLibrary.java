package pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//singleton eager implemented
public final class CdLibrary implements Library {

    private static CdLibrary INSTANCE = new CdLibrary();

    private CdLibrary() {
        populateAlbumsList();
    }

    public static CdLibrary getInstance() {
        return INSTANCE;
    }

    List<CdAlbum> cdAlbumsList = new ArrayList<>();

    private void populateAlbumsList(){
        addRecord(new CdAlbum("Jerry Cantrell", "Bogy Depot", "rock album", 1.99));
        addRecord(new CdAlbum("Alice in Chains", "Facelift", "grunge album", 2.19));
        addRecord(new CdAlbum("Nirvana", "Nevermind", "grunge album", 1.69));
        addRecord(new CdAlbum("Stone temple pilots", "Core", "rock album", 1.99));
        addRecord(new CdAlbum("Death", "Scream Bloody Gore", "metal album", 1.59));
    }

    @Override
    public void addRecord(CdAlbum album) {
        cdAlbumsList.add(album);
    }

    @Override
    public void editRecord(CdAlbum album) {

    }

    @Override
    public void deleteRecord(String recordName) {
        for(CdAlbum album : cdAlbumsList){
            if(album.getTitle()
                    .toLowerCase()
                    .contains(recordName.toLowerCase())){
                cdAlbumsList.remove(album);
            }
        }
    }

    @Override
    public void printRecords() {
        for(CdAlbum album : cdAlbumsList){
            System.out.println(album);
        }
    }

    @Override
    public void sortRecords() {

    }
}
