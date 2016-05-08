package to.cwa.trocado.om.upload;

/**
 * @author krico
 * @since 08/05/16.
 */
public class UploadResult {
    private String id;
    private String name;
    private int size;
    private String type;
    private long lastModified;

    public UploadResult() {
    }

    public UploadResult(String id, Upload origin) {
        this.id = id;
        this.name = origin.getName();
        this.size = origin.getSize();
        this.type = origin.getType();
        this.lastModified = origin.getLastModified();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
