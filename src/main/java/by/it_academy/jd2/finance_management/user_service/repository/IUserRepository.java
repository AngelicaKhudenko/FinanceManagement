package by.it_academy.jd2.finance_management.user_service.repository;

import by.it_academy.jd2.finance_management.user_service.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity,UUID> {
}
