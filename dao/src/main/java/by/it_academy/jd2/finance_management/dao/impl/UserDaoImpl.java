package by.it_academy.jd2.finance_management.dao.impl;

import by.it_academy.jd2.finance_management.core.enums.PageSizeDTO;
import by.it_academy.jd2.finance_management.dao.api.IUserDao;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDaoImpl implements IUserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(UserEntity entity) {

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        query.from(UserEntity.class);

        this.em.persist(entity);
    }

    @Override
    public Optional<UserEntity> get(UUID uuid) {

        UserEntity entity = this.em.find(UserEntity.class, uuid);

        return Optional.ofNullable(entity);
    }

    @Override
    public List<UserEntity> get(PageSizeDTO pageSize) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        query.from(UserEntity.class);

        List<UserEntity> resultList = em.createQuery(query)
                .setFirstResult(pageSize.getPage() * pageSize.getSize())
                .setMaxResults((pageSize.getPage() + 1) * pageSize.getSize()-1)
                .getResultList();

        return resultList;
    }

    @Override
    public void update(UUID uuid, UserEntity entity) {

        UserEntity userEntity = this.em.find(UserEntity.class, uuid);

        if (userEntity == null) {
            throw new IllegalStateException("Пользователь с данным id не найден в БД");
        }

        if (userEntity.getVersion() == null) {
            throw new IllegalStateException("Версия обновления пользователя не должна принимать нулевое значение");
        }

        if (!userEntity.getVersion().equals(entity.getVersion())) {
            throw new OptimisticLockException("Версия пользователя была изменена другим пользователем");
        }

        LocalDateTime dateUpdate = entity.getUpdate();

        userEntity.setUpdate(dateUpdate);
        userEntity.setVersion(dateUpdate);

        userEntity.setMail(entity.getMail());
        userEntity.setFio(entity.getFio());
        userEntity.setRole(entity.getRole());
        userEntity.setStatus(entity.getStatus());
        userEntity.setPassword(entity.getPassword());

        this.em.merge(userEntity);
    }
}
