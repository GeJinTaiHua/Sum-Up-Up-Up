package www.wjl.com.Interview.entity;

import lombok.Data;

import java.util.List;

/**
 * 聚合
 *
 * @author xiaolong
 * @date 2019/5/22 14:26
 */
@Data
public class Family {
    // 一个家庭里有许多You
    private List<You> children;
}
