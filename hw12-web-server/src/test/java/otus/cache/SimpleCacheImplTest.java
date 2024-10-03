package otus.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.cache.SimpleCacheImpl;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("java:S2925")
@DisplayName("Самодельный кэш должен")
class SimpleCacheImplTest {

    @Test
    @DisplayName("очищаться при недостатке памяти")
    void cacheShouldBePurgedAfterGCIsCalled() throws Exception {
        SimpleCacheImpl<String, BigObject> cache = new SimpleCacheImpl<>();
        for (int i = 0; i < 1000; i++) {
            cache.put("key" + i, new BigObject());
        }

        Field declaredField = SimpleCacheImpl.class.getDeclaredField("cache");
        declaredField.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) declaredField.get(cache);

        assertTrue(!map.isEmpty() && map.size() < 1000);

        System.gc();
        TimeUnit.MILLISECONDS.sleep(500);
        assertTrue(map.isEmpty());
    }

    private static class BigObject {
        private int[] array;

        public BigObject() {
            this.array = new int[1024 * 1024];
        }
    }
}
