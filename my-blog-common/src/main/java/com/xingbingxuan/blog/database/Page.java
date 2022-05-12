package com.xingbingxuan.blog.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/4 10:25
 */
@Data
@NoArgsConstructor
public class Page<T> {

    //返回的记录集合
    private List<T> rows;
    //总记录条数
    private int totalCount;
    // 当前页数
    private int pageNow = 1;
    // 每页显示记录的条数
    private int pageSize = 8;
    // 总的页数
    private int totalPageCount;
    // 是否有首页
    private boolean hasFirst;
    // 是否有前一页
    private boolean hasPre;
    // 是否有下一页
    private boolean hasNext;
    // 是否有最后一页
    private boolean hasLast;



    /**
     * 通过构造函数 传入 总记录数 和 当前页
     * @param totalCount
     * @param pageNow
     */
    public Page(int totalCount, int pageNow) {
        this.totalCount = totalCount;
        this.pageNow = pageNow;
    }
    /**
     * 取得总页数，总页数=总记录数/总页数
     * @return
     */
    public int getTotalPageCount() {
        totalPageCount = getTotalCount() / getPageSize();
        return (totalCount % pageSize == 0) ? totalPageCount
                : totalPageCount + 1;
    }
    /**
     * 取得选择记录的初始位置
     * @return
     */
    public int getStartPos() {
        return (pageNow - 1) * pageSize;
    }
    /**
     * 是否是第一页
     * @return
     */
    public boolean isHasFirst() {
        return pageNow != 1;
    }
    /**
     * 是否有首页
     * @return
     */
    public boolean isHasPre() {
        // 如果有首页就有前一页，因为有首页就不是第一页
        return isHasFirst();
    }
    /**
     * 是否有下一页
     * @return
     */
    public boolean isHasNext() {
        // 如果有尾页就有下一页，因为有尾页表明不是最后一页
        return isHasLast();
    }
    /**
     * 是否有尾页
     * @return
     */
    public boolean isHasLast() {
        // 如果不是最后一页就有尾页
        return pageNow != getTotalCount();
    }
}
