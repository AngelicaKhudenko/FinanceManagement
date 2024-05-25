package by.it_academy.jd2.finance_management.dao.api;

import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity,UUID> {
}
