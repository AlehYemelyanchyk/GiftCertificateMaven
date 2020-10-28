package com.epam.esm.rest.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.rest.exceptions.ResourceNotFoundException;
import com.epam.esm.services.TagService;
import com.epam.esm.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TagController {

    private static final Logger LOGGER = LogManager.getLogger();

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    public Tag saveTag(@RequestBody Tag tag) {
        Tag returnObject;
        try {
            Optional<Tag> optionalTag = tagService.save(tag);
            returnObject = optionalTag.orElseThrow(() -> new RuntimeException("Operation failed."));
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @GetMapping("/tags")
    public List<Tag> findAll() {
        List<Tag> tags;

        try {
            tags = tagService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return tags;
    }

    @GetMapping("/tags/{id}")
    public Tag findById(@PathVariable int id) {
        Tag returnObject;
        try {
            Optional<Tag> optionalTag = tagService.findById(id);
            returnObject = optionalTag.orElseThrow(() ->
                    new ResourceNotFoundException("Tag (id = " + id + ") not found."));
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @GetMapping("/tags/findByName")
    public Tag findByName(@RequestParam("name") String name) {
        Tag returnObject;
        try {
            Optional<Tag> optionalTag = tagService.findByName(name);
            returnObject = optionalTag.orElseThrow(() ->
                    new ResourceNotFoundException("Tag (name = " + name + ") not found."));
        } catch (ServiceException e) {
            LOGGER.error("findByName error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @DeleteMapping("/tags")
    public void delete(@RequestBody Tag tag) {
        try {
            tagService.delete(tag);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new ResourceNotFoundException("Tag (tag = " + tag.getName() + ") not found.");
        }
    }

    @DeleteMapping("/tags/{id}")
    public void deleteById(@PathVariable Integer id) {
        try {
            tagService.deleteById(id);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new ResourceNotFoundException("Tag (id = " + id + ") not found.");
        }
    }
}
