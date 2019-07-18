package pack.cd.interfaces;

import pack.cd.CdAlbum;
import pack.cd.exceptions.EmptyCdAlbumException;

public interface Library {

    void addRecord(CdAlbum album);

    void editRecord(int id, CdAlbum newAlbum);

    void deleteRecord(int id);

    void printRecords();

    String getStatistics();

}
