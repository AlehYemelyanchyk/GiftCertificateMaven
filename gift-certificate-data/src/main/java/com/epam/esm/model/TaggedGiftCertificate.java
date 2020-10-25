package com.epam.esm.model;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TaggedGiftCertificate extends GiftCertificate {
    private static final Long serialVersionUID = 1724820758632935338L;

    private Set<Tag> tags = new HashSet<>();

    public TaggedGiftCertificate() {
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}