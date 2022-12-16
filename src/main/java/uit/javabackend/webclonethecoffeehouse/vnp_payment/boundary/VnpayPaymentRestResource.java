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
import uit.javabackend.webclonethecoffeehouse.vnp_payment.paymentConfig.VnpayConstant;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.service.VnpayPaymentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/v1/payment")
public class VnpayPaymentRestResource {

    private final OrderService orderService;
    private final VnpayPaymentService vnpayPaymentService;
    Logger logger = LoggerFactory.getLogger(VnpayPaymentRestResource.class);
    @Autowired
    private TCHMapper mapper;

    public VnpayPaymentRestResource(OrderService orderService, VnpayPaymentService vnpayPaymentService) {
        this.orderService = orderService;
        this.vnpayPaymentService = vnpayPaymentService;
    }

    @TCHOperation(name = "CreatePayment")
    @PostMapping("create-payment")
    public Object createPayment(@RequestBody @Valid VnpPaymentCreateDTO vnpPaymentCreateDTO, HttpServletRequest request) throws IOException {

        return ResponseUtil.get(vnpayPaymentService.createPayment(vnpPaymentCreateDTO, request), HttpStatus.OK);
    }

    @Operation(summary = " demo return url")
    @GetMapping("billing-infomation")
    public Object billingInfomation(@RequestParam("vnp_ResponseCode") String code
            , HttpServletRequest request) {
        logger.info("contextpath:  " + request.getRemoteAddr());
        ResponseForVnpayDTO rspDto = new ResponseForVnpayDTO();
        rspDto.setCode(code);
        //rspDto.setMessage("success");
        return new ResponseEntity<>(rspDto, HttpStatus.OK);
    }

    @Operation(summary = "post data response từ vnpay cap nhat giao dich cua don hang")
    @TCHOperation(name = "UpdateTransactionOfOrder")
    @PostMapping("payment-order") // fix cho nay lai
    public Object updateTransactionOfOrder(@RequestBody VnpayTransactionDto vnpayTransactionDto) throws IOException {

        return ResponseUtil.get(vnpayPaymentService.updateTransactionOfOrder(vnpayTransactionDto), HttpStatus.OK);
    }

    @Operation(summary = "truy van thong tin don hang truy xuat truc tiep tu vnpay")
    @TCHOperation(name = "QueryPaymentInformation")
    @PostMapping("order-payment-infomation") // fix cho nay lai
    public Object vnpayQuery(HttpServletRequest req, @RequestBody VnpayQueryDTO vnpayQueryDTO) throws IOException {


        return ResponseUtil.get(vnpayPaymentService.vnpayQuery(req,vnpayQueryDTO), HttpStatus.ACCEPTED);

    }

    @TCHOperation(name = "GetAllVnpayPayment")
    @GetMapping("get-all")
    public Object getAllVnpayPayment() {
        return ResponseUtil.get(vnpayPaymentService.findAllDto(VnpayPaymentDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "GetVnpayPaymentById")
    @GetMapping("{vnpaypayment-id}")
    public Object getVnpayPaymentById(@PathVariable("vnpaypayment-id") UUID vnpayPaymentId) {
        return ResponseUtil.get(vnpayPaymentService.findVnpayPaymentById(vnpayPaymentId), HttpStatus.OK);
    }


}
