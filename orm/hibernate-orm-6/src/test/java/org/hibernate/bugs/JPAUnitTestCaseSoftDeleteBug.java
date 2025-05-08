package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.bugs.entities.HomepageEntity;
import org.hibernate.bugs.entities.OfferEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class JPAUnitTestCaseSoftDeleteBug {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	private UUID idWithRelation;
	private UUID idWithNullRelation;

	@Test
	void testSoftDelete() throws Exception {
		initData();

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		var offer = entityManager.find(OfferEntity.class, idWithRelation);
		System.out.println("Working homepage: " + offer.getHomepage());

		/**
		 * org.hibernate.FetchNotFoundException: Entity `org.hibernate.bugs.entities.HomepageEntity` with identifier value `e1facc91-e746-4886-a99c-c9733ed6fbf3` does not exist
		 */
		var offerWithNullRelation = entityManager.find(OfferEntity.class, idWithNullRelation);
		System.out.println("Exception homepage: " + offerWithNullRelation.getHomepage());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private void initData(){
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// prepare data
		entityManager.getTransaction().begin();

		var homepage = new HomepageEntity();
		entityManager.persist(homepage);

		var offer1 = new OfferEntity();
		entityManager.persist(offer1);

		var offer2 = new OfferEntity();
		entityManager.persist(offer2);

		offer1.setHomepage(homepage);
		homepage.setPromotedOffer(offer1);

		idWithRelation = offer1.getId();
		idWithNullRelation = offer2.getId();

		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
