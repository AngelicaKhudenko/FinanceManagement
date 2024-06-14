package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.config.properties.URLProperty;
import by.it_academy.jd2.user_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.user_service.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.user_service.core.dto.*;
import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.core.exceptions.FieldsIncorrectException;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.model.VerificationEntity;
import by.it_academy.jd2.user_service.repository.IVerificationRepository;
import by.it_academy.jd2.user_service.service.api.ICabinetService;
import by.it_academy.jd2.user_service.service.api.IMailService;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.controller.token.UserHolder;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements ICabinetService{
    private final IUserService userService;
    private final IMailService mailService;
    private final IVerificationRepository verificationRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final ConversionService conversionService;
    private final UserHolder userHolder;
    private final URLProperty urlProperty;

    public CabinetServiceImpl(IUserService userService,
                              IMailService mailService,
                              IVerificationRepository verificationRepository,
                              PasswordEncoder encoder,
                              JwtTokenHandler jwtHandler,
                              ConversionService conversionService,
                              UserHolder userHolder,
                              URLProperty urlProperty) {

        this.userService = userService;
        this.mailService = mailService;
        this.verificationRepository = verificationRepository;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
        this.urlProperty = urlProperty;
    }

    @Transactional
    @Override
    public UserEntity create(UserRegistrationCUDTO user) {

        UserCUDTO userCUDTO = this.conversionService.convert(user,UserCUDTO.class);

        userCUDTO.setRole(EUserRole.USER);
        userCUDTO.setStatus(EUserStatus.WAITING_ACTIVATION);

        UserEntity entity = this.userService.create(userCUDTO);

        String verificationCode = generateVerificationCode();

        VerificationEntity verificationEntity = new VerificationEntity();

        verificationEntity.setMail(user.getMail());
        verificationEntity.setCode(verificationCode);

        this.verificationRepository.saveAndFlush(verificationEntity);

        MailDTO mail = generateVerificationMail(user.getMail(),verificationCode, user.getFio());

        this.mailService.create(mail);

        return entity;
    }

    @Transactional
    @Override
    public UserEntity verify(VerificationDTO verificationDTO) {

        Optional<VerificationEntity> optional = this.verificationRepository.findById(verificationDTO.getMail());

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("mail","Неверный адрес электронной почты");
        }

        VerificationEntity verification = optional.get();

        if (!verificationDTO.getCode().equals(verification.getCode())) {
            throw new FieldsIncorrectException("code","Неверный код авторизации");
        }

        Optional<UserEntity> optionalUser = this.userService.getByMail(verification.getMail());

        if (optionalUser.isEmpty()) {
            throw new FieldsIncorrectException("mail","Неверный адрес электронной почты");
        }

        UserEntity user = optionalUser.get();
        user.setStatus(EUserStatus.ACTIVATED);

        return this.userService.update(user);
    }

    @Override
    public String login(LoginDTO loginDTO) {

        String mail = loginDTO.getMail();
        String password = loginDTO.getPassword();

        Optional<UserEntity> optional = this.userService.getByMail(mail);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("mail","Пользователь с указанной почтой отсутствует");
        }

        UserEntity entity = optional.get();

        if (entity.getStatus() != EUserStatus.ACTIVATED) {
            throw new IllegalArgumentException("Пользователь не активирован");
        }

        if (!encoder.matches(password, entity.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        return jwtHandler.generateAccessToken(entity.getUuid(),entity.getRole());
    }

    @Override
    public UserEntity getInfoAboutMe() {

        UserDetailsExpanded details = this.userHolder.getUser();

        return this.userService.get(UUID.fromString(details.getUsername()));
    }

    private String generateVerificationCode() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString().replace("-","");
    }

    private MailDTO generateVerificationMail(String login, String verificationCode, String fio) {

        StringBuilder builder = new StringBuilder();

        builder.append("Здравствуйте, ");
        builder.append(fio);
        builder.append("! Мы рады, что вы стали пользователем нашего приложения! Для верификации вашего аккаунта перейдите по ссылке: ");
        builder.append("http://");
        builder.append(System.getenv("SERVER"));
        builder.append(this.urlProperty.getVerification());
        builder.append("?code=");
        builder.append(verificationCode);
        builder.append("&mail=");
        builder.append(login);

        String text = builder.toString();

        MailDTO mail = MailDTO
                .builder()
                .from(System.getenv("MAIL_LOGIN"))
                .to(login)
                .topic("Верификация")
                .text(text)
                .build();

        return mail;
    }
}
