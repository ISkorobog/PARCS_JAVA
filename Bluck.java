import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import parcs.*;

public class Bluck implements AM {
    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("Bluck.jar");
        AMInfo info = new AMInfo(curtask, null);
        long startTime = System.nanoTime();

        int n=fromFilen(info.curtask.findFile("input"));
        ArrayList<ArrayList<Integer>> a = fromFile(info.curtask.findFile("input"));
        int maxWorkers = 4;
        point[] ps = new point[maxWorkers];
        channel[] cs = new channel[maxWorkers];
        int step=n/maxWorkers+1;
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
            c.write(step*i);
            c.write(step*i+step);
            ps[i] = p;
            cs[i] = c;
        }
        
        ArrayList<Double> x=new ArrayList<Double>();
        for(int i=0;i<n;i++)x.add(0.0);
        for(int t=1;t<=n;t++){

        
        for(int i=0;i<maxWorkers;i+=1){
            cs[i].write(x);
        }
        ArrayList<ArrayList<Double>> ans= new ArrayList<ArrayList<Double>>();
        for(int i=0;i<maxWorkers;i+=1){
            ans.add((ArrayList<Double>)cs[i].readObject());
        }
        ArrayList<Double> newx= new ArrayList<>();
        for (ArrayList<Double> q :ans) {
            for (double xx : q) {
                newx.add(xx);
            }
        }
        x=newx;
    }
    for(int i=0;i<n;i++)System.out.print(x.get(i).toString()+" ");
      

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time in millis: " + elapsedTime / 1000000);
        curtask.end();
    }

    public void run(AMInfo info) {
            int n = info.parent.readInt();
            ArrayList<ArrayList<Integer>> a=(ArrayList<ArrayList<Integer>>)info.parent.readObject();
            int l= info.parent.readInt();
            int r=info.parent.readInt();
            if(l>=n)l=n-1;
            if(r>n)r=n;
            for(int t=1;t<=n;t++){
                ArrayList<Double> x=(ArrayList<Double>)info.parent.readObject();
                ArrayList<Double> xnew=new ArrayList<Double>();
                for(int k=l;k<r;k++){
                    xnew.add((double)a.get(k).get(n));
                    for(int i=0;i<n;i++){
                        if(i!=k)xnew.set(k-l, xnew.get(k-l)-a.get(k).get(i)*x.get(i));
                    }
                    xnew.set(k-l, xnew.get(k-l)/a.get(k).get(k));
                }
                
            info.parent.write(xnew);
            }
        }

       
    public static int fromFilen(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        int n = sc.nextInt();
        return n;
    }
    public static ArrayList<ArrayList<Integer>> fromFile(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        int n = sc.nextInt();
        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < n; i++){ a.add(new ArrayList<Integer>());
        for(int j=0;j<=n;j++){
            a.get(i).add(sc.nextInt());
        }}
        return a;
    }

}
