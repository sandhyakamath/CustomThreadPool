import com.godel.CustomThreadPool.CustomThreadPool;

public class test {
    public static void main(String[] args) {
        CustomThreadPool tp = new CustomThreadPool(2);
        for (int i =1; i < 10; i++) {
            final int x = i;
            Runnable runnable = () -> {
                System.out.println(x*7);
            };
            tp.submit(runnable);
        }
    }
}
