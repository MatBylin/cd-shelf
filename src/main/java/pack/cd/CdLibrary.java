package pack.cd;

import pack.cd.interfaces.DBable;
import pack.cd.interfaces.Library;
import pack.cd.interfaces.Sortable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//singleton eager implemented
public final class CdLibrary implements Library, Sortable, DBable, Serializable {

    private static CdLibrary INSTANCE = new CdLibrary();

    private CdLibrary() {
        //populateAlbumsList();
        readDb();
    }

    public static CdLibrary getInstance() {
        return INSTANCE;
    }

    private List<CdAlbum> cdAlbumsList = new ArrayList<>();


//    private void populateAlbumsList() {
//        addRecord(new CdAlbum("Jerry Cantrell", "Bogy Depot", "rock album", 36.99));
//        addRecord(new CdAlbum("Alice in Chains", "Facelift", "grunge album", 43.19));
//        addRecord(new CdAlbum("Nirvana", "Nevermind", "grunge album", 59.69));
//        addRecord(new CdAlbum("Stone temple pilots", "Core", "rock album", 13.99));
//        addRecord(new CdAlbum("Death", "Scream Bloody Gore", "metal album", 29.99));
//    }

    @Override
    public void addRecord(CdAlbum album) {
        cdAlbumsList.add(album);
    }

    @Override
    public void editRecord(int id, CdAlbum newAlbum) {
        int index = getIndexById(id);
        cdAlbumsList.set(index, newAlbum);
    }

    public void searchFor(String search) {
       List<CdAlbum> albums = cdAlbumsList.stream()
                .filter(e -> e.getArtist().toLowerCase().contains(search.toLowerCase()) ||
                        e.getTitle().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
       printRecords(albums);
    }

    @Override
    public void deleteRecord(int id) {
        boolean isDeleted = cdAlbumsList.removeIf(album -> album.getCdId() == id);
        if (isDeleted) {
            System.out.printf("Album with id : %d removed succesfully! \n", id);
        } else {
            System.out.printf("Album with id : %d not found! \n", id);
        }
    }

    @Override
    public void printRecords() {
        for (CdAlbum album : cdAlbumsList) {
            System.out.println(album);
        }
    }

    private void printRecords(List<CdAlbum> cdAlbumsSortList) {
        for (CdAlbum album : cdAlbumsSortList) {
            System.out.println(album);
        }
    }

    @Override
    public void printSortedByTitleRecords() {
        List<CdAlbum> copy =
                cdAlbumsList.stream()
                        .sorted()
                        .collect(Collectors.toList());
        printRecords(copy);
    }

    @Override
    public void printSortedByArtistRecords() {
        List<CdAlbum> copy =
                cdAlbumsList.stream()
                        .sorted(Comparator.comparing(CdAlbum::getArtist))
                        .collect(Collectors.toList());
        printRecords(copy);
    }

    @Override
    public void printSortedByIdAsc() {
        List<CdAlbum> copy =
                cdAlbumsList.stream()
                        .sorted(Comparator.comparingInt(CdAlbum::getCdId))
                        .collect(Collectors.toList());
        printRecords(copy);
    }

    @Override
    public void printSortedByIdDesc() {
        List<CdAlbum> copy =
                cdAlbumsList.stream()
                        .sorted((x, y) -> y.getCdId() - x.getCdId())
                        .collect(Collectors.toList());
        printRecords(copy);
    }

    @Override
    public void printSortedByRecordPrice() {
        List<CdAlbum> copy =
                cdAlbumsList.stream()
                        .sorted(Comparator.comparingInt(x -> (int) x.getPriceBought()))
                        .collect(Collectors.toList());
        printRecords(copy);
    }

    @Override
    public void readDb() {
        try (InputStream is = Files.newInputStream(Paths.get("albumlist.ser"))) {
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            cdAlbumsList = (List<CdAlbum>) objectInputStream.readObject();

            System.out.println("\nDatabase read successful...");
            getMaxIdFromDbList().ifPresentOrElse(e -> CdAlbum.setId(e + 1), () -> CdAlbum.setId(1));
            //CdAlbum.setId(getMaxIdFromDbList() + 1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeDb() {
        try (OutputStream os = Files.newOutputStream(Paths.get("albumlist.ser"))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);

            objectOutputStream.writeObject(cdAlbumsList);
            System.out.println("Database write successful...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getIndexById(int id) {
        int indexOfAlbum = -1;
        if (cdAlbumsList != null) {
            for (CdAlbum album : cdAlbumsList) {
                if (album.getCdId() == id) {
                    indexOfAlbum = cdAlbumsList.indexOf(album);
                    System.out.printf("Album with id : %d found! \n", id);
                }
            }
        }
        return indexOfAlbum;
    }

    private Optional<Integer> getMaxIdFromDbList() {
        return cdAlbumsList.stream()
                .max(((o1, o2) -> o1.getCdId() - o2.getCdId()))
                .map(e -> e.getCdId());
    }

}


// DO NOT READ!!!
//    public int getIndexById(int id) {
//        int indexOfAlbum = 0;
//        for (CdAlbum album : cdAlbumsList) {
//            if (album.getCdId() == id) {
//                indexOfAlbum = cdAlbumsList.indexOf(album);
//                System.out.printf("Album with id : %d found! \n", id);
//            }
//        }
//        return indexOfAlbum;
//    }
//    private int getMaxIdFromDbList(){
//        return cdAlbumsList.stream().max(((o1, o2) -> o1.getCdId() - o2.getCdId())).get().getCdId();
//    }

//        if(length () > 0)
//        DoubleSummaryStatistics doubleSummaryStatistics = cdAlbumsList.stream().mapToDouble(e -> e.getPricePerDay()).summaryStatistics();
//        doubleSummaryStatistics.getMax()