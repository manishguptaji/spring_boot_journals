package net.engineeringdigest.journalApp.repos;

import net.engineeringdigest.journalApp.entity.UserEntry;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UseRepoCustomImpl implements UserRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntry getUserDataUsingCriteria(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntry> query = cb.createQuery(UserEntry.class);
        Root<UserEntry> root = query.from(UserEntry.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.equal(root.get("userName"), name));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getSingleResult();
    }
}

