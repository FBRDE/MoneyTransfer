package com.openclassrooms.moneytransfer.service;

import com.openclassrooms.moneytransfer.model.Transaction;
import com.openclassrooms.moneytransfer.model.User;
import com.openclassrooms.moneytransfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    //Get list of users
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
    //Get user by id
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    //Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    //Get user by password
    public Optional<User> getUserByPassword(String password) {
        return userRepository.findByPassword(password);
    }
    public boolean existsById(Integer id){return  userRepository.existsById(id);}
    //Add or update user
    @Transactional
    public User addOrUpdateUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roleService.findByRoleName("ROLE_USER"));
        return userRepository.save(user);
    }
    //Delete user
    @Transactional
    public void deleteUser(User user) {
         userRepository.delete(user);
    }
    public Map<Integer,String> getConnection(String email) {
       User user= userRepository.findByEmail(email).get();
       List<User> friends=user.getFriends();
       Map<Integer, String> friendsMap=new HashMap<>();
        for (User friend: friends) {
            friendsMap.put(friend.getId(), friend.getLastName());

        }
       return friendsMap;
    }
    //When user is connected by social media this method is called
    @Transactional
    public void processOAuthPostLogin(String username) {
        if (!(userRepository.existsByEmail(username)))
         {
             User newUser = new User();
             newUser.setEmail(username);
             newUser.setRoles(roleService.findByRoleName("ROLE_USER"));
             userRepository.save(newUser);
        }
    }
    //Get list of transaction of user
    @Transactional(readOnly = true)
    public List<Transaction> getTransactions(String username) {
        List<Transaction> transactions=null;
        if (userRepository.existsByEmail(username))
        {User user=userRepository.findByEmail(username).get();
         transactions= user.getTransactions();
        }
        return transactions;
    }
    //Get user id by username
    @Transactional(readOnly = true)
    public int getUserId(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            return userRepository.findByEmail(user.getEmail()).get().getId();
        } else {
            return 0;
        }
    }
    //Add new connection
    @Transactional
    public String addConnection(String friendEmail, String email) {
        if (userRepository.existsByEmail(friendEmail)) {
            User friend = userRepository.findByEmail(friendEmail).get();
            User user = userRepository.findByEmail(email).get();
            if (user.getFriends().contains(friend)){
                return "exists";
            } else {
                List<User> friends =user.getFriends() ;
                friends.add(friend);
                user.setFriends(friends);
                userRepository.save(user);
                return "success";
            }
        } else {
            return "error";
        }
    }
    //Find pages for user
    @Transactional
    public Page<User> findPaginated(User user,Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
       List<User> friends=user.getFriends();
        if (friends.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, friends.size());
            list = friends.subList(startItem, toIndex);
        }
        Page<User> userPage
                = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), friends.size());

        return userPage;
    }
    //Update user
    @Transactional
    public boolean UpdateUser(User user, String email) {
        User userToModify=null;
        if (userRepository.existsByEmail(email))
        { userToModify= userRepository.findByEmail(email).get();
        user.setId(userToModify.getId());
        user.setFriends(userToModify.getFriends());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        String password2=userToModify.getPassword();

        if(password != "")
        {
            if (!(password.equals(password2)))
        user.setPassword(passwordEncoder.encode(password));
        }
        else
            user.setPassword(password2);
        user.setRoles(roleService.findByRoleName("ROLE_USER"));
        userRepository.save(user);

        return true;

        }
        return false;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}