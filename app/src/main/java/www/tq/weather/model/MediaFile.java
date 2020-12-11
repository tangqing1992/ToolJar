package www.tq.weather.model;

public class MediaFile {
    private String path;
    private long time;
    private String name;
    private FileType fileType;
    public enum FileType{ img, video, music };

    public MediaFile(String path, long time, String name,FileType fileType) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.fileType = fileType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public FileType getFileType() {
        return fileType;
    }
}
