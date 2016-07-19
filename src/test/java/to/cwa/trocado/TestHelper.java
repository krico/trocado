package to.cwa.trocado;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import to.cwa.trocado.om.HasId;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static junit.framework.TestCase.*;

/**
 * @author krico
 * @since 08/05/16.
 */
public class TestHelper {
    private static PodamFactory podamFactory;

    public static void assertUtilityClassWellDefined(Class<?> clazz) throws Exception {
        String name = clazz.getName();
        assertTrue(name + " must be final", Modifier.isFinal(clazz.getModifiers()));

        assertEquals(name + " must have a single constructor " + Arrays.toString(clazz.getDeclaredConstructors()), 1, clazz.getDeclaredConstructors().length);
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
            fail(name + " must have private constructor");
        }
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
        for (Method method : clazz.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(clazz)) {
                fail(name + " must have only static methods:" + method);
            }
        }
    }

    private static PodamFactory podamFactory() {
        if (podamFactory == null) {
            PodamFactory factory = new PodamFactoryImpl();
            factory.getClass();  //customize
            podamFactory = factory;
        }
        return podamFactory;
    }

    public static <T> T makePojo(Class<T> klass) {
        return podamFactory().manufacturePojo(klass);
    }

    public static <T extends HasId<?>> T makeEntity(Class<T> klass) {
        T pojo = makePojo(klass);
        pojo.setId(null);
        return pojo;
    }

    public static Closeable beginObjectify() {
        Trocado.initializeObjectify();
        return ObjectifyService.begin();
    }

}
