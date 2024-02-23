package u5w3d5.u5w3d5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import u5w3d5.u5w3d5.dao.UserDAO;
import u5w3d5.u5w3d5.dto.NewUserDTO;
import u5w3d5.u5w3d5.dto.UserLoginDTO;
import u5w3d5.u5w3d5.entities.User;
import u5w3d5.u5w3d5.exception.BadRequestException;
import u5w3d5.u5w3d5.exception.UnauthorizedException;
import u5w3d5.u5w3d5.security.JWTTools;


@Service
public class AuthService {
    @Autowired
    private UserService usersService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UserDAO usersDAO;
    @Autowired
    private JWTTools jwtTools;

    public String GenerateToken(UserLoginDTO payload) {
        User user = usersService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

    public User saveUser(NewUserDTO payload) {
        usersDAO.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });

        User newUser = new User(payload.name(), payload.surname(),
                payload.email(), bcrypt.encode(payload.password()));
        return usersDAO.save(newUser);
    }
}
