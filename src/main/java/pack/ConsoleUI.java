package pack;

import pack.cd.CdAlbum;
import pack.cd.CdLibrary;
import pack.cd.exceptions.EmptyCdAlbumException;
import pack.cd.exceptions.NullAlbumObjectException;

import java.util.List;
import java.util.Scanner;

import static pack.UIHelper.createCdAlbumFromUser;
import static pack.UIHelper.getIdFromUser;

public class ConsoleUI {

    private static Scanner sc = new Scanner(System.in);
    private static CdLibrary lib = new CdLibrary();

    public static void main(String[] args) {

        System.out.println("********** C D - S H E L F ********** ");
        while (true) {
            System.out.println(MenuHelper.MENU);
            System.out.print("Provide your menu option : ");
            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    printRecord();
                    break;
                case 2:
                    addRecord();
                    break;
                case 3:
                    printSortedMenu();
                    break;
                case 4:
                    editRecord();
                    break;
                case 5:
                    searchForPattern();
                    break;
                case 6:
                    printStatistics();
                    break;
                case 7:
                    deleteRecord();
                    break;
                case 8:
                    readDb();
                    break;
                case 9:
                    writeDb();
                    break;
                case 10:
                    exit();
                    break;
            }
        }
    }

    //OPTION 1 PRINT RECORDS
    private static void printRecord() {
        try {
            lib.printRecords();
        } catch (EmptyCdAlbumException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //OPTION 2 ADD RECORD
    private static void addRecord() {
        try {
            CdAlbum album = createCdAlbumFromUser();
            lib.addRecord(album);
        } catch (NullAlbumObjectException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void printSortedMenu() {
        int sortOption = UIHelper.getSortOptionFromUser();
        try {
            switch (sortOption) {
                case 1:
                    lib.printRecords(lib.getSortedByTitleRecords());
                    break;
                case 2:
                    lib.printRecords(lib.getSortedByArtistRecords());
                    break;
                case 3:
                    lib.printRecords(lib.getSortedByRecordPrice());
                    break;
                case 4:
                    lib.printRecords(lib.getSortedByIdAsc());
                    break;
                case 5:
                    lib.printRecords(lib.getSortedByIdDesc());
                    break;
                default:
                    System.out.println("Option not found!!!");
            }
        } catch (EmptyCdAlbumException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void editRecord() {
        try {
            int id = getIdFromUser();
            if (lib.isAlbumInList(id)) {
                CdAlbum editAlbum = createCdAlbumFromUser();
                lib.editRecord(id, editAlbum);
            } else {
                System.out.println("There is no album with id " + id);
            }
        } catch (EmptyCdAlbumException ex) {
            System.out.println(ex.getMessage());
        } catch (NullAlbumObjectException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void searchForPattern() {
        try{
            String search = UIHelper.getSearchStringFromUser();
            List<CdAlbum> searchAlbums = lib.searchFor(search);
            if (searchAlbums.size() == 0) {
                System.out.printf("There is no result for '%s' pattern\n", search);
            }
            else{
                lib.printRecords(searchAlbums);
            }
        }
        catch(EmptyCdAlbumException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void printStatistics() {
        try{
            String stats = lib.getStatistics();
            System.out.println(stats);
        }
        catch(EmptyCdAlbumException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteRecord() {
        try{
            int id = getIdFromUser();
            lib.deleteRecord(id);
        }
        catch(EmptyCdAlbumException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void readDb() {
        lib.readDb();
    }

    private static void writeDb() {
        try{
            lib.writeDb();
        }
        catch(EmptyCdAlbumException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void exit() {
        System.exit(1);
    }
}
