package com.self.forkJoin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 计算一个目录的大小
 * 通过递归的方式,算出每个文件的大小然后做和
 * 采用java fork join框架
 * Created by shaojieyue
 * Created time 2018-10-04 17:45
 */
public class DirSizeCalculate {
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    public long dirSize(String file) {
        final ForkJoinTask<Long> submit = forkJoinPool.submit(new SumTask(new File(file)));
        try {
            final Long integer = submit.get();
            return integer;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return 0;
    }

    class SumTask extends RecursiveTask<Long> {
        private File file;



        public SumTask(File file) {
            this.file = file;
        }

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         */
        @Override
        protected Long compute() {
            if (!file.exists()) {
                System.out.println(file.getAbsolutePath());
                return 0L;
            }
            if (file.isFile()) {
                return file.length();
            } else if (file.isDirectory()) {
                final File[] files = file.listFiles();
                long sum = 0;
                for (File file1 : files) {
                    final SumTask sumTask = new SumTask(file1);
                    sumTask.fork();
                    sum = sum + sumTask.join();
                }
                return sum;
            } else if(Files.isSymbolicLink(file.toPath())) { //这块存在问题,因为先判断了是否有file
                final SumTask sumTask;
                try {
                    final File file = Files.readSymbolicLink(this.file.toPath()).toAbsolutePath().toFile();
                    System.out.println(file);
                    sumTask = new SumTask(file);
                    sumTask.fork();
                    return sumTask.join();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0L;
            }else {
                System.out.println(file.getAbsolutePath());
            }
            return 0L;
        }
    }

    public static void main(String[] args) throws IOException {
        DirSizeCalculate dirSizeCalculate = new DirSizeCalculate();
        long size = dirSizeCalculate.dirSize("/home/shaojieyue/tools");
        System.out.println(size);
    }
}
