import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import parcs.*;

public class Bluck implements AM {
    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("Bluck.jar");
        AMInfo info = new AMInfo(curtask, null);
        long startTime = System.nanoTime();

//        (new Bluck()).run(info);
        int n=fromFilen(info.curtask.findFile("input"));
        ArrayList<ArrayList<Integer>> a = fromFile(info.curtask.findFile("input"));
        int maxWorkers = 4;
        point[] ps = new point[maxWorkers];
        channel[] cs = new channel[maxWorkers];
        for (int i = 0; i < maxWorkers; i += 1) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("Bluck");
            /*ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = a.size() / maxWorkers * i; j < a.size() / maxWorkers * (i + 1); ++j) {
                tmp.add(a.get(j));
            }*/
            c.write(n);
            c.write(a);

            ps[i] = p;
            cs[i] = c;
        }

        /*ArrayList<byte[]> t = new ArrayList<>();
        for(int i = 0; i < maxWorkers; i += 1) {
            ArrayList<Byte> res = (ArrayList<Byte>)cs[i].readObject();
            t.add(convertToByteArray(res));
        }
//        System.out.println(HexBin.encode(t.get(0)));
//        System.out.println(HexBin.encode(t.get(1)));
        byte[] res = buildMerkle(t);

        System.out.println("Waiting for result...");
        System.out.println("Result: " + HexBin.encode(res));

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time in millis: " + elapsedTime / 1000000);
        curtask.end();*/
    }

    public void run(AMInfo info) {
            int n = (int)info.parent.readObject();
            ArrayList<ArrayList<Integer>> a=(ArrayList<ArrayList<Integer>>)info.parent.readObject();
            System.out.println(n);
        }

        /*System.out.println("Task started with len = " + a.size());
        byte[] x;
//        if (depth >= 3) {
        if (true) {
            try {
                x = buildMerkleInit(a);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            point p1 = info.createPoint();
            channel c1 = p1.createChannel();
            p1.execute("Bluck");
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < a.size() / 2; ++i) {
                tmp.add(a.get(i));
            }
            c1.write(tmp);
            c1.write(depth + 1);

            point p2 = info.createPoint();
            channel c2 = p2.createChannel();
            p2.execute("Bluck");
            tmp.clear();
            for (int i = a.size() / 2; i < a.size(); ++i) {
                tmp.add(a.get(i));
            }
            c2.write(tmp);
            c2.write(depth + 1);


            ArrayList<Byte> res1 = (ArrayList<Byte>)c1.readObject();
            ArrayList<Byte> res2 = (ArrayList<Byte>)c2.readObject();
            ArrayList<byte[]> t = new ArrayList<>();
            t.add(convertToByteArray(res1));
            t.add(convertToByteArray(res2));
            System.out.println(HexBin.encode(t.get(0)));
            System.out.println(HexBin.encode(t.get(1)));
            try {
                x = buildMerkle(t);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        System.out.println("Task finished");

        if (info.parent == null) {
            System.out.println("Result: " + HexBin.encode(x));
        } else {
            ArrayList<Byte> list = new ArrayList<>();
            for(byte i: x) {
                list.add(i);
            }
            info.parent.write(list);
        }

    }*/
    public static int fromFilen(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        int n = sc.nextInt();
        return n;
    }
    public static ArrayList<ArrayList<Integer>> fromFile(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        int n = sc.nextInt();
        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++) 
        for(int j=0;j<n;j++){
            a.get(i).add(sc.nextInt());
        }
        return a;
    }

}
