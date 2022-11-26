package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class CustomerController
{
	private final List<Customer> customers = new ArrayList<>();
	private int count = 1;

	//curl -X GET  http://localhost:8080/customer/all
	@GetMapping("customer/all")
	public ResponseEntity<List<Customer>> getCustomers(
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direction", required = false) String direction,
			@RequestParam(value = "paging", required = false) String paging,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "index", required = false) Integer index)
	{
		List<Customer> list = customers;
		if(sortBy == null && direction == null && paging == null)
		{
			return ResponseEntity.ok(customers);
		}
		if(sortBy !=null)
		{
			switch (sortBy) {
				case "username":
					Collections.sort(list, new UsernameComparator());
					break;
				case "id":
					Collections.sort(list, new IdComparator());
					break;
				case "age":
					Collections.sort(list, new AgeComparator());
					break;
				default:
					return ResponseEntity.ok(customers);
			}
		}
		if(direction !=null)
		{
			switch (direction)
			{
				case "up":
					Collections.sort(list, new IdComparator());
					break;
				case "down":
					Collections.sort(list, new DownIdComparator());
					break;
				default:
					return ResponseEntity.ok(customers);
			}
		}
		if(paging !=null)
		{
			return ResponseEntity.ok(list.subList(index, Math.min(index+limit, list.size()-1)));
		}
		return null;
	}
	static class DownIdComparator implements Comparator<Customer> {
		@Override
		public int compare(Customer a, Customer b) {
			return Integer.compare((int)-a.getId(),(int) -b.getId());
		}
	}
	static class UsernameComparator implements Comparator<Customer> {
		public int compare(Customer a, Customer b) {
			return a.username.compareToIgnoreCase(b.username);
		}
	}

	static class IdComparator implements Comparator<Customer> {
		@Override
		public int compare(Customer a, Customer b) {
			return Integer.compare((int)a.getId(),(int) b.getId());
		}
	}
	static class AgeComparator implements Comparator<Customer> {
		@Override
		public int compare(Customer a, Customer b) {
			return Integer.compare(a.getAge(), b.getAge());
		}
	}

//	curl -X POST 'http://localhost:8080/customer?repeatPassword=bad' -H 'Content-Type: application/json' --data-raw '{
//		"username": "Vladoss",
//		"password": "bad",
//		"age": 17
//}'
	@PostMapping("customer")
	public ResponseEntity<Void> addCustomers(@RequestBody Customer customer, @RequestParam String repeatPassword)
	{
		if (customer.getPassword().equals(repeatPassword))
		{
			for (Customer c : customers) {
				if (c.getUsername().equals(customer.getUsername())) {
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}
			}
			customer.id = count;
			customers.add(customer);
			count++;
			return ResponseEntity.accepted().build();
		}
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	//curl -X GET http://localhost:8080/customer?id=1
	@GetMapping("customer")
	public ResponseEntity<Customer> getCustomer(@RequestParam Integer id)
	{
		for (Customer c : customers)
		{
			if(c.getId() == id)
			{
				return ResponseEntity.ok(c);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//curl -X DELETE 'http://localhost:8080/customer?id=1'
	@DeleteMapping("customer")
	public ResponseEntity<Customer> deleteCustomer(@RequestParam Integer id)
	{
		int i = 0;
		for (Customer c : customers)
		{
			if(c.getId() == id)
			{
				customers.remove(i);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			i++;
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

//	curl -X PUT 'http://localhost:8080/customer?id=1&repeatPassword=bad' -H 'Content-Type: application/json' --data-raw '{
//		"username": "Vladok",
//		"password": "bad",
//		"age": 17
//}'
	@PutMapping("customer")
	public ResponseEntity<Customer> putCustomer(@RequestParam Integer id, @RequestParam String repeatPassword, @RequestBody Customer customer)
	{
		if(repeatPassword.equals(customer.getPassword())) {
			int i = 0;
			for (Customer c : customers) {
				if (c.getId() == id) {
					customer.setId(customers.get(i).getId());
					customers.remove(i);
					customers.add(i, customer);
					return new ResponseEntity<>(HttpStatus.OK);
				}
				i++;
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	//curl -X GET 'http://localhost:8080/customer/age?age=1'
	@GetMapping("customer/age")
	public ResponseEntity<List<Customer>> choiseAgeCustomer(@RequestParam Integer age) {
		List<Customer> list = new ArrayList<>();
		for (Customer c : customers) {
			if (Math.abs(c.getAge() - age) <= 5)
			{
				list.add(c);
			}
		}
		if(list.get(0) == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(list);
	}

}
