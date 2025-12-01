package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Country;
import com.matchup.api.matchup_api.models.Sport;
import com.matchup.api.matchup_api.models.User;
import com.matchup.api.matchup_api.repositories.CountryRepository;
import com.matchup.api.matchup_api.repositories.SportRepository;
import com.matchup.api.matchup_api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController

@RequestMapping(path="/api/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository _userRepository;
    private final CountryRepository _countryRepository;
    private final SportRepository _sportRepository;
    private final String DEFAULT_PROFILE_IMAGE_BASE64 = "/9j/4AAQSkZJRgABAQEASABIAAD/4QBiRXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAEaAAUAAAABAAAASgEbAAUAAAABAAAAUgEoAAMAAAABAAIAAAITAAMAAAABAAEAAAAAAAAAAABIAAAAAQAAAEgAAAAB/+0AXlBob3Rvc2hvcCAzLjAAOEJJTQQEAAAAAABBHAIAAAIAAhwCNwAIMjAyMzA1MjYcAjwACzIyNDUyMSswMDAwHAI+AAgyMDIzMDUyNhwCPwALMjI0NTIxKzAwMDAA/9sAQwAGBAUGBQQGBgUGBwcGCAoQCgoJCQoUDg8MEBcUGBgXFBYWGh0lHxobIxwWFiAsICMmJykqKRkfLTAtKDAlKCko/9sAQwEHBwcKCAoTCgoTKBoWGigoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgo/8IAEQgCWAJYAwEiAAIRAQMRAf/EABoAAQADAQEBAAAAAAAAAAAAAAADBAUCAQb/xAAUAQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIQAxAAAAH7IAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlImlbMebXGY0xmR64wovoozBaVIiAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA6l1ire9AAAAADz0UM76CIwk8AAAAAAAAAAAAAAAAAAAAAAAAAAAAs8bQ6AAAAAAAADnH2uT59NCAAAAAAAAAAAAAAAAAAAAAAAAAOudItygAAAAAAAAABFifQZ5mgAAAAAAAAAAAAAAAAAAAAAAAAk3KGiAAAAAAAAAAAPPRgx6OcAAAAAAAAAAAAAAAAAAAAAAADs2ZgAAAAAAAAAAAAhw/osA4AAAAAAAAAAAAAAAAAAAAAAAs1rhrAAAAAAAAAAAAAY2zklMAAAAAAAAAAAAAAAAAAAAAAC7SuGsAAAAAAAAAAAABlauSUwAAAAAAAAAAAAAAAAAAAAAALFfo+gAAAAAAAAAAAAAxdr585AAAAAAAAAAAAAAAAAAAAAAABtWMzTAAAAAAAAAAAAK+LfoAAAAAAAAAAAAAAAAAAAAAAAAHW98/oGkAAAAAAAAAABz1mlHkAAAAAAAAAAAAAAAAAAAAAAAAHvg258LZJAAAAAAAAACM4xZIgAAAAAAAAAAAAAAAAAAAAAAAAABNCN6TB1iwAAAAAAAViXH5jAAAAAAAAAAAAAAAAAAAAAAAAAAAAHvgv6GB0fQMq4WXnoAAeVyzxm1C7R8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHs0Ate1Ba4gHvgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD08ddEbrw8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdXCjLrTmXYuCGX0AOehDDcGXW3R8626RRdcgAAAAAAAAAAAAAAAAAAAAAAAAAAuFW/dkOegAAAAAAAA4o6I+f53s0pgAAAAAAAAAAAAAAAAAAAAAAAdy6xBbAAAAAAAAAAAACrlb8ZgrFcAAAAAAAAAAAAAAAAAAAAAXPdQegAAAAAAAAAAAAAB5la3h88u0gAAAAAAAAAAAAAAAAAABc42D0AAAAAAAAAAAAAAAADJ1vD55ZrAAAAAAAAAAAAAAAAACTjZJewAAAAAAAAAAAAAAAAAA4xN6sYz3wAAAAAAAAAAAAAAAEha1OegAAAAAAAAAAAAAAAAAAADLo/QYZGAAAAAAAAAAAAAABqZ+6egAAAAAAAAAAAAAAAAAAAAUb3J8+kjAAAAAAAAAAAAAB6aGjHIAAAAAAAAAAAAAAAAAAAAAAZ2du4Z4AAAAAAAAAAAABYr6JogAAAAAAAAAAAAAAAAAAAAAAYu1nGcAAAAAAAAAAAABs430B0AAAAAAAAAAAAAAAAAAAAAABWs8nz4AAAAAAAAAAAAJN4AAAAAAAAAAAAAAAAAAAAAAAAMGMAAAAAAP/xAAmEAACAQQCAQQDAQEAAAAAAAABAgMAEkBQBBEhExQwMyAjYHCg/9oACAEBAAEFAv8An+CMa9B69ua9sa9uaMD0UYfwSoWpePSoq/AyK1Nx6ZCu9AJMcFD5D5qSCiCDuY0LlECD53QMJI7Dt4o76A6GCR2JY7DtYkvIHQwyOxKlh2YHZjWxcWRbwR0dlxkyOSmyRbmHjIPkOtrbDirlcpdjEOo8mUdpr1HbZbjptdAP25c4/bruL9mXyvs13F+zL5X2a7jfZl8r7NdAepcuc9y65T02Wx7bXwnuPJmNsew4recnlN52APRU3DHY2gns7HjPkcl9mKie9cWV7FO0RrSrXDDdrQ7XHaxvYUYMMF2CiR7zt0YqY5A+BJIEp2LHdRz9UrBvkZgtST974Eil5BpZkNA9/kTTTKKacmiSf4MSOK9d69d69d6Mjn/XujVrV0f4RUZqHHNCBaEa/iaMa0YFpuOaZGXeAE0sBpYlX5miVqbjmiCNuASUgoALhEBqeCiCDs44SaVQuMyhhJAV2KqWMcQXKkiDUylTrY4y9IoUZbqGEkZTWQxXUPAzSO6lit1UMV2imit1EMV2kmit00Md50hqaOw6NFuZQFGlYXB1tbRQpYunmS9dDx086nkJ5z0W5gOhqSOw62tncZOhq+SnYzUW5gOhqz5Dra2ZxV13KXNjFqa2QXLlwi6TXzjqTK4o2HKGXAOo9fOO4soeBr28jJTy+xfw/wAn/8QAFBEBAAAAAAAAAAAAAAAAAAAAoP/aAAgBAwEBPwE0n//EABQRAQAAAAAAAAAAAAAAAAAAAKD/2gAIAQIBAT8BNJ//xAAnEAABAwMDBAIDAQAAAAAAAAABACFQETFAEiJhAjAygUFgIHBxoP/aAAgBAQAGPwL/AD/MCvhXC8lcL4Tg/QmC3FMOw4W0pxO0C397aqGa4TYDzPCbCdcS3CoMShlaBUxqKhk9RyNQkgMoiRPVlDqkRlGQAzCI8Zhj/WZ6j/WZ6j/WZ6jxmGPBzCZAZRkaZVJEFVGRUomS0nI0j7/VVGJUp5eownmWXOBynm9ybuOVsnmW51enYvVbWT/Q/Iq6+FdeR/b1l4lW+iME5onqV4/l4r5TOnE4y3K3estqeXZbk2E62J5TcybGdbXEiye+U108dwmzHXEZU2TZzqotFV6rQVem0RU2hKi0NxDcQlFQQ1CqQfMRzBajFahAAKgiqFEZ+qM1ZwEcRmnqjh1ZoEcRmCQOWTIA5YkDliQIyh/ZI/3u/wD/xAAqEAEAAAUEAQMEAgMAAAAAAAABABEhMVBAQVFhcTCBkSBgscFw8aCh8P/aAAgBAQABPyH/AB/v0qgS4PLBuwdD4h24gWF4Y/Sr7CbvdwXN0RYIfQsCwXF0w3a7zspE2AK1dQAJBI9QAk2gGtPUSwyc1RHkxIA99BIQ94SpU2cw6rZdgABI0QICZD0Kqzlll7LsEQSDSFKJjCydmzlGK4wMhphmvmGa4ZOQbljUTDcs5JCN4AALGoAg2hENsjcPBqqgeHIy71PVS/1kO5nWdCOPndFdZK7K4+5rFjHrmsWMedXesKjr76BD0Az1nYDPIT5uU1XnFMjIb3tqphO1ci5lyBGw6gVsEO5dyUlmeNRMfkyakzLwUzcvpjmbtoU2bfKOYgjsaQHgd3LFmltyNlGimJQs1tsZianAnHDQCc8Imp5oZMyEorOYAmxPUAmQQtFHcLNm51GaR6gtBDkTuAqET6gCrIjkzqKcDyhmaV7+whSykWT3Kwdj2j/sQ9D2i6exSFW6v8u9z4j+ojufH2Jdh7j9SQOAGw+IALAfQBuDCbj4h9pPeA3nlF2DvOIyE3qH3S6ItUzy+tcpHkgtXPphGQk95eQCrG+/YgyRA0RkiJG+vZiQCjlKrSg2QlppKMVD9jIygaxXatUrtEJQNcc7xyiStZkozhnnljF2/wCcAkUGuAJUMPu/xxVMXhzBTANYnEuxxiJzbf7wk5/Tw1wsvASJFDCASTaKgXWwjkIE2DDGthh2WCvHlF8RZLLRbAztgWxUrYN8AhG8EVgxRtYYRjbXzBXcZIRcv41yEbwABbGAQbMIxtrbh4MdYPDrekDHdrGs60VyHTmurp+3kPx+r8jrkPAK6syXBLIDtCWqEjyMkJHher//2gAMAwEAAgADAAAAEPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPONDHHGMPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPNLPPPPPLPNPPPPPPPPPPPPPPPPPPPPPPPPPPPKPPPPPPPPPOPPPPPPPPPPPPPPPPPPPPPPPPPPLHPPPPPPPPPPCPPPPPPPPPPPPPPPPPPPPPPPPPFPPPPPPPPPPPLFPPPPPPPPPPPPPPPPPPPPPPPPHPPPPPPPPPPPPHPPPPPPPPPPPPPPPPPPPPPPPLPPPPPPPPPPPPPLPPPPPPPPPPPPPPPPPPPPPPPOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPNPPPPPPPPPPPPNPPPPPPPPPPPPPPPPPPPPPPPPNPPPPPPPPPPPPNPPPPPPPPPPPPPPPPPPPPPPPPKPPPPPPPPPPPKPPPPPPPPPPPPPPPPPPPPPPPPPPEPPPPPPPPPPFPPPPPPPPPPPPPPPPPPPPPPPPPPLOPPPPPPPPDPPPPPPPPPPPPPPPPPPPPPPPPPPPPLCONPPMKDPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPLHDPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPLPLPPPLPPPPPPPPPPPPPPPPPPPPPPPPPPPPPLHPPPPPPPPLPPPPPPPPPPPPPPPPPPPPPPPPPMPPPPPPPPPPPPPHPPPPPPPPPPPPPPPPPPPPPPLHPPPPPPPPPPPPPPHPPPPPPPPPPPPPPPPPPPPMHPPPPPPPPPPPPPPPPHPPPPPPPPPPPPPPPPPPMPPPPPPPPPPPPPPPPPPPHNPPPPPPPPPPPPPPPONPPPPPPPPPPPPPPPPPPPPOPPPPPPPPPPPPPPPIPPPPPPPPPPPPPPPPPPPPPLNPPPPPPPPPPPPPONPPPPPPPPPPPPPPPPPPPPPPOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPKPPPPPPPPPPPPPPPPPPPPPPPKPPPPPPPPPPPPPHPPPPPPPPPPPPPPPPPPPPPPPPHPPPPPPP/EABQRAQAAAAAAAAAAAAAAAAAAAKD/2gAIAQMBAT8QNJ//xAAUEQEAAAAAAAAAAAAAAAAAAACg/9oACAECAQE/EDSf/8QALBABAAECBAUEAgMAAwAAAAAAAREAITFBUFFAYXGBoZGxwfDR4SAwYHCg8f/aAAgBAQABPxD/AK/16Ub4PWi/onKaUYrlNWM2kHE8xr64nWKvQjfB6/4KKKM4W9aJdV2aUIZnF/X+iUmnOL+tIlRbPzUUUZQt666YXtlDF7tw70CEGAH9kARWIlS8TzjZ6Uy5HdaHFmZo4LC6z4BsLkMSu1i4OsTeQ3U6UaQMAy4I0iYjnUnl2R11YLcXNqh5CgDhFohQjSdS+kaoNktAUIxMV58MpsOJsaGyGhNTkCldkz+8uIiCs7pl956liBKF2KEuAgOIEOUQlElix3MtRJTXL3l+OKMzbvZnzqIE8w5t+KCNFuDmX1DbkzzxmzbnnT+lT4cZ0qPDTxKOS9zjBCGY93T/ADXucZ4r3dPi2kPJxkhmR7un3lmodx4y0skHsGntnJehoRJM+KUCXKmzlvU6hmUHhxUjLIhG79dRkcQZ6j9e3FSOIMur+vfUcZtkpjpCTiGOsy1jNsupElo3nvnxEY1i/XlqaEiBkSjFmF1cNgJhDnSMiplXVMjCyblPRdiTnwmDAYG9J1jYNjVpoS/RoCo5jZ4KGYPPapoQfR1gbcZjgnOihJHdvtwDUkyi+9LbjIMA5a0woyXEyrFHY4ldL9f2db5VNEjfi9KZUZbq566JOs1FNwDuWfxVseUMecKA5oD/ACkRbixVuOUM+cKlsyiJfxSl6zX+CdljcYrEB1fKhFx2K5lCiw7FYAOr4U7LG6z/AMuikinJV/6OkSUTmv8ACQic7IPVprIjYTXvYY9qD8mXvXjwI/h5cCaD8GHtQczeUvepRO2EVKJxsk9TXCL2ypdBO5KfFACQc1/umEE6lEoDuQ/imLyy1cSa8grD7692uXSjguTSkrF7y9mmRXkmqQ823m1BUIu78NFKkY5+tTU2xk1EbNX29YQb6YdOKKYo8Qx60tij7bTkLht3n0qD0GfPjLELlUtx8D86YwSZBUQQBAHHAUWCNMsuYfeGlIoTEKgBAAaAAQglPIjuGkGEYaQTH+KACDQkEhpkO5lIx6MkuKu36UZAAQBoisRRcaQ5JrjLromZldeVEXAW0a72FZGVx5aECgCVsBRXwx3pBLAxnSKRISyOg24ZbXN/WlW4ZbTJ/egZlbHYzo/IKDSiYkoSsytjuZcfykJ00zlBeb69+OxClC7G9BnAIDTBDkISsQpQO5vxuXN73+NOOw+1vnjAVAutAHE3OufmdOFSJgOuXmKRFGycXGUuz7fuNQhpix7/ALni7xNi+X41C0Ju2O588Xlabnd/EahasTY7P4niz2m9BqAsZ3qOKyDgedSzDgef7f/Z";

    public UserController(UserRepository userRepository, CountryRepository countryRepository, SportRepository sportRepository) {
        _userRepository = userRepository;
        _countryRepository = countryRepository;
        _sportRepository = sportRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        logger.info("Getting all users");
        return _userRepository.findAll();

        /*return _userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();*/
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable("id") UUID id) {
        logger.info("Getting user with id: " + id);

        return _userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        /*User foundUser = _userRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return UserDTO.fromEntity(foundUser);*/
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating new user");
        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Country country = _countryRepository.findByName(user.getCountry().getName());
        if (country == null) {
            Country newCountry = new Country(
                    user.getCountry().getName(),
                    user.getCountry().getPhoneCode(),
                    user.getCountry().getFlagIcon()
            );

            _countryRepository.save(newCountry);
            user.getCountry().setId(newCountry.getId());
        } else
            user.getCountry().setId(country.getId());

        /*Sport favSport = _sportRepository.findByName(user.getFavoriteSport().getName());
        if(favSport != null){
            user.getFavoriteSport().setId(favSport.getId());
        }*/
        user.setProfilePicture(DEFAULT_PROFILE_IMAGE_BASE64);
        User newUser = _userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable UUID id, @RequestBody User user) {
        User existingUser = _userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        Country country = _countryRepository.findByName(user.getCountry().getName());
        if (country == null) {
            Country newCountry = new Country(
                    user.getCountry().getName(),
                    user.getCountry().getPhoneCode(),
                    user.getCountry().getFlagIcon()
            );

            _countryRepository.save(newCountry);
            user.getCountry().setId(newCountry.getId());
        }
        else user.getCountry().setId(country.getId());

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setCity(user.getCity());
        existingUser.setMobilePhone(user.getMobilePhone());
        existingUser.setPasswordHash(user.getPasswordHash());
        existingUser.setGender(user.getGender());
        existingUser.setCountry(user.getCountry());
        existingUser.setFavoriteSport(user.getFavoriteSport());

        try {
            return _userRepository.save(existingUser);
        } catch (Exception e) {
            logger.error("Error updating user with id: {}", id, e);
            throw e;
        }
    }

    @PutMapping("/{id}/update-image")
    public boolean updateProfilePicture(@PathVariable UUID id, @RequestBody String base64) {
        User existingUser = _userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingUser.setProfilePicture(base64);
        try {
            _userRepository.save(existingUser);
            return true;
        }
        catch (Exception e) {
            logger.error("Error updating profile picture for user with id: {}", id, e);
            return false;
        }
    }
}
