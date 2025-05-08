package org.hibernate.bugs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;

import java.util.UUID;

@Entity(name = "Offer")
@SoftDelete
public class OfferEntity {

    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne(mappedBy = "promotedOffer")
    @NotFound(action = NotFoundAction.IGNORE)
    private HomepageEntity homepage;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public HomepageEntity getHomepage() {
        return homepage;
    }

    public void setHomepage(HomepageEntity homepage) {
        this.homepage = homepage;
    }
}