import com.xingbingxuan.blog.utils.TokenUtil;

/**
 * @author : xbx
 * @date : 2022/4/23 22:53
 */
public class test {

    public static void main(String[] args) {

        String s = TokenUtil.addUser("122222",  120);
        System.out.println(s);
        String userByToken = TokenUtil.getUserByToken(s);
        System.out.println(userByToken);
    }
}
