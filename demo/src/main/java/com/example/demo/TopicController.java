//вместо слов в фигурных скобках мы можем туда записать всё, что подходит под это слово, например {text} - String, {number} - int
    //ВВЕСТИ Remove-item alias:curl ПЕРЕД НАЧАЛОМ РАБОТЫ, НЕ ВАЖНО ЧТО ЭТО И КАК РАБОТАЕТ, ГЛАВНОЕ ЧТО РАБОТАЕТ
package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TopicController
{
    //Topics

    private List<Topic> topics = new ArrayList<>();
    private List<User> user = new ArrayList<>();

    //curl -X POST -d {text} http://localhost:8080/topic/
    @PostMapping("topic")
    public ResponseEntity<Void> addTopic(@RequestBody Topic topic)
    {
        if (user.size() == 0)
        {
            user.add(topic.user);
        }
        else
        {
            int a = 0;
            for (int i = 0; i < topics.size(); i++) {
                if (topics.get(i).user.name.equals(topic.user.name) && topics.get(i).user.age == topic.user.age) {
                    for (int j = 0; j < user.size(); j++) {
                        if (user.get(i).comments.get(j).name.equals(topic.name)) {
                            user.get(i).comments.get(j).comment.addAll(topic.comment);
                        } else
                            user.get(i).comments.add(topic);
                    }
                    a++;
                }
            }
            if (a == 0) {
                user.add(topic.user);
            }
        }
        topics.add(topic);
        return ResponseEntity.accepted().build();
    }

    //curl -X GET http://localhost:8080/topic/
    @GetMapping("topic")
    public ResponseEntity<List<Topic>> getTopics()
    {
        return ResponseEntity.ok(topics);
    }

    //curl -X DELETE http://localhost:8080/topic/{index}
    @DeleteMapping("topic/{index}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("index") Integer index)
    {
        topics.remove((int) index);
        return ResponseEntity.noContent().build();
    }

    //curl -X PUT -H "Content-Type: application/json" -d {nameTopic} http://localhost:8080/topic/{index}
    @PutMapping("topic/{index}")
    public ResponseEntity<Void> updateTopic(@PathVariable("index") Integer i, @RequestBody String name)
    {
        topics.get(i).setName(name);
        return ResponseEntity.accepted().build();
    }

    // curl -X  GET  http://localhost:8080/topic/count
    @GetMapping("topic/count")
    public ResponseEntity<Integer> countTopic()
    {
        return ResponseEntity.ok(topics.size());
    }

    //curl -X DELETE http://localhost:8080/topic/
    @DeleteMapping("topic")
    public ResponseEntity<Void> deleteTopics()
    {
        topics = new ArrayList<>();
        return ResponseEntity.noContent().build();
    }

    //curl -X GET http://localhost:8080/topic/{index}
    @GetMapping("topic/{index}")
    public ResponseEntity<Topic> getTopic(@PathVariable("index") Integer index)
    {
        return ResponseEntity.ok(topics.get(index));
    }

    //curl -X POST http://localhost:8080/topic/0/create -H 'Content-Type: application/json' --data-raw 'auto'
    @PostMapping("topic/{index}/create")
    public ResponseEntity<Void> addComment(@PathVariable("index") Integer index, @RequestBody String text)
    {
        topics.get(index).comment.add(text);
        return ResponseEntity.accepted().build();
    }

    //curl -X DELETE http://localhost:8080/topic/0/delete/0
    @DeleteMapping("topic/{index1}/delete/{index2}")
    public ResponseEntity<Void> deleteComment(@PathVariable("index1") Integer index1, @PathVariable("index2") Integer index2)
    {
        topics.get(index1).comment.remove(index2);
        return ResponseEntity.noContent().build();
    }

    //curl -X  POST -d {text} http://localhost:8080/topic/{index1}/edit/{index2}
    @PostMapping("topic/{index1}/edit/{index2}")
    public ResponseEntity<Void> editComment(@PathVariable("index1") Integer index1, @PathVariable("index2") Integer index2, @RequestBody String text)
    {
        topics.get(index1).comment.remove(index2);
        topics.get(index1).comment.add(index2, text);
        return ResponseEntity.accepted().build();
    }

    //curl -X GET http://localhost:8080/topic/{index}/comments
    @GetMapping("topic/{index}/comments")
    public ResponseEntity<List<String>> getComments(@PathVariable("index") Integer index)
    {
        return ResponseEntity.ok(topics.get(index).comment);
    }

    //curl -X GET http://localhost:8080/topic/{index}/comments
    @GetMapping("topic/user/{userIndex}")
    public ResponseEntity<User> getUser(@PathVariable("userIndex") Integer userIndex)
    {
        return ResponseEntity.ok(user.get(userIndex));
    }

    //curl -X GET http://localhost:8080/topic/{index}/comments
    @GetMapping("topic/user/{userIndex}/comments")
    public ResponseEntity<ArrayList<Topic>> getUserComments(@PathVariable("userIndex") Integer userIndex)
    {
        return ResponseEntity.ok(user.get(userIndex).comments);
    }

}
