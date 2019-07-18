package pack.cd.interfaces;

import pack.cd.CdAlbum;

import java.util.List;

public interface Sortable {

    List<CdAlbum> getSortedByTitleRecords();

    List<CdAlbum> getSortedByArtistRecords();

    List<CdAlbum> getSortedByRecordPrice();

    List<CdAlbum> getSortedByIdAsc();

    List<CdAlbum> getSortedByIdDesc();
}
