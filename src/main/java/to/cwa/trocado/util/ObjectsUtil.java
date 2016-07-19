package to.cwa.trocado.util;

import com.google.appengine.api.datastore.Category;
import com.googlecode.objectify.Ref;

/**
 * @author krico
 * @since 21/05/16.
 */
public final class ObjectsUtil {
    private ObjectsUtil() {
    }

    public static String toString(Category category) {
        return category == null ? null : category.getCategory();
    }

    public static <T> T get(Ref<T> ref) {
        if (ref == null) return null;
        return ref.get();
    }
}
