package uit.javabackend.webclonethecoffeehouse.vnp_payment.boundary;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.dto.*;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.paymentConfig.PaymentConfig;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.service.VnpayPaymentService;

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
public class VnpayPaymentRestResource {

    private final OrderService orderService;
    private final VnpayPaymentService vnpayPaymentService;
    @Autowired
    private TCHMapper mapper;
    Logger logger = LoggerFactory.getLogger(VnpayPaymentRestResource.class);

    public VnpayPaymentRestResource(OrderService orderService, VnpayPaymentService vnpayPaymentService) {
        this.orderService = orderService;
        this.vnpayPaymentService = vnpayPaymentService;
    }
    @TCHOperation(name = "PersonalPayment")
    @PostMapping("create-payment")
    public Object createPayment(@RequestBody @Valid VnpPaymentCreateDTO vnpPaymentCreateDTO, HttpServletRequest request) throws IOException {

        return ResponseUtil.get(vnpayPaymentService.createPayment(vnpPaymentCreateDTO,request),HttpStatus.OK);
    }

    // demo return url
    @TCHOperation(name = "PersonalPayment")
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
    @TCHOperation(name = "PersonalPayment")
    @PostMapping ("payment-order") // fix cho nay lai
    public Object updateTransactionOfOrder( @RequestBody VnpayTransactionDto vnpayTransactionDto) throws IOException {

        return ResponseUtil.get(vnpayPaymentService.updateTransactionOfOrder(vnpayTransactionDto),HttpStatus.OK);
    }

    @Operation(summary = "truy van thong tin don hang truy xuat truc tiep tu vnpay")
    @TCHOperation(name = "PersonalPayment")
    @PostMapping ("order-payment-infomation") // fix cho nay lai
    public Object vnpayQuery(HttpServletRequest req, @RequestBody VnpayQueryDTO vnpayQueryDTO) throws IOException {
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
    @TCHOperation(name = "PaymentManagement")
    @GetMapping("get-all")
    public Object getAllVnpayPayment(){
        return ResponseUtil.get(vnpayPaymentService.findAllDto(VnpayPaymentDTO.class),HttpStatus.OK);
    }
    @TCHOperation(name = "PersonalPayment")
    @GetMapping("{vnpaypayment-id}")
    public Object getVnpayPaymentById(@PathVariable("vnpaypayment-id") UUID vnpayPaymentId){
        return ResponseUtil.get(vnpayPaymentService.findVnpayPaymentById(vnpayPaymentId),HttpStatus.OK);
    }



}
