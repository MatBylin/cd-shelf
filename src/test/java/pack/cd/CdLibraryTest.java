package pack.cd;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pack.cd.exceptions.EmptyCdAlbumException;
import pack.cd.exceptions.NullAlbumObjectException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CdLibraryTest {

    private CdLibrary lib = null;

    @BeforeEach
    void setUp() {
        lib = new CdLibrary(true);
    }

    @AfterEach
    void tearDown()  {
        lib = null;
    }

    @Test
    void afterInitializationCdAlbumListShouldBeEmpty() {
        assertEquals(0, lib.getCdAlbumsList().size());
    }

    @Test
    void afterInitializationCdLibraryMethodsShouldThrowEmptyCdAlbumListException() {
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.printRecords());
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.editRecord(1, new CdAlbum("a", "b", "c", 1.10)));
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.searchFor("a"));
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.deleteRecord(1));
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.getStatistics());
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.getSortedByIdAsc());
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.getSortedByArtistRecords());
        assertThrows(EmptyCdAlbumException.class, ()->
                lib.writeDb());
    }

    @Test
    void addNullObjectAlbumShouldThrowNullAlbumObjectException() {
        assertThrows(NullAlbumObjectException.class, ()->
                lib.addRecord(null));
    }

    @Test
    void afterAddingOneRecordCdAlbumListSouldHaveSizeEqualsOne() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        assertEquals(1, lib.getCdAlbumsList().size());
    }

    @Test
    void afterAddingTwoRecordsCdAlbumListSouldHaveSizeEqualsTwo() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        assertEquals(2, lib.getCdAlbumsList().size());
    }

    @Test
    void afterAddingTwoRecordsAndRemoveOneCdAlbumListSouldHaveSizeEqualsOne() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));

        int id = lib.getCdAlbumsList().get(0).getCdId();
        lib.deleteRecord(id);
        assertEquals(1, lib.getCdAlbumsList().size());
    }

    @Test
    void addOneRecordThenShouldBeEditable() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        lib.editRecord(lib.getCdAlbumsList().get(0).getCdId(), new CdAlbum("x", "y", "z", 1.10));

        assertEquals(1, lib.getCdAlbumsList().size());
        assertEquals("x", lib.getCdAlbumsList().get(0).getArtist());
    }

    @Test
    void editRecordWithNullCdAlbumShouldThrowException() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));

        assertThrows(NullAlbumObjectException.class, ()->
                lib.editRecord(lib.getCdAlbumsList().get(0).getCdId(), null));
    }

    @Test
    void searchResultForExistingPatternShouldReturnValidListSize() {
        lib.addRecord(new CdAlbum("ala", "ma", "kota", 1.10));
        List<CdAlbum> searchAlbum = lib.searchFor("ala");
        assertEquals(1, searchAlbum.size());
    }

    @Test
    void searchResultForExistingPatternShouldBeCaseInsensitive() {
        lib.addRecord(new CdAlbum("ala", "ma", "kota", 1.10));
        List<CdAlbum> searchAlbum = lib.searchFor("ALA");
        assertEquals(1, searchAlbum.size());
    }

    @Test
    void searchResultForExistingPatternsShouldReturnValidListSize() {
        lib.addRecord(new CdAlbum("ala", "ma", "kota", 1.10));
        lib.addRecord(new CdAlbum("ala", "ma", "kota", 1.10));
        List<CdAlbum> searchAlbum = lib.searchFor("ala");
        assertEquals(2, searchAlbum.size());
    }

    @Test
    void searchResultForNonExistingPatternShouldReturnListSizeEqualsZero() {
        lib.addRecord(new CdAlbum("ala", "ma", "kota", 1.10));
        List<CdAlbum> searchAlbum = lib.searchFor("idontexist");
        assertEquals(0, searchAlbum.size());
    }

    @Test
    void printStatisticsOnNotEmptyCdAlbumListShouldReturnNotNullString() {
        lib.addRecord(new CdAlbum("ala", "ma", "kota", 1.10));
        String statistics = lib.getStatistics();
        assertNotNull(statistics);
    }

    @Test
    void getSortedByArtistShouldSortAlbumListByArtistAlphabetically() {
        lib.addRecord(new CdAlbum("z", "y", "x", 1));
        lib.addRecord(new CdAlbum("a", "b", "c", 2));

        List<CdAlbum> sorted = lib.getSortedByArtistRecords();
        assertEquals("a", sorted.get(0).getArtist());
    }

    @Test
    void getSortedByTitleShouldSortAlbumListByTitleAlphabetically() {
        lib.addRecord(new CdAlbum("z", "y", "x", 1));
        lib.addRecord(new CdAlbum("a", "b", "c", 2));

        List<CdAlbum> sorted = lib.getSortedByTitleRecords();
        assertEquals("a", sorted.get(0).getArtist());
    }

    @Test
    void getSortedByPriceShouldSortAlbumListByPriceAsc() {
        lib.addRecord(new CdAlbum("z", "y", "x", 300));
        lib.addRecord(new CdAlbum("a", "b", "c", 2));

        List<CdAlbum> sorted = lib.getSortedByRecordPrice();
        assertEquals("a", sorted.get(0).getArtist());
    }

    @Test
    void getSortedByIdDescShouldSortAlbumListByCdIdDesc() {
        lib.addRecord(new CdAlbum("z", "y", "x", 300));
        lib.addRecord(new CdAlbum("a", "b", "c", 2));
        int id = lib.getCdAlbumsList().get(1).getCdId();

        List<CdAlbum> sorted = lib.getSortedByIdDesc();
        assertEquals(id, sorted.get(0).getCdId());
    }

    @Test
    void readTestDatabaseShouldReturnListSizeEqualsSix() {
        lib.readTestDb();
        assertEquals(8, lib.getCdAlbumsList().size());
    }

    @Test
    void isAlbumInListWhenAlbumWithIdExistShouldReturnTrue() {
        lib.addRecord(new CdAlbum("z", "y", "x", 300));
        int id = lib.getCdAlbumsList().get(0).getCdId();
        assertTrue(lib.isAlbumInList(id));
    }

    @Test
    void isAlbumInListWhenAlbumWithIdNotExistShouldReturnFalse() {
        lib.addRecord(new CdAlbum("z", "y", "x", 300));
        assertFalse(lib.isAlbumInList(99999));
    }
}
