package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.user_service.core.dto.*;
import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.model.VerificationEntity;
import by.it_academy.jd2.user_service.repository.IVerificationRepository;
import by.it_academy.jd2.user_service.service.api.ICabinetService;
import by.it_academy.jd2.user_service.service.api.IMailService;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.token.UserDetailsExpanded;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements ICabinetService{
    private final Converter<UserRegistrationCUDTO, UserCUDTO> registrationConverter;
    private final IUserService userService;
    private final IMailService mailService;
    private final IVerificationRepository verificationRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final static String urlVerification = "../verify";

    public CabinetServiceImpl(Converter<UserRegistrationCUDTO, UserCUDTO> registrationConverter,
                              IUserService userService, IMailService mailService,
                              IVerificationRepository verificationRepository,
                              PasswordEncoder encoder,
                              JwtTokenHandler jwtHandler) {

        this.registrationConverter = registrationConverter;
        this.userService = userService;
        this.mailService = mailService;
        this.verificationRepository = verificationRepository;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
    }

    @Transactional
    @Override
    public void create(UserRegistrationCUDTO user) {

        if (!user.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о пользователе");
        }

        UserCUDTO userCUDTO = this.registrationConverter.convert(user);

        userCUDTO.setRole(EUserRole.USER);
        userCUDTO.setStatus(EUserStatus.WAITING_ACTIVATION);

        this.userService.create(userCUDTO);

        String verificationCode = generateVerificationCode();

        VerificationEntity verificationEntity = new VerificationEntity();

        verificationEntity.setMail(user.getMail());
        verificationEntity.setCode(verificationCode);

        this.verificationRepository.saveAndFlush(verificationEntity);

        MailDTO mail = generateVerificationMail(user.getMail(),verificationCode);

        this.mailService.create(mail);
    }

    @Transactional
    @Override
    public void verify(VerificationDTO verificationDTO) {

        if (!verificationDTO.fieldsChanged()) {
            throw new IllegalArgumentException("Не переданы данные для верификации");
        }

        Optional<VerificationEntity> optional = this.verificationRepository.findById(verificationDTO.getMail());

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Неверный код авторизации");
        }

        VerificationEntity verification = optional.get();

        if (!verificationDTO.getCode().equals(verification.getCode())) {
            throw new IllegalArgumentException("Неверный код авторизации");
        }

        Optional<UserEntity> optionalUser = this.userService.getByMail(verification.getMail());

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Ошибка при поиске пользователя. Пользователь с таким id отсутствует");
        }

        UserEntity user = optionalUser.get();
        user.setStatus(EUserStatus.ACTIVATED);

        this.userService.update(user);
    }

    @Override
    public String login(LoginDTO loginDTO) {

        if (!loginDTO.fieldsChanged()) {
            throw new IllegalArgumentException("Не переданы параметры для входа");
        }

        String mail = loginDTO.getMail();
        String password = loginDTO.getPassword();

        Optional<UserEntity> optional = this.userService.getByMail(mail);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с указанной почтой отсутствует");
        }

        UserEntity entity = optional.get();

        if (entity.getStatus() != EUserStatus.ACTIVATED) {
            throw new IllegalArgumentException("Пользователь не активирован");
        }

        if (!encoder.matches(password, entity.getPassword())) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        return jwtHandler.generateAccessToken(entity);
    }

    @Override
    public UserEntity getInfoAboutMe() {

        UserDetailsExpanded details = this.userService.getDetails();

        return this.userService.get(details.getUUID());
    }

    private String generateVerificationCode() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString().replace("-","");
    }

    private MailDTO generateVerificationMail(String login, String verificationCode) {

        StringBuilder builder = new StringBuilder();

        builder.append("Здравствуйте, ");
        builder.append(login);
        builder.append(" ");
        builder.append("Мы рады, что вы стали пользователем нашего приложения!");
        builder.append(" ");
        builder.append("Для верификации вашего аккаунта перейдите по ссылке:");
        builder.append(" ");
        builder.append(urlVerification);
        builder.append("Ваш код верификации: ");
        builder.append(verificationCode);

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