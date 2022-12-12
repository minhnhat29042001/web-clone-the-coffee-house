package uit.javabackend.webclonethecoffeehouse.test;

import uit.javabackend.webclonethecoffeehouse.common.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestObjectMap {
    public static void main(String[] args) {
//        VnpayTransactionDto vnpayTransactionDto = new VnpayTransactionDto();
//        vnpayTransactionDto.setVnp_Amount("43434");
//        vnpayTransactionDto.setVnp_BankTranNo("dsdas");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String,String> fields = objectMapper.convertValue(vnpayTransactionDto,Map.class);
//
//        Iterator<Map.Entry<String,String>> iterator = fields.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry<String,String> entry = iterator.next();
//            if(entry.getValue().isEmpty()){
//                iterator.remove();
//            }
//        }
//
//        System.out.println(fields);


        String dateTimeConvert = "2022-12-03 19:29:14.883484";
        LocalDateTime d = LocalDateTime.parse(dateTimeConvert);
       // String converted = d.format(DateTimeFormatter.ofPattern(DateTimeUtils.DATETIME_FORMAT_VNPAY));
        System.out.println();
    }
}
