package com.vike.spider.security;

import com.vike.spider.stock.entity.Client;
import com.vike.spider.stock.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: lsl
 * @createDate: 2019/9/29
 */
@Service
public class ClientLoginService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<Client> optional = clientRepository.findClientByLoginName(s);

        if(optional.isPresent()){

            Client client = optional.get();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            return new ClientDetail(client.getLoginName(),client.getPassword()+"0000"+client.getSalt(),grantedAuthorities,
                    client.getClientMenus(), client.getClientName());

        }else {
            throw new UsernameNotFoundException("账号不存在");
        }
    }
}
