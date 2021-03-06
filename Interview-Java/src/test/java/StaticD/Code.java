package StaticD;

import java.sql.Date;

/**
 * 静态代码块
 *
 * @author xiaolong
 * @date 2019/5/20 18:11
 */
public class Code {
    private Date birthDate;
    private static Date startDate, endDate;

    static {
        startDate = Date.valueOf("1946");
        endDate = Date.valueOf("1964");
    }

    public Code(Date birthDate) {
        this.birthDate = birthDate;
    }

    boolean isBornBoomer() {
        return birthDate.compareTo(startDate) >= 0 && birthDate.compareTo(endDate) < 0;
    }
}
