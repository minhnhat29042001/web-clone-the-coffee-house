package uit.javabackend.webclonethecoffeehouse.currency.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.currency.dto.CurrencyDTO;
import uit.javabackend.webclonethecoffeehouse.currency.dto.CurrencyWithProductsDTO;
import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;
import uit.javabackend.webclonethecoffeehouse.currency.repository.CurrencyRepository;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.service.ProductService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface CurrencyService  extends GenericService<Currency, CurrencyDTO, UUID> {
    @Override
    List<Currency> findAll();


    Currency update (CurrencyDTO currency);
    CurrencyDTO save (CurrencyDTO currencyDTO);
    void deleteByName (String name);

    CurrencyWithProductsDTO addProduct(List<UUID> ids,UUID currencyId);
    CurrencyWithProductsDTO removeProduct(List<UUID> ids,UUID currencyId);
    CurrencyWithProductsDTO getCurrencyWithProductDTO (UUID currencyId);
    List<CurrencyWithProductsDTO> getAllCurrencyWithProductDTO();
}

@Service
@Transactional
class CurrencyServiceImpl implements  CurrencyService{
    private final CurrencyRepository repository;
    private final TCHMapper mapper;
    private final ProductService productService;
    private final ValidationException currencyIsNotExisted = new ValidationException("Product is not existed.");

    CurrencyServiceImpl(CurrencyRepository repository, TCHMapper mapper, ProductService productService) {
        this.repository = repository;
        this.mapper = mapper;
        this.productService = productService;
    }

    @Override
    public JpaRepository<Currency, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    @Transactional
    public List<Currency> findAll() {
        return repository.findAll();
    }

    @Override
    public Currency update(CurrencyDTO currency) {
        Currency curCurrency = repository.findByName(currency.getName())
                .orElseThrow(() -> new RuntimeException("Currency is not existed."));
        curCurrency.setName(currency.getName());
        return repository.save(curCurrency);
    }

    @Override
    public CurrencyDTO save(CurrencyDTO currencyDTO) {
        Currency currency =mapper.map(currencyDTO,Currency.class);
        Currency savedCurrency = repository.save(currency);
        return mapper.map(savedCurrency,CurrencyDTO.class);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public CurrencyWithProductsDTO addProduct(List<UUID> ids,UUID currencyId) {
        Currency currency = repository.findById(currencyId).orElseThrow( () ->
                currencyIsNotExisted
        );
        List<Product> products = productService.findByIds(ids);
        products.forEach(currency::addProduct);
        return mapper.map(currency,CurrencyWithProductsDTO.class);
    }

    @Override
    public CurrencyWithProductsDTO removeProduct(List<UUID> ids, UUID currencyId) {
        Currency currency = repository.findById(currencyId).orElseThrow(()->
                currencyIsNotExisted );
        List<Product> products = productService.findByIds(ids);
        products.forEach(currency::removeProduct);
        return mapper.map(currency,CurrencyWithProductsDTO.class);
    }

    @Override
    public CurrencyWithProductsDTO getCurrencyWithProductDTO(UUID currencyId) {
        Currency currency = repository.findById(currencyId).orElseThrow( () ->
                currencyIsNotExisted
        );
        return mapper.map(currency,CurrencyWithProductsDTO.class);
    }

    @Override
    public List<CurrencyWithProductsDTO> getAllCurrencyWithProductDTO() {
        List<Currency> currencyList = repository.findAll();
        List<CurrencyWithProductsDTO> currencyWithProductsDTOList = new ArrayList<>();
        currencyList.forEach(
                currency -> {
            CurrencyWithProductsDTO currencyWithProductsDTO = mapper.map(currency,CurrencyWithProductsDTO.class);
            currencyWithProductsDTOList.add(currencyWithProductsDTO);
        });
        return currencyWithProductsDTOList;
    }


}
