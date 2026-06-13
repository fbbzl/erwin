package lang;

import org.fz.erwin.lang.NullSafe;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2025/8/9 14:06
 */

public class NullSafeTest {

    public static void main(String[] args) {
        Integer length = NullSafe.nullDefault(() -> {
            String a = null;
            return a.length();
        }, () -> 1);

        System.err.println(length);
    }
}
