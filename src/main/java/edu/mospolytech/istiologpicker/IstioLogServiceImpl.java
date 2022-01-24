package edu.mospolytech.istiologpicker;

import java.util.List;
import org.apache.spark.api.java.JavaRDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;


public class IstioLogServiceImpl {
   private static final Logger LOGGER = LoggerFactory.getLogger(IstioLogServiceImpl.class);
   
   public List<Tuple2<Integer, String>> getTraffic(JavaRDD<String> lines, String trafficType){
      return lines.filter(line -> line.startsWith("["))
            .map(IstioLogUtils::getLog)
            .filter(line -> !line.contains("\"-\""))
            .filter(line -> line.contains(trafficType))
            .mapToPair(IstioLogUtils::joinLogs)
            .reduceByKey((a, b) -> a + b)
            .mapToPair(Tuple2::swap)
            .sortByKey(false)
            .collect();
   
             
   }
}
