package uit.javabackend.webclonethecoffeehouse.payment.vnp_payment.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.exception.GiraBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderStatus;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderService;
import uit.javabackend.webclonethecoffeehouse.payment.vnp_payment.dto.ResponseForVnpayDTO;
import uit.javabackend.webclonethecoffeehouse.payment.vnp_payment.dto.VnpPaymentCreateDTO;
import uit.javabackend.webclonethecoffeehouse.payment.vnp_payment.dto.VnpayQueryDTO;
import uit.javabackend.webclonethecoffeehouse.payment.vnp_payment.dto.VnpayTransactionDto;
import uit.javabackend.webclonethecoffeehouse.payment.vnp_payment.paymentConfig.PaymentConfig;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/v1/payment")
public class VnpPaymentApi {

    private final OrderService orderService;
    Logger logger = LoggerFactory.getLogger(VnpPaymentApi.class);

    public VnpPaymentApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("create-payment")
    public Object createPayment(@RequestBody @Valid VnpPaymentCreateDTO vnpPaymentDTO, HttpServletRequest request) throws IOException {

        String vnp_OrderInfo = vnpPaymentDTO.getDescription();
       // String vnp_TxnRef = PaymentConfig.getRandomNumber(8); // o day chinh là orderId phải fix lại
        String vnp_TxnRef = String.valueOf(vnpPaymentDTO.getOrderId()); // o day chinh là orderId phải fix lại

        String bank_code = vnpPaymentDTO.getBankcode();

        int amount = vnpPaymentDTO.getAmount() * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", PaymentConfig.vnp_CurrCode);
        vnp_Params.put("vnp_BankCode", bank_code);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", PaymentConfig.vnp_OrderType);
        vnp_Params.put("vnp_Locale", PaymentConfig.vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());

        //Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // save vnp_params to db

        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();

        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

        ResponseForVnpayDTO rspDto = new ResponseForVnpayDTO();
        rspDto.setCode("00");
        rspDto.setMessage("success");
        rspDto.setData(paymentUrl);

        return ResponseUtil.get(rspDto,HttpStatus.OK);
    }

    // demo return url
    @GetMapping("billing-infomation")
    public Object billingInfomation(@RequestParam("vnp_ResponseCode") String code
            ,HttpServletRequest request){
        logger.info("contextpath:  " + request.getRemoteAddr() );
        ResponseForVnpayDTO rspDto = new ResponseForVnpayDTO();
        rspDto.setCode(code);
        //rspDto.setMessage("success");
        return new ResponseEntity<>(rspDto,HttpStatus.OK);
    }

    @Operation(summary = "post data response từ vnpay cap nhat giao dich cua don hang")
    @PostMapping ("payment-order") // fix cho nay lai
    public Object updateTransactionOfOrder( @RequestBody VnpayTransactionDto vnpayTransactionDto) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map fields = objectMapper.convertValue(vnpayTransactionDto,Map.class);

        String vnp_SecureHash = vnpayTransactionDto.getVnp_SecureHash();
        if (fields.containsKey("vnp_SecureHashType"))
        {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash"))
        {
            fields.remove("vnp_SecureHash");
        }
//        // Check checksum
        String signValue = PaymentConfig.hashAllFields(fields);
        if (signValue.equals(vnpayTransactionDto.getVnp_SecureHash()))
        {
            // 1 update status for order
            OrderDTO order = orderService.findOrderByOrderId(UUID.fromString(vnpayTransactionDto.getVnp_TxnRef()));

            // 1.1 check orderid
            if(order != null){

                //1.2 check amount la chinh xac
                if(order.getTotalPrice().equals(vnpayTransactionDto.getVnp_Amount())){

                    // 1.3 check status cua order hien tai la ordered
                    if(vnpayTransactionDto.getVnp_ResponseCode().equals("00")){

                        order.setStatus(OrderStatus.PROCESSED);
                        //bo sung them paymentStatus o day
                        orderService.update(order);

                    }
                }
            }

        }else {
            throw new GiraBusinessException("securehash khong chinh xac");
        }
        return ResponseUtil.get(fields,HttpStatus.OK);
    }

    @Operation(summary = "truy van thong tin don hang truy xuat truc tiep vnpay")
    @PostMapping ("order-payment-infomation") // fix cho nay lai
    public Object vnpayQuery(HttpServletRequest req, @RequestBody VnpayQueryDTO vnpayQueryDTO) throws IOException {
        logger.info(req.getRemoteAddr());
        //vnp_Command = querydr
        String vnp_TxnRef = vnpayQueryDTO.getOrderId();
        String vnp_TransDate = vnpayQueryDTO.getCreate_date();
        String vnp_TmnCode = PaymentConfig.PaymentQuery.vnp_TmnCode;
        String vnp_IpAddr = req.getRemoteAddr();

        Map<String, String> vnp_Params = new HashMap<>();
        //vnp_Params.put("vnp_RequestId", PaymentConfig.getRandomNumber32bi(5));
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", PaymentConfig.PaymentQuery.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);


        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_apiUrl + "?" + queryUrl;
        System.out.println( " paymentUrl: " + paymentUrl);

        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
        String[] responseData = respDecode.split("&|\\=");

        return ResponseUtil.get(Arrays.toString(responseData), HttpStatus.ACCEPTED);

    }
}
