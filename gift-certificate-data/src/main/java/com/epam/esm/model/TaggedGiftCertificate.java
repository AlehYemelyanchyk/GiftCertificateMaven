package com.epam.esm.model;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class TaggedGiftCertificate extends GiftCertificate {
    private static final Long serialVersionUID = 1724820758632935338L;

    private Set<Tag> tags = new HashSet<>();

    public TaggedGiftCertificate() {
    }

    public TaggedGiftCertificate(String name, String description, Double price, LocalDateTime createDate, LocalDateTime lastUpdateDate, Integer duration) {
        super(name, description, price, createDate, lastUpdateDate, duration);
    }

    public TaggedGiftCertificate(Long id, String name, String description, Double price, LocalDateTime createDate, LocalDateTime lastUpdateDate, Integer duration) {
        super(id, name, description, price, createDate, lastUpdateDate, duration);
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}