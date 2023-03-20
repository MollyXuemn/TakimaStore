package io.takima.master3.store.core.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoRepositoryBean
public class CustomJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID> {
    private final JpaEntityInformation<T, ?> entityInformation;

    public CustomJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
    }

    /**
     * Override parent method to apply JOIN-FETCHES according to hibernate's metamodel
     */
    @Override
    protected <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {
        return super.getQuery(new EagerFetchSpec<>(spec), domainClass, sort);
    }

    /**
     *
     * @param query must not be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @param spec can be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return
     */
    @Override
    protected <S extends T> Page<S> readPage(TypedQuery<S> query, final Class<S> domainClass, Pageable pageable,
                                             @Nullable Specification<S> spec) {

        // TODO Step 5.2
        return super.readPage(query, domainClass, pageable, spec);
    }

    class EagerFetchSpec<T, ID> implements Specification<T> {
        private final Specification<T> spec;

        public EagerFetchSpec(Specification<T> spec) {
            super();
            this.spec = spec;
        }

        private void applyFetchMode(Root<T> root) {
            Metadata metadata = MetadataExtractor.INSTANCE.getMetadata();
            Iterator<Property> propertyIterator = metadata.getEntityBinding(getDomainClass().getName()).getPropertyIterator();
            Stream<Property> stream = StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(propertyIterator, Spliterator.ORDERED),
                            false)
                    .filter(p -> p.getType().isEntityType())
                    .filter(p -> !p.isLazy());

            // TODO join fetch entities that should be eagerly loaded
            stream.forEach(p -> {
                root.fetch(p.getName(), JoinType.LEFT);
            });

        }

        private Class getDomainClass() {
            return entityInformation.getJavaType();
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            applyFetchMode(root);
            return spec.toPredicate(root, query, criteriaBuilder);
        }
    }
}
