package uit.javabackend.webclonethecoffeehouse.currency.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.currency.dto.CurrencyDTO;
import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;
import uit.javabackend.webclonethecoffeehouse.currency.service.CurrencyService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/CurrencyMangement")
public class CurrencyRestResource {

    private final CurrencyService currencyService;

    public CurrencyRestResource(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/GetAllCurrencies")
    public Object findAll(){return ResponseUtil.get(currencyService.findAllDto(CurrencyDTO.class), HttpStatus.OK);}


    @GetMapping("/{currency-id}/GetCurrencyWithProduct")
    public Object findProductWithProductDTO(@PathVariable ("currency-id") UUID currencyID){
        return ResponseUtil.get(currencyService.getCurrencyWithProductDTO(currencyID),HttpStatus.OK);
    }

    @GetMapping("/GetAllCurrenciesWithProduct")
    public Object findAllCurrencyWithProductDTO(){
        return ResponseUtil.get(currencyService.getAllCunrrencyWithProductDTO(),HttpStatus.OK);
    }

    @PostMapping("/AddCurrency")
    public Object save (@RequestBody @Valid CurrencyDTO currencyDTO){

        return ResponseUtil.get(currencyService.save(currencyDTO),HttpStatus.CREATED);
    }


    @PutMapping("/UpdateCurrency")
    public Object updateCurrency(@RequestBody Currency currency){
        return ResponseUtil.get(currencyService.update(currency),HttpStatus.OK);
    }

    @DeleteMapping("/DeleteByNameCurrency")
    public Object deleteCurrency(@RequestParam ("name") String name){
        currencyService.deleteByName(name);
        return HttpStatus.OK;
    }

    @PostMapping("/{currency-id}/AddProducts")
    public ResponseEntity<?> addProduct (@RequestBody List<UUID> ids,@PathVariable ("currency-id") UUID currencyId){
        return ResponseUtil.get(currencyService.addProduct(ids,currencyId),HttpStatus.CREATED);
    }

    @DeleteMapping ("/{currency-id}/RemoveProducts")
    public ResponseEntity<?> deleteProduct(@RequestBody List<UUID> ids,@PathVariable ("currency-id") UUID currencyId){
        return ResponseUtil.get(currencyService.removeProduct(ids,currencyId),HttpStatus.OK);
    }
}
