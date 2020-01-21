package com.ordersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordersystem.entity.Order;
import com.ordersystem.service.OrderService;

@RestController
@RequestMapping("/waterorderingapp")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public List getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> getOrderStatus(@PathVariable("orderId") long orderId) {
		return new ResponseEntity<Order>(orderService.getOrderStatus(orderId), HttpStatus.OK);
	}

	@PostMapping("/orders/add/")
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		if (orderService.addOrder(order)) {
			return new ResponseEntity<Order>(order, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping("orders/update/{orderId}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable("orderId") long orderId, @RequestBody Order order) {

		if (orderService.updateOrder(orderId, order)) {
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/orders/cancel/{orderId}")
	public ResponseEntity<HttpStatus> cancelOrder(@PathVariable long orderId) {
		if (orderService.cancelOrder(orderId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/statusupdates")
	public List showStatus() {
		return orderService.showOrderStatusUpdates();
	}

}
