package pack;

import pack.cd.CdAlbum;
import pack.cd.CdLibrary;

import java.util.List;
import java.util.Scanner;

import static pack.UIHelper.createCdAlbumFromUser;
import static pack.UIHelper.getIdFromUser;

public class ConsoleUI {

    private static Scanner sc = new Scanner(System.in);
    private static CdLibrary lib = CdLibrary.getInstance();

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
                    deleteRecord();
                    break;
                case 7:
                    readDb();
                    break;
                case 8:
                    writeDb();
                    break;
                case 9:
                    exit();
                    break;
            }
        }
    }

    private static void printRecord() {
        lib.printRecords();
    }


    private static void addRecord() {
        CdAlbum album = createCdAlbumFromUser();
        lib.addRecord(album);
    }

    private static void printSortedMenu() {
        int sortOption = UIHelper.getSortOptionFromUser();
        switch (sortOption) {
            case 1:
                lib.printSortedByTitleRecords();
                break;
            case 2:
                lib.printSortedByArtistRecords();
                break;
            case 3:
                lib.printSortedByRecordPrice();
                break;
            case 4:
                lib.printSortedByIdAsc();
                break;
            case 5:
                lib.printSortedByIdDesc();
                break;
            default:
                System.out.println("Option not fount");
        }
    }


    private static void editRecord() {
        int id = getIdFromUser();
        CdAlbum editAlbum = createCdAlbumFromUser();
        lib.editRecord(id, editAlbum);
    }

    private static void searchForPattern() {
        String search = UIHelper.getSearchStringFromUser();
        lib.searchFor(search);
    }

    private static void deleteRecord() {
        int id = getIdFromUser();
        lib.deleteRecord(id);
    }

    private static void readDb() {
        lib.readDb();
    }

    private static void writeDb() {
        lib.writeDb();
    }

    private static void exit() {
        System.exit(1);
    }
}
