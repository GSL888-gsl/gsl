import com.isoft.service.NewsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @org.junit.Test
    public void test() {
//        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml") ;
//        UserService userService = (UserService) app.getBean("userServiceImpl");
//        System.out.println(userService.getById(1));
//        String s= MD5Util.getMD5("123");
//        System.out.println(s);

        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml") ;
//        UserDao userDao= (UserDao) app.getBean("userDao");
//        System.out.println(userDao.getNameCounts("aa"));
//
//        Users users=new Users();
//        users.setId(1);
//        users.setPassword(MD5Util.MD5("123"));
//        System.out.println(userDao.update(users));
//        UserService userService= (UserService) app.getBean("userServiceImpl");
//        System.out.println(userService.getLogin("郭盛林","333"));
//        Users users=new  Users();
//        users.setName("郭盛林");
//        users.setPassword("123");
//        System.out.println(userService.register(users));
//        NewsDao newsDao= (NewsDao) app.getBean("newsDao");
//        System.out.println(newsDao.getCount("高","2020-07-16",1));
//        System.out.println(newsDao.get("%","2020-07-10",2,0,2,"title","asc"));
        NewsService newsService= (NewsService) app.getBean("newsServiceImpl");
        System.out.println(newsService.delone(16));
//        System.out.println( newsService.getData(null,null,null,1,4,null,null));
//
//        News news=new News();
////        news.setId(1);
//        System.out.println(newsService.del(1));
    }
}
