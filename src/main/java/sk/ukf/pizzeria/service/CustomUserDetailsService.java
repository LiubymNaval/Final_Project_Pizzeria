package sk.ukf.pizzeria.service;

import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.repository.PouzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PouzivatelRepository pouzivatelRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pouzivatel pouzivatel = pouzivatelRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Používateľ s emailom " + email + " nebol nájdený."));

        List<SimpleGrantedAuthority> authorities = pouzivatel.getRoly().stream()
                .map(rola -> new SimpleGrantedAuthority(rola.getNazov()))
                .collect(Collectors.toList());

        return new User(
                pouzivatel.getEmail(),
                pouzivatel.getHeslo(),
                authorities
        );
    }
}
