package uit.javabackend.webclonethecoffeehouse.vnp_payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderStatus;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderService;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.dto.*;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.model.VnpayPayment;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.paymentConfig.VnpayConstant;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.paymentConfig.VnpayConfig;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.repository.VnpayPaymentRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public interface VnpayPaymentService extends GenericService<VnpayPayment, VnpayPaymentDTO, UUID> {
    VnpayPaymentDTO update(VnpayPaymentDTO vnpayPaymentDTO);

    //void deleteByName(String name);

    VnpayPaymentDTO findVnpayPaymentById(UUID vnpayPaymentId);

    VnpayPaymentDTO save(VnpayPaymentDTO vnpayPaymentDTO);

    Object createPayment(VnpPaymentCreateDTO vnpPaymentCreateDTO, HttpServletRequest servletRequest) throws UnsupportedEncodingException;

    Object updateTransactionOfOrder(VnpayTransactionDto vnpayTransactionDto);
    Object vnpayQuery(HttpServletRequest req, VnpayQueryDTO vnpayQueryDTO) throws IOException;
}

@Service
@PropertySource("classpath:validation/ValidationMessages.properties")
class VnpayPaymentServiceImp implements VnpayPaymentService {

    private final VnpayPaymentRepository repository;
    private final OrderService orderService;
    private final TCHMapper mapper;
    @Value("${vnpaypayment.id.existed}")
    private TCHBusinessException vnpayPaypentIsNotExisted;
    @Autowired
    private VnpayConfig vnpayConfig;

    VnpayPaymentServiceImp(VnpayPaymentRepository repository, OrderService orderService, TCHMapper mapper) {
        this.repository = repository;
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<VnpayPayment, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    @Override
    public VnpayPaymentDTO update(VnpayPaymentDTO vnpayPaymentDTO) {
        return null;
    }

    @Override
    public VnpayPaymentDTO findVnpayPaymentById(UUID vnpayPaymentId) {
        VnpayPayment curVnpayPayment = repository.findById(vnpayPaymentId).orElseThrow(() -> vnpayPaypentIsNotExisted);
        return mapper.map(curVnpayPayment, VnpayPaymentDTO.class);
    }

    @Override
    public VnpayPaymentDTO save(VnpayPaymentDTO vnpayPaymentDTO) {

        VnpayPayment vnpayPayment = getMapper().map(vnpayPaymentDTO, VnpayPayment.class);
        VnpayPayment savedVnpayPayment = repository.save(vnpayPayment);
        return getMapper().map(savedVnpayPayment, VnpayPaymentDTO.class);
    }

    @Override
    public Object createPayment(VnpPaymentCreateDTO vnpPaymentCreateDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        String vnp_OrderInfo = vnpPaymentCreateDTO.getDescription();
        String vnp_TxnRef = String.valueOf(vnpPaymentCreateDTO.getOrderId());
        String bank_code = vnpPaymentCreateDTO.getBankcode();

        int amount = vnpPaymentCreateDTO.getAmount() * 100;

        // conver to map
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConstant.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConstant.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnpayConfig.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", VnpayConstant.vnp_CurrCode);
        vnp_Params.put("vnp_BankCode", bank_code);//
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);//
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);//
        vnp_Params.put("vnp_OrderType", VnpayConstant.vnp_OrderType);
        vnp_Params.put("vnp_Locale", VnpayConstant.vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());

        //Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        VnpayPaymentDTO vnpayPaymentDTO = new VnpayPaymentDTO();
        vnpayPaymentDTO.setVnp_Version(VnpayConstant.vnp_Version);
        vnpayPaymentDTO.setVnp_Command(VnpayConstant.vnp_Command);
        vnpayPaymentDTO.setVnp_TmnCode(vnpayConfig.getVnp_TmnCode());
        vnpayPaymentDTO.setVnp_Amount(String.valueOf(amount));
        vnpayPaymentDTO.setVnp_CurrCode(VnpayConstant.vnp_CurrCode);
        vnpayPaymentDTO.setVnp_BankCode(bank_code);
        vnpayPaymentDTO.setVnp_TxnRef(vnp_TxnRef);
        vnpayPaymentDTO.setVnp_OrderInfo(vnp_OrderInfo);
        vnpayPaymentDTO.setVnp_OrderType(VnpayConstant.vnp_OrderType);
        vnpayPaymentDTO.setVnp_Locale(VnpayConstant.vnp_Locale);
        vnpayPaymentDTO.setVnp_CreateDate(vnp_CreateDate);
        vnpayPaymentDTO.setVnp_ExpireDate(vnp_ExpireDate);
        vnpayPaymentDTO.setVnp_IpAddr(request.getRemoteAddr());

