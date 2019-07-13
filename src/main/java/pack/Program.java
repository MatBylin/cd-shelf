package pack;

public class Program {

    public static void main(String[] args) {
        CdAlbum album = new CdAlbum("AC/DC", "Thunderstack", "rock album", 10.99);

        CdLibrary lib = CdLibrary.getInstance();

        lib.addRecord(album);

       lib.printRecords();
    }
}
