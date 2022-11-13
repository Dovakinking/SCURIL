//вместо слов в фигурных скобках мы можем туда записать всё, что подходит под это слово, например {text} - String, {number} - int
    //ВВЕСТИ Remove-item alias:curl ПЕРЕД НАЧАЛОМ РАБОТЫ, НЕ ВАЖНО ЧТО ЭТО И КАК РАБОТАЕТ, ГЛАВНОЕ ЧТО РАБОТАЕТ
package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserController
{
    //USERS
    private final List<User> users = new ArrayList<User>();

    //curl -X GET http://localhost:8080/users
    @GetMapping("users")
    public ResponseEntity<List<User>> getUser()
    {
        return ResponseEntity.ok(users);
    }

    //curl -X POST -H "Content-Type: application/json" -d {name} -d {age}  http://localhost:8080/users
    /*@PostMapping("users")
    public ResponseEntity<Void> addUser(@RequestBody String тфьу_фпу)
    {
        users.add(new User(тфьу_фпу));
        return ResponseEntity.accepted().build();
    }*/
    // curl -X POST -H "Content-Type: application/json" -d '{"name":"Vlad","age":239}'  http://localhost:8080/users
    @PostMapping("users")
    public ResponseEntity<Void> addUser(@RequestBody User user)
    {
        users.add(user);
        return ResponseEntity.accepted().build();
    }
    //curl -X DELETE http://localhost:8080/users/{index}
    @DeleteMapping("users/{index}")
    public ResponseEntity<Void> deleteUser(@PathVariable("index") Integer index)
    {
        users.remove(index);
        return ResponseEntity.noContent().build();
    }

    //curl -X GET http://localhost:8080/users/{index}
    @GetMapping("users/{index}")
    public ResponseEntity<User> getUser(@PathVariable("index") Integer index)
    {
        return ResponseEntity.ok(users.get(index));
    }

    //curl -X PUT -H "Content-Type: application/json" -d {age}  http://localhost:8080/users/{index}
    @PutMapping("users/{index}")
    public ResponseEntity<Void> updateAge(@PathVariable("index") Integer i, @RequestBody Integer age)
    {
        users.get(i).setAge(age);
        return ResponseEntity.accepted().build();
    }
}
