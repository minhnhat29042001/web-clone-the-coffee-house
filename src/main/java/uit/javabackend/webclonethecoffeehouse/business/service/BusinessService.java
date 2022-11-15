package uit.javabackend.webclonethecoffeehouse.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.business.dto.BusinessDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Business;
import uit.javabackend.webclonethecoffeehouse.business.repository.BusinessRepository;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;

import javax.validation.ValidationException;
import java.util.UUID;

public interface BusinessService extends GenericService<Business, BusinessDTO, UUID> {

    BusinessDTO update(BusinessDTO businessDTO);
}

@Service
@Transactional
class BusinessServiceImp implements BusinessService {
    private final BusinessRepository repository;
    private final TCHMapper mapper;
    private final ValidationException businessIsNotExisted = new ValidationException("Business is not existed.");


    BusinessServiceImp(BusinessRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Business, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public BusinessDTO update(BusinessDTO businessDTO) {
        Business curBusiness = repository.findByCompanyName(businessDTO.getCompanyName())
                .orElseThrow(() -> businessIsNotExisted);

        curBusiness.setCompanyName(businessDTO.getCompanyName());
        curBusiness.setRepresentativeName(businessDTO.getRepresentativeName());
        curBusiness.setBrand(businessDTO.getBrand());
        curBusiness.setOrderHotline(businessDTO.getOrderHotline());
        curBusiness.setRecruitmentHotline(businessDTO.getRecruitmentHotline());
        curBusiness.setEmail(businessDTO.getEmail());
        curBusiness.setAddress(businessDTO.getAddress());
        curBusiness.setTaxCode(businessDTO.getTaxCode());
        curBusiness.setSocialNetworkUrl(businessDTO.getSocialNetworkUrl());
        curBusiness.setWebsiteUrl(businessDTO.getWebsiteUrl());
        curBusiness.setImageUrl(businessDTO.getImageUrl());

        return save(curBusiness, Business.class, BusinessDTO.class);
    }
}