        VnpayPayment vnpayPayment = getMapper().map(vnpayPaymentDTO, VnpayPayment.class);
        VnpayPayment savedVnpayPayment = repository.save(vnpayPayment);

        Order order = orderService.findById(vnpPaymentCreateDTO.getOrderId()).orElseThrow(() -> new TCHBusinessException("order is not existed"));
        order.setVnpayPayment(savedVnpayPayment);
        orderService.save(order);

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

        String vnp_SecureHash = VnpayConstant.hmacSHA512(vnpayConfig.getVnp_HashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;

        ResponseForVnpayDTO rspDto = new ResponseForVnpayDTO();
        rspDto.setCode("00");
        rspDto.setMessage("success");
        rspDto.setData(paymentUrl);
        return rspDto;
    }

    @Override
    public Object updateTransactionOfOrder(VnpayTransactionDto vnpayTransactionDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        VnpayPayment savedVnpayPayment = new VnpayPayment();
        Map fields = objectMapper.convertValue(vnpayTransactionDto, Map.class);

        String vnp_SecureHash = vnpayTransactionDto.getVnp_SecureHash();
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
//        // Check checksum
        String signValue = VnpayConstant.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            // 1 update status for order

            Order order = orderService.findById(UUID.fromString(vnpayTransactionDto.getVnp_TxnRef()))
                    .orElseThrow(() -> new TCHBusinessException("order is not existed"));

            // 1.1 check orderid
            if (order != null) {


                int realAmount = Integer.valueOf(vnpayTransactionDto.getVnp_Amount()) / 100;
                //1.2 check amount la chinh xac
                if (order.getTotalPrice().compareTo(realAmount) == 0) {


                    // get vnpayPament by order id nay
                    VnpayPayment vnpayPayment = order.getVnpayPayment();
                    vnpayPayment.setVnp_BankTranNo(vnpayTransactionDto.getVnp_BankTranNo());
                    vnpayPayment.setVnp_CardType(vnpayTransactionDto.getVnp_CardType());
                    vnpayPayment.setVnp_ResponseCode(vnpayTransactionDto.getVnp_ResponseCode());
                    vnpayPayment.setVnp_TransactionNo(vnpayTransactionDto.getVnp_TransactionNo());
                    vnpayPayment.setVnp_PayDate(vnpayTransactionDto.getVnp_PayDate());
                    vnpayPayment.setVnp_TransactionStatus(vnpayTransactionDto.getVnp_TransactionStatus());
                    savedVnpayPayment = repository.save(vnpayPayment);


                    // 1.3 check response code cua order
                    if (vnpayTransactionDto.getVnp_ResponseCode().equals("00") && order.getStatus() == OrderStatus.ORDERED) {

                        // update transaction status to db
                        order.setStatus(OrderStatus.PROCESSED);
                    } else {
                        order.setStatus(OrderStatus.CANCELED);

                    }
                    orderService.update(order);
                } else {
                    throw new TCHBusinessException("so tien khong chinh xac");
                }
            }

        } else {
            throw new TCHBusinessException("securehash khong chinh xac");
        }
        return getMapper().map(savedVnpayPayment, VnpayPaymentDTO.class);
    }

    @Override
    public Object vnpayQuery(HttpServletRequest req, VnpayQueryDTO vnpayQueryDTO) throws IOException {
        //vnp_Command = querydr
        String vnp_TxnRef = vnpayQueryDTO.getOrderId();
        String vnp_TransDate = vnpayQueryDTO.getCreate_date();
        String vnp_TmnCode = VnpayConstant.PaymentQuery.vnp_TmnCode;
        String vnp_IpAddr = req.getRemoteAddr();

        Map<String, String> vnp_Params = new HashMap<>();
        //vnp_Params.put("vnp_RequestId", PaymentConfig.getRandomNumber32bi(5));
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", VnpayConstant.PaymentQuery.vnp_Command);
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
        String vnp_SecureHash = VnpayConstant.hmacSHA512(vnpayConfig.getVnp_HashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.getVnp_apiUrl() + "?" + queryUrl;
        System.out.println(" paymentUrl: " + paymentUrl);

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
        return Arrays.toString(responseData);
    }

    @Override
    public VnpayPayment save(VnpayPaymentDTO dto, Class<VnpayPayment> modelClass) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return VnpayPaymentService.super.save(dto, modelClass);
    }

}
