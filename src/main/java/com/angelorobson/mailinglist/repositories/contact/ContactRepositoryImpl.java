package com.angelorobson.mailinglist.repositories.contact;

import com.angelorobson.mailinglist.entities.*;
import com.angelorobson.mailinglist.repositories.filter.ContactFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

public class ContactRepositoryImpl implements ContactRepositoryQuery {

    @Autowired
    private EntityManager manager;

    @Override
    public Page<Contact> findAllByFilter(ContactFilter contactFilter, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);
        Root<Contact> root = criteria.from(Contact.class);

        Predicate[] predicates = createConstraints(contactFilter, builder, root);
        criteria.where(predicates).distinct(true);

        TypedQuery<Contact> query = manager.createQuery(criteria);

        addPagingRestrictions(query, page);

        return new PageImpl<>(query.getResultList(), page, total(contactFilter));
    }

    private Predicate[] createConstraints(ContactFilter contactFilter, CriteriaBuilder builder, Root<Contact> root) {
        List<Predicate> predicates = new ArrayList<>();

        Join<Contact, Category> categoryJoin = root.join(Contact_.category);

        if (contactFilter.getCategory() != null) {
            if (!isEmpty(contactFilter.getCategory().getCategory())) {
                predicates.add(builder.equal(categoryJoin.get(Category_.category), contactFilter.getCategory().getCategory()));
            }
        }

        if (contactFilter.getFunctions() != null) {
            if (!contactFilter.getFunctions().isEmpty()) {
                predicates.add(root.join(Contact_.functions).in(contactFilter.getFunctions()));
            }
        }

        if (!isEmpty(contactFilter.getUserNameInstagram())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Contact_.userNameInstagram)), "%" + contactFilter.getUserNameInstagram().toLowerCase() + "%"));
        }

        if (contactFilter.getGender() != null) {
            if (!isEmpty(contactFilter.getGender())) {
                predicates.add(
                        builder.equal(root.get(Contact_.gender), contactFilter.getGender()));
            }
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void addPagingRestrictions(TypedQuery<?> query, Pageable pageRequest) {
        int currentPage = pageRequest.getPageNumber();
        int totalRecordsPerPage = pageRequest.getPageSize();
        int firstPageRegistration = currentPage * totalRecordsPerPage;

        query.setFirstResult(firstPageRegistration);
        query.setMaxResults(totalRecordsPerPage);
    }

    private Long total(ContactFilter produtoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Contact> root = criteria.from(Contact.class);

        Predicate [] predicates = createConstraints(produtoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
