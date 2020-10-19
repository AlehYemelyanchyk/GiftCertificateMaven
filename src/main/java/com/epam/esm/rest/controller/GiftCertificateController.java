package com.epam.esm.rest.controller;

import com.epam.esm.entity.GiftCertificate;
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
        GiftCertificate returnObject = null;
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
        List<GiftCertificate> giftCertificates = null;

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
        GiftCertificate returnObject = null;
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

    @GetMapping("/certificates/findByName")
    public GiftCertificate findByName(@RequestParam("name") String name) {
        GiftCertificate returnObject = null;
        try {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findByName(name);
            returnObject = optionalGiftCertificate.orElseThrow(() ->
                    new ResourceNotFoundException("Certificate (name = " + name + ") not found."));
        } catch (ServiceException e) {
            LOGGER.error("findByName error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @GetMapping("/certificates/searchBy")
    public List<GiftCertificate> searchBy(@RequestParam("part") String part) {
        List<GiftCertificate> returnObject;
        try {
            returnObject = giftCertificateService.findByPartNameDescription(part);
        } catch (ServiceException e) {
            LOGGER.error("findByName error: " + e.getMessage());
            throw new RuntimeException();
        }
        if (returnObject.isEmpty()) {
            throw new ResourceNotFoundException("Certificate (name = " + part + ") not found.");
        }
        return returnObject;
    }

    @GetMapping("/certificates/findByTagName")
    public List<GiftCertificate> findByTagName(@RequestParam("name") String name) {
        List<GiftCertificate> returnObject;
        try {
            returnObject = giftCertificateService.findAllGiftCertificatesByTagName(name);
        } catch (ServiceException e) {
            LOGGER.error("findByTagName error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @PutMapping("/certificates")
    public GiftCertificate updateCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate returnObject;

        try {
            returnObject = giftCertificateService.update(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        if (returnObject == null) {
            throw new ResourceNotFoundException("Gift Certificate (Gift Certificate = " + giftCertificate + ") not found.");
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
