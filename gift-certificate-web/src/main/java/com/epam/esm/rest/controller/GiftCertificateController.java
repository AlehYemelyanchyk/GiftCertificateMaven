package com.epam.esm.rest.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.rest.exceptions.ResourceNotFoundException;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private GiftCertificateService giftCertificateService;

    @PostMapping("/certificates")
    public GiftCertificate savePortfolio(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate returnObject;
        try {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.save(giftCertificate);
            returnObject = optionalGiftCertificate.orElseThrow(() -> new RuntimeException("Operation failed."));
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @GetMapping("/certificates")
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates;

        try {
            giftCertificates = giftCertificateService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return giftCertificates;
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        GiftCertificate returnObject;
        try {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findById(id);
            returnObject = optionalGiftCertificate.orElseThrow(() ->
                    new ResourceNotFoundException("Tag (id = " + id + ") not found."));
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @GetMapping("/certificates/findBy")
    public List<TaggedGiftCertificate> findBy(@RequestParam Optional<String> tagName,
                                                @RequestParam Optional<String> name,
                                                @RequestParam Optional<String> description,
                                                @RequestParam Optional<String> sortBy,
                                                @RequestParam Optional<String> sortOrder) {
        List<TaggedGiftCertificate> returnObject;

        SearchParametersHolder searchParametersHolder = new SearchParametersHolder();
        searchParametersHolder.setTagName(tagName.orElse(null));
        searchParametersHolder.setName(name.orElse(null));
        searchParametersHolder.setDescription(description.orElse(null));
        searchParametersHolder.setSortBy(sortBy.orElse(null));
        searchParametersHolder.setSortOrder(sortOrder.orElse(null));

        try {
            returnObject = giftCertificateService.findBy(searchParametersHolder);
        } catch (ServiceException e) {
            LOGGER.error("searchBy error: " + e.getMessage());
            throw new RuntimeException();
        }
        if (returnObject.isEmpty()) {
            throw new ResourceNotFoundException("Certificates not found.");
        }
        return returnObject;
    }

    @PutMapping("/certificates")
    public GiftCertificate updateCertificate(@RequestParam Long id,
                                             @RequestParam Optional<String> name,
                                             @RequestParam Optional<String> description,
                                             @RequestParam Optional<Double> price,
                                             @RequestParam Optional<Integer> duration) {
        GiftCertificate returnObject;
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        giftCertificate.setName(name.orElse(null));
        giftCertificate.setDescription(description.orElse(null));
        giftCertificate.setPrice(price.orElse(null));
        giftCertificate.setDuration(duration.orElse(null));

        try {
            Optional<GiftCertificate> giftCertificateOptional =
                    giftCertificateService.update(giftCertificate);
            returnObject = giftCertificateOptional.orElseThrow(() ->
                    new ResourceNotFoundException("Gift Certificate (Gift Certificate = " + name + ") not found."));
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @DeleteMapping("/certificates/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            giftCertificateService.deleteById(id);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new ResourceNotFoundException("Gift Certificate (id = " + id + ") not found.");
        }
    }
}
