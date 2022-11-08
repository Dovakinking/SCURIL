//вместо слов в фигурных скобках мы можем туда записать всё, что подходит под это слово, например {text} - String, {number} - int
//ВВЕСТИ Remove-item alias:curl ПЕРЕД НАЧАЛОМ РАБОТЫ, НЕ ВАЖНО ЧТО ЭТО И КАК РАБОТАЕТ, ГЛАВНОЕ ЧТО РАБОТАЕТ
package com.example.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class Controller
{
    //MESSAGES

    private final List<String> messages = new ArrayList<>();

    //curl -X GET  http://localhost:8080/messages/
    /*@GetMapping("messages")
    public ResponseEntity<List<String>> getMessages()
    {
        return ResponseEntity.ok(messages);
    }*/

    //curl -X POST -d {text} http://localhost:8080/messages/
    @PostMapping("messages")
    public ResponseEntity<Void> addMessage(@RequestBody String text)
    {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }

    //curl -X GET -H "Content-Type: application/json" -d {name} http://localhost:8080/messages/{index}
    @GetMapping("messages")
    public ResponseEntity<List<String>> getText(@RequestBody String text)
    {
        Iterator<String> x = messages.listIterator(0);
        int[] i = new int[messages.size()];
        int count = 0;
        List<String> str = new ArrayList<>();
        while (x.hasNext())
        {
            if (x.next().toString().startsWith(text))
                i[count] = 239;
            count++;
        }
            for (int j = 0; j < messages.size(); j++)
        {
            if (i[j] == 239)
            {
                str.add(messages.get(j));
            }
        }
            return ResponseEntity.ok(str);
    }

    //curl -X GET http://localhost:8080/messages/{index}
    @GetMapping("messages/{index}")
    public ResponseEntity<String> getMessage(@PathVariable("index") Integer index)
    {
        return ResponseEntity.ok(messages.get(index));
    }

    //curl -X DELETE http://localhost:8080/messages/{index}
    @DeleteMapping("messages/{index}")
    public ResponseEntity<Void> deleteText(@PathVariable("index") Integer index)
    {
        messages.remove((int) index);
        return ResponseEntity.noContent().build();
    }

    //curl -X PUT -H "Content-Type: application/json" -d {message} http://localhost:8080/messages/{index}
    @PutMapping("messages/{index}")
    public ResponseEntity<Void> updateMessage(@PathVariable("index") Integer i, @RequestBody String message)
    {
        messages.remove((int) i);
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }

    // curl -X  GET  http://localhost:8080/messages/search/{text}
    @GetMapping("messages/search/{text}")
    public ResponseEntity<Integer> searchMessages(@PathVariable("text") String text)
    {
        int temp = /*ес*/ - 3;//юху
        for (int i = 0; i < messages.size(); i++)
        {
            if(messages.get(i).contains(text))
            {
                temp = i;
                break;
            }
        }
        return ResponseEntity.ok(temp);
    }

    // curl -X  GET  http://localhost:8080/messages/count
    @GetMapping("messages/count")
    public ResponseEntity<Integer> countMessages()
    {
        return ResponseEntity.ok(messages.size());
    }

    //curl -X  POST -d {text} http://localhost:8080/messages/
    @PostMapping("messages/{index}/create")
    public ResponseEntity<Void> addMessage(@PathVariable("index") Integer index_1, @RequestBody String text)
    {
        messages.add(index_1-1, text); return ResponseEntity.accepted().build();
    }

    //curl -X  DELETE http://localhost:8080/messages/search/{substring}
    @DeleteMapping("messages/search/{text}")
    public ResponseEntity<Void> deleteText(@PathVariable("text") String text)
    {
        Iterator<String> x = messages.listIterator(0);
        int[] i = new int[messages.size()];
        int count = 0;
        while (x.hasNext())
        {
            if (x.next().toString().contains(text))
                i[count] = 239;
            count++;
        }
        count = 0;
            for (int j = 0; j < messages.size(); j++)
            {
                if (i[count] == 239)
                {
                    messages.remove(j);
                    j--;
                }
                count++;
            }
            return ResponseEntity.noContent().build();
    }

    //USERS
    private final List<User> users = new ArrayList<User>();

    //curl -X GET http://localhost:8080/users
    @GetMapping("users")
    public ResponseEntity<String> getUser()
    {
        return ResponseEntity.ok(users.toString());
    }

    //curl -X POST -H "Content-Type: application/json" -d {name} -d {age}  http://localhost:8080/users
    @PostMapping("users")
    public ResponseEntity<Void> addUser(@RequestBody String тфьу_фпу)
    {
        users.add(new User(тфьу_фпу));
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
