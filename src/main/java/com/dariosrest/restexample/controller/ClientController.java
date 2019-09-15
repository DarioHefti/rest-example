package com.dariosrest.restexample.controller;

import com.dariosrest.restexample.model.Adress;
import com.dariosrest.restexample.model.Laptop;
import com.dariosrest.restexample.model.Product;
import com.dariosrest.restexample.model.Users;
import com.dariosrest.restexample.repository.AdressRepository;
import com.dariosrest.restexample.repository.LaptopRepository;
import com.dariosrest.restexample.repository.ProductRepository;
import com.dariosrest.restexample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/users")
public class ClientController {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    AdressRepository adressRepository;
    @Autowired
    LaptopRepository laptopRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/start")
    public List<Users> start() {
        final Product product1 = new Product();
        product1.setName("Brot");
        product1.setPrice(2.5);

        final Product product2 = new Product();
        product2.setName("Käse");
        product2.setPrice(6.50);

        final Adress adress = new Adress();
        adress.setCityname("Gossau");
        adress.setStreetnumber(15);
        adress.setStreetname("Talstrasse");
        adress.setZip(9200);


        Users users = new Users();
        users.setName("ruedi");
        users.setAdress(adress);
        adress.setUsers(users);

        final Laptop laptop = new Laptop();
        laptop.setName("dell");
        laptop.setPrice(135.51);
        users.getLaptops().add(laptop);
        laptop.setUsers(users);

        final Laptop laptop1 = new Laptop();
        laptop1.setName("acer");
        laptop1.setPrice(1355.56);
        users.getLaptops().add(laptop1);
        laptop1.setUsers(users);

        users.getProducts().add(product1);
        users.getProducts().add(product2);

        product1.getUsers().add(users);
        product2.getUsers().add(users);

        usersRepository.save(users);

        final Users users1 = new Users();
        users1.setName("peter");
        final Adress adress1 = new Adress();
        adress1.setStreetname("Schaffhauserstrassse");
        adress1.setStreetnumber(5);
        adress1.setCityname("Winti");
        users1.setAdress(adress1);
        adress1.setUsers(users1);

        users1.getProducts().add(product2);
        product2.getUsers().add(users1);
        usersRepository.save(users1);

        return Arrays.asList(users, users1);
    }

    @GetMapping(value = "/all")
    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    @PostMapping(value = "/load")
    public List<Users> persist(@RequestBody final Users users) {
        usersRepository.save(users);
        return usersRepository.findAll();
    }

    @GetMapping(value = "/{name}")
    public List<Users> findByName(@PathVariable final String name) {
        return usersRepository.findByName(name);
    }

    @GetMapping(value = "/removeAll")
    public void removeAll() {
        usersRepository.deleteAll();
        adressRepository.deleteAll();
        laptopRepository.deleteAll();
        productRepository.deleteAll();
    }

    @GetMapping(value = "/ProductForUser/{userName}")
    public List<Product> getUsersForProduct(@PathVariable final String userName) {
        final List<Users> users = usersRepository.findByName(userName);
        List<Product> productsForUserName = new ArrayList<>();
        for (Users currUser : users) {
            productsForUserName.addAll(productRepository.findByUsersId(currUser.getId()));
        }
        return productsForUserName;
    }

}
