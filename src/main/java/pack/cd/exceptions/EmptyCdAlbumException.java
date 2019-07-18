package pack.cd.exceptions;

//unchecked
public class EmptyCdAlbumException extends RuntimeException {

    public EmptyCdAlbumException() {}

    public EmptyCdAlbumException(String message) {
        super(message);
    }
}
