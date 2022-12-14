package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
class Paging
{
    int number;
    int limit;
    public Paging(int number, int limit)
    {
        this.number = number;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "number=" + number +
                ", limit=" + limit +
                '}';
    }
}
@RestController
public class ContactController
{
    private final List<Contact> contacts = new ArrayList<Contact>();

    // curl -X POST -H "Content-Type: application/json" -d '{"name":"Vlad", "number":"239", "email": "239@gmail.com"}' http://localhost:8080/contacts
    @PostMapping("contacts")
    public ResponseEntity<Void> addContact(@RequestBody Contact contact)
    {
        contacts.add(contact);
        return ResponseEntity.accepted().build();
    }

    // curl -X DELETE http://localhost:8080/contacts/{index}
    @DeleteMapping("contacts/{index}")
    public ResponseEntity<Void> deleteContact(@PathVariable("index") Integer index)
    {
        contacts.remove(index);
        return ResponseEntity.accepted().build();
    }

    //curl http://localhost:8080/contacts
    @GetMapping("contacts")
    public ResponseEntity<List<Contact>> getContact()
    {
        return ResponseEntity.ok(contacts);
    }

    //curl -X PUT -H "Content-Type: application/json" -d '{"name":"Vlad", "number":"239", "email": "239@gmail.com"}' http://localhost:8080/contacts
    @PutMapping("contacts/{index}")
    public ResponseEntity<Void> updateContact(@PathVariable("index") Integer i, @RequestBody Contact contact)
    {
        contacts.remove(i);
        contacts.add(i, contact);
        return ResponseEntity.accepted().build();
    }

    //curl -X GET http://localhost:8080/contacts/0
    @GetMapping("contacts/{index}")
    public ResponseEntity<Contact> getContact(@PathVariable("index") Integer index)
    {
        return ResponseEntity.ok(contacts.get(index));
    }

    //curl -X GET http://localhost:8080/contacts/search/{substring or subnumber}
    @GetMapping("contacts/search/{substring}")
    public ResponseEntity<List<Contact>> searchContact(@PathVariable("substring") String substring)
    {
        List<Contact> contacts1 = new ArrayList<Contact>();
        for (Contact contact : contacts) {
            if (contact.email.contains(substring) || contact.name.contains(substring) || (contact.number+"").contains(substring))
                contacts1.add(contact);
        }
        return ResponseEntity.ok(contacts1);
    }

    //curl http://localhost:8080/contacts/sort/name
    @GetMapping("contacts/sort/name")
    public ResponseEntity<List<Contact>> sortNameContact()
    {
        ArrayList contacts1 = (ArrayList) contacts;
        Collections.sort(contacts1, new NameComparator());
        return ResponseEntity.ok((List<Contact>)contacts1);
    }

    //curl http://localhost:8080/contacts/sort/number
    @GetMapping("contacts/sort/number")
    public ResponseEntity<List<Contact>> sortNumberContact()
    {
        ArrayList contacts1 = (ArrayList) contacts;
        Collections.sort(contacts1, new NumberComparator());
        return ResponseEntity.ok((List<Contact>)contacts1);
    }

    //curl http://localhost:8080/contacts/sort/email
    @GetMapping("contacts/sort/email")
    public ResponseEntity<List<Contact>> sortEmailContact()
    {
        ArrayList contacts1 = (ArrayList) contacts;
        Collections.sort(contacts1, new EmailComparator());
        return ResponseEntity.ok((List<Contact>)contacts1);
    }

    class NameComparator implements Comparator<Contact> {
        public int compare(Contact a, Contact b) {
            return a.name.compareToIgnoreCase(b.name);
        }
    }

    class EmailComparator implements Comparator<Contact> {
        public int compare(Contact a, Contact b) {
            return a.email.compareToIgnoreCase(b.email);
        }
    }
    class NumberComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact a, Contact b) {
            return Integer.compare(a.number, b.number);
        }
    }

    public static String firstNChars(String str, int n) {
        if (str == null) {
            return null;
        }

        return str.length() < n ? str : str.substring(0, n);
    }

    @GetMapping ("contacts/paging")
    public ResponseEntity<String> pagingContact(@RequestBody Paging page)
    {
        int number = page.number;
        int limit = page.limit;
        if (number < 0 || limit <= 0)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(firstNChars(contacts.get(page.number-1).toString(),limit));
    }



}

