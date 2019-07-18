package pack.cd.exceptions;

//unchecked
public class NullAlbumObjectException extends RuntimeException {
    public NullAlbumObjectException() {
    }

    public NullAlbumObjectException(String message) {
        super(message);
    }
}
