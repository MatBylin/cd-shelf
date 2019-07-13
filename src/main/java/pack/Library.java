package pack;

public interface Library {
    void addRecord(CdAlbum album);

    void editRecord(CdAlbum album);

    void deleteRecord(String recordName);

    void printRecords();

    void sortRecords();
}
