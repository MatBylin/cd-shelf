package pack.cd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pack.cd.exceptions.NullAlbumObjectException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CdLibraryTest {

    private CdLibrary lib = null;

    @BeforeEach
    void setUp() {
        lib = new CdLibrary();
    }

    @Test
    void afterInitializationCdAlbumListShouldBeEmpty() {
        assertEquals(6, lib.getCdAlbumsList().size());
    }

    @Test
    void addNullObjectAlbumShouldThrowNullAlbumObjectException() {
        assertThrows(NullAlbumObjectException.class, () ->
                lib.addRecord(null));
    }

    @Test
    void afterAddingOneRecordCdAlbumListSouldHaveSizeEqualsOne() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        assertEquals(7, lib.getCdAlbumsList().size());
    }

    @Test
    void afterAddingTwoRecordsCdAlbumListSouldHaveSizeEqualsTwo() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        assertEquals(8, lib.getCdAlbumsList().size());
    }

    @Test
    void afterAddingTwoRecordsAndRemoveOneCdAlbumListSouldHaveSizeEqualsOne() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));

        int id = lib.getCdAlbumsList().get(0).getCdId();
        lib.deleteRecord(id);
        assertEquals(7, lib.getCdAlbumsList().size());
    }

    @Test
    void recordThenShouldBeEditable() {
        lib.editRecord(lib.getCdAlbumsList().get(0).getCdId(), new CdAlbum("x", "y", "z", 1.10));

        assertEquals("x", lib.getCdAlbumsList().get(0).getArtist());
    }

    @Test
    void editRecordWithNullCdAlbumShouldThrowException() {
        lib.addRecord(new CdAlbum("a", "b", "c", 1.10));

        assertThrows(NullAlbumObjectException.class, () ->
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
    void printStatisticShouldReturnNotNullString() {
        String statistics = lib.getStatistics();
        assertNotNull(statistics);
    }

    @Test
    void getSortedByArtistShouldSortAlbumListByArtistAlphabetically() {
        List<CdAlbum> sorted = lib.getSortedByArtistRecords();
        assertEquals("Alice in Chains", sorted.get(0).getArtist());
    }

    @Test
    void getSortedByTitleShouldSortAlbumListByTitleAlphabetically() {
        List<CdAlbum> sorted = lib.getSortedByTitleRecords();
        assertEquals("Jerry Cantrell", sorted.get(0).getArtist());
    }

    @Test
    void getSortedByPriceShouldSortAlbumListByPriceAsc() {
        List<CdAlbum> sorted = lib.getSortedByRecordPrice();
        assertEquals("Stone temple pilots", sorted.get(0).getArtist());
    }

    @Test
    void isAlbumInListWhenAlbumWithIdNotExistShouldReturnFalse() {
        lib.addRecord(new CdAlbum("z", "y", "x", 300));
        assertFalse(lib.isAlbumInList(99999));
    }
}
