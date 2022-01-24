package edu.mospolytech.istiologpicker;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;


public class Main {
   private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
   
   public static void main(String[] args) {
      IstioLogServiceImpl istioLogServiceImpl = new IstioLogServiceImpl();
      
      if(args.length == 0){
         throw new RuntimeException("Не указан путь к файлу");
      }
      
      String path = args[0];
      String trafficType = "inbound";
      
      if(Boolean.getBoolean("outboundTraffic")){
         trafficType = "outbound";
      }
      
      SparkConf sparkConf = new SparkConf().setAppName("spark_istio_logs").setMaster("local[*]");
      
      JavaSparkContext sc = new JavaSparkContext(sparkConf);
      JavaRDD rdd = sc.textFile(path);
      
      List<Tuple2<Integer, String>> result = istioLogServiceImpl.getTraffic(rdd, trafficType);
      
      try(PrintWriter writer = new PrintWriter("output.txt", "UTF-8");) {

         for (Tuple2<Integer, String> tuple2 : result) {
            writer.println(tuple2._1 + " - число вызовов. " + tuple2._2 + " - название кластера");
         }
      } catch (Exception e) {
         throw new RuntimeException("Ошибка при попытке записать в файл output.txt");
      }
           
   }
}
