package pack.cd;

import pack.cd.exceptions.EmptyCdAlbumException;
import pack.cd.exceptions.NullAlbumObjectException;
import pack.cd.interfaces.DBable;
import pack.cd.interfaces.Library;
import pack.cd.interfaces.Sortable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CdLibrary implements Library, Sortable, DBable, Serializable {

    public CdLibrary() {
        readDb();
    }

    private List<CdAlbum> cdAlbumsList = new ArrayList<>();

    List<CdAlbum> getCdAlbumsList() {
        return cdAlbumsList;
    }

    private void checkForEmptyCdAlbumException() {
        if (cdAlbumsList == null || cdAlbumsList.size() == 0) {
            throw new EmptyCdAlbumException("Album list is null or empty!");
        }
    }

    @Override
    public void printRecords() {
        checkForEmptyCdAlbumException();
        for (CdAlbum album : cdAlbumsList) {
            System.out.println(album);
        }
    }

    public void printRecords(List<CdAlbum> cdAlbumsSortList) {
        checkForEmptyCdAlbumException();
        for (CdAlbum album : cdAlbumsSortList) {
            System.out.println(album);
        }
    }

    @Override
    public void addRecord(CdAlbum album) {
        if (album == null) {
            throw new NullAlbumObjectException("Album object is null!");
        }
        cdAlbumsList.add(album);
    }

    @Override
    public void editRecord(int id, CdAlbum newAlbum) {
        checkForEmptyCdAlbumException();
        if (newAlbum == null) {
            throw new NullAlbumObjectException("Album object is null!");
        }
        int index = getIndexById(id);
        cdAlbumsList.set(index, newAlbum);
        System.out.println("Album correctly edited!");
    }

    public List<CdAlbum> searchFor(String search) {
        checkForEmptyCdAlbumException();
        return cdAlbumsList.stream()
                .filter(e -> e.getArtist().toLowerCase().contains(search.toLowerCase()) ||
                        e.getTitle().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public void deleteRecord(int id) {
        checkForEmptyCdAlbumException();
        boolean isDeleted = cdAlbumsList.removeIf(album -> album.getCdId() == id);
        if (isDeleted) {
            System.out.printf("Album with id : %d removed succesfully! \n", id);
        } else {
            System.out.printf("Album with id : %d not found! \n", id);
        }
    }

    @Override
    public String getStatistics() {
        checkForEmptyCdAlbumException();
        DoubleSummaryStatistics priceStat =
                cdAlbumsList.stream().mapToDouble(CdAlbum::getPriceBought).summaryStatistics();

        return "\nNumber of CD's on the shelf : " + priceStat.getCount() +
                "\nThe cheapest CD: " + priceStat.getMin() +
                "\nThe most expensive CD: " + priceStat.getMax() +
                "\nAvg bought price CD: " + priceStat.getAverage();
    }

    @Override
    public List<CdAlbum> getSortedByTitleRecords() {
        checkForEmptyCdAlbumException();
        return cdAlbumsList.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<CdAlbum> getSortedByArtistRecords() {
        checkForEmptyCdAlbumException();
        return cdAlbumsList.stream()
                .sorted(Comparator.comparing(CdAlbum::getArtist))
                .collect(Collectors.toList());
    }

    @Override
    public List<CdAlbum> getSortedByIdAsc() {
        checkForEmptyCdAlbumException();
        return cdAlbumsList.stream()
                .sorted(Comparator.comparingInt(CdAlbum::getCdId))
                .collect(Collectors.toList());
    }

    @Override
    public List<CdAlbum> getSortedByIdDesc() {
        checkForEmptyCdAlbumException();
        return cdAlbumsList.stream()
                .sorted((x, y) -> y.getCdId() - x.getCdId())
                .collect(Collectors.toList());
    }

    @Override
    public List<CdAlbum> getSortedByRecordPrice() {
        checkForEmptyCdAlbumException();
        return cdAlbumsList.stream()
                .sorted(Comparator.comparingInt(x -> (int) x.getPriceBought()))
                .collect(Collectors.toList());
    }

    @Override
    public void readDb() {
        try (InputStream is = Files.newInputStream(Paths.get("albumlist.ser"))) {
            try (ObjectInputStream input = new ObjectInputStream(is)){
                cdAlbumsList = (List<CdAlbum>) input.readObject();
                System.out.println("\nDatabase read successfull...");
                getMaxIdFromDbList().ifPresentOrElse(e -> CdAlbum.setId(e + 1), () -> CdAlbum.setId(1));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeDb() {
        checkForEmptyCdAlbumException();
        try (OutputStream os = Files.newOutputStream(Paths.get("albumlist.ser"))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(cdAlbumsList);
            System.out.println("Database write successful...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getIndexById(int id) {
        checkForEmptyCdAlbumException();
        int indexOfAlbum = -1;
        for (CdAlbum album : cdAlbumsList) {
            if (album.getCdId() == id) {
                indexOfAlbum = cdAlbumsList.indexOf(album);
            }
        }
        return indexOfAlbum;
    }

    public boolean isAlbumInList(int id) {
        checkForEmptyCdAlbumException();
        for (CdAlbum album : cdAlbumsList) {
            if (album.getCdId() == id) {
                return true;
            }
        }
        return false;
    }

    private Optional<Integer> getMaxIdFromDbList() {
        return cdAlbumsList.stream()
                .max((Comparator.comparingInt(CdAlbum::getCdId)))
                .map(CdAlbum::getCdId);
    }
}

// POPULATE IN CASE OF EMPTY DB / OR DB DEGUG
//    private void populateAlbumsList() {
//        addRecord(new CdAlbum("Jerry Cantrell", "Bogy Depot", "rock album", 36.99));
//        addRecord(new CdAlbum("Alice in Chains", "Facelift", "grunge album", 43.19));
//        addRecord(new CdAlbum("Nirvana", "Nevermind", "grunge album", 59.69));
//        addRecord(new CdAlbum("Stone temple pilots", "Core", "rock album", 13.99));
//        addRecord(new CdAlbum("Death", "Scream Bloody Gore", "metal album", 29.99));
//    }