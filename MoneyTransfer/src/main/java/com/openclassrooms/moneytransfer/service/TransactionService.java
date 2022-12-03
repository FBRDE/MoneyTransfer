package com.openclassrooms.moneytransfer.service;

import com.openclassrooms.moneytransfer.dto.TransferForm;
import com.openclassrooms.moneytransfer.dto.TransferFormat;
import com.openclassrooms.moneytransfer.model.Transaction;
import com.openclassrooms.moneytransfer.model.User;
import com.openclassrooms.moneytransfer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;

    //Allows to retrieve the number of pages to display the transactions of a user
    @Transactional(readOnly = true)
    public int getTotalPages(int userId, int page) {
        User user = userService.getUserById(userId).get();
        return transactionRepository.findBySender(user, PageRequest.of(page, 3)).getTotalPages();
    }
    //Get list og friends of a user
    @Transactional(readOnly = true)
    public Map<Integer,String> getConnections(String email) {
        User user= userService.getUserByEmail(email).get();
        List<User> friends=user.getFriends();
        Map<Integer, String> friendsMap=new HashMap<>();
        for (User friend: friends) {
            friendsMap.put(friend.getId(), friend.getFirstName().concat(" ").concat(friend.getLastName()));

        }
        return friendsMap;
    }
    //Get transactions of a user
    @Transactional(readOnly = true)
    public List<TransferFormat> getTransactions(int userId, int page) {

        List<TransferFormat> transferList=null;
        if(userService.existsById(userId))
        {
        User user = userService.getUserById(userId).get();
         transferList = transactionRepository.findBySender(user, PageRequest.of(page, 3, Sort.by("id").descending())).get().map(
                t->new TransferFormat(
                        t.getId(),
                        t.getReceiver().getFirstName().concat(" ").concat(t.getReceiver().getLastName()),
                        t.getDescription(),
                        t.getAmount(),
                        t.getTransactionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")))).collect(Collectors.toList());
        }
        return transferList;
    }

    //Add a new transfer
    @Transactional
    public boolean addTransfer(TransferForm transferForm, String email) {
        User sender = userService.getUserByEmail(email).get();
        User receiver = userService.getUserById(transferForm.getReceiverId()).get();

        Double transferAmount = transferForm.getAmount();
        if (sender.getBalance()>=transferAmount)
        {
            //0.5% levy to monetize the application
            sender.setBalance(sender.getBalance()-transferAmount-(0.005*transferAmount));

            receiver.setBalance(receiver.getBalance()+transferAmount);
            Transaction transaction = new Transaction(0,LocalDateTime.now(),
                transferAmount,transferForm.getDescription(),
                sender,
                receiver);

            List <Transaction> transactions = sender.getTransactions();
            transactions.add(transaction);
            sender.setTransactions(transactions);
            transactionRepository.save(transaction);
            userService.addOrUpdateUser(sender);
            userService.addOrUpdateUser(receiver);
            return true;

        }
        return false;
    }
}