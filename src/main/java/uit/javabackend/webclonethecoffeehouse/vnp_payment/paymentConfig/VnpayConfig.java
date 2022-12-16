package uit.javabackend.webclonethecoffeehouse.vnp_payment.paymentConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class VnpayConfig {

    @Value("${payment.vnpay.params.vnp-pay-url}")
    private String vnp_PayUrl ;

    @Value("${payment.vnpay.params.vnp-return-url}")
    private String vnp_ReturnUrl ;//domain return

    @Value("${payment.vnpay.params.vnp-tmn-code}")
    private String vnp_TmnCode ; //dang ki xong thay ma o day

    @Value("${payment.vnpay.params.vnp-hash-serect}")
    private String vnp_HashSecret ; //dang ki xong thay ma o day

    @Value("${payment.vnpay.params.vnp-api-url}")
    private String vnp_apiUrl ;

    public static String HASHSERECT_STATIC;

    @Value("${payment.vnpay.params.vnp-hash-serect}")
    public void setHashSerectStatic(String vnp_HashSecret) {
        VnpayConfig.HASHSERECT_STATIC = vnp_HashSecret;
    }
}
