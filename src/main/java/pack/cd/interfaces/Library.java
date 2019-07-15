package pack.cd.interfaces;

import pack.cd.CdAlbum;

public interface Library {

    void addRecord(CdAlbum album);

    void editRecord(int id, CdAlbum newAlbum);

    void deleteRecord(int id);

    void printRecords();

}
