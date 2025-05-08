package org.hibernate.bugs.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.util.*;

@Entity(name = "Homepage")
@SoftDelete
public class HomepageEntity {

    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    @JoinColumn(name="promoted_offer_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private OfferEntity promotedOffer = null;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OfferEntity getPromotedOffer() {
        return promotedOffer;
    }

    public void setPromotedOffer(OfferEntity promotedOffer) {
        this.promotedOffer = promotedOffer;
    }

}
