package to.cwa.trocado.upload.om;

import java.io.Serializable;

/**
 * @author krico
 * @since 07/05/16.
 */
public class Upload implements Serializable {
    private String name;
    private int size;
    private String type;
    private long lastModified;
    private String base64Data;

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

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
