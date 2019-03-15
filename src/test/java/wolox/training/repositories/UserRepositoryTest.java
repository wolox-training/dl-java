package wolox.training.repositories;


import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertThrows;

import wolox.training.models.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User testUser = new User();

    @Before
    public void init() throws JSONException { ;
        testUser.setId(1);
        testUser.setUsername("Daniela");
        testUser.setBirthdate(LocalDate.of(1992, 1, 28));
        userRepository.save(testUser);
    }

    @Test
    public void whenUserWithoutName_throwException() {
        User wrongUser = new User();
        wrongUser.setId(2);
        wrongUser.setBirthdate(LocalDate.of(1992, 1, 28));
        assertThrows(IllegalArgumentException.class, () -> wrongUser.setUsername(null));
    }

    @Test
    public void whenFindUserById_thenReturnUser(){
        User queriedUser = userRepository.findById(testUser.getId()).get();
        assertThat(queriedUser).hasFieldOrPropertyWithValue("id", testUser.getId());
    }

    @Test
    public void whenFindByUserByName_thenReturnUser(){
        User queriedUser = userRepository.findByUsername(testUser.getUsername()).get();
        assertThat(queriedUser).hasFieldOrPropertyWithValue("username", testUser.getUsername());
    }
}
