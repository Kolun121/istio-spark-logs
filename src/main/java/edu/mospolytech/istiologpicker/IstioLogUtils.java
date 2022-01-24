package edu.mospolytech.istiologpicker;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;


public class IstioLogUtils {
   private static final Logger LOGGER = LoggerFactory.getLogger(IstioLogUtils.class);
   
   private static List<Integer> allowedValues = Arrays.asList(17, 18, 19, 20, 21);
   
   public static String getLog(String log){
      StringBuilder stringBuilder = new StringBuilder();
     
      
      //Вид строки "[%START_TIME%] \"%REQ(:METHOD)% %REQ(X-ENVOY-ORIGINAL-PATH?:PATH)% %PROTOCOL%\" %RESPONSE_CODE% %RESPONSE_FLAGS% \"%DYNAMIC_METADATA(istio.mixer:status)%\" \"%UPSTREAM_TRANSPORT_FAILURE_REASON%\" %BYTES_RECEIVED% %BYTES_SENT% %DURATION% %RESP(X-ENVOY-UPSTREAM-SERVICE-TIME)% \"%REQ(X-FORWARDED-FOR)%\" \"%REQ(USER-AGENT)%\" \"%REQ(X-REQUEST-ID)%\" \"%REQ(:AUTHORITY)%\" \"%UPSTREAM_HOST%\" %UPSTREAM_CLUSTER% %UPSTREAM_LOCAL_ADDRESS% %DOWNSTREAM_LOCAL_ADDRESS% %DOWNSTREAM_REMOTE_ADDRESS% %REQUESTED_SERVER_NAME% %ROUTE_NAME%\n"
      String[] logParts = log.split("\\s+");
      
      for(int i = 1; i <= logParts.length - 1; i++){
         if(allowedValues.contains(i)){
            stringBuilder.append(logParts[i - 1]).append(" ");
         }
      }
      
      stringBuilder.trimToSize();
      
      return stringBuilder.toString();
   }
   
   public static Tuple2<String, Integer> joinLogs(String log){      
      String[] logParts = log.split("\\s+");
      
      return new Tuple2<>(logParts[1], 1);
   }
}
