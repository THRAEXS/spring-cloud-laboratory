package pers.guiwang.admin.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guiwang.admin.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 鬼王
 * @Date 2018/11/02 15:21
 */
@RestController
@RequestMapping("/user")
@Log
public class UserController {

    private static final String DEF_ID = "key";
    private static final String DEF_NAME = "鬼王";
    private static final String DEF_ACCOUNT = "Guiwang";
    private static final String DEF_PASSWORD = "000000";

    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        final int size = 10;
        List<User> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            log.info("list index: " + i);
            list.add(new User(DEF_ID+i, DEF_NAME+i, DEF_ACCOUNT+i, DEF_PASSWORD));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> find(@PathVariable("id") String id) {
        log.info("find user ------------");

        return new ResponseEntity<>(new User(id, DEF_NAME, DEF_ACCOUNT, DEF_PASSWORD), HttpStatus.OK);
    }

    /**
     * add(@RequestBody User user)配合[data]
     * add(User user)配合[params]
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody User user) {
        log.info("PostMapping add ------------");
        log.info(user.toString());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody User user) {
        log.info("PutMapping update ------------");
        log.info(user.toString());

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        log.info("DeleteMapping delete ------------");
        log.info(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
