package com.ordersystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderStatus;
import com.ordersystem.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	public List getAllOrders() {
		List allOrders = new ArrayList<>();
		orderRepository.findAll().forEach(allOrders::add);
		return allOrders;
	}

	public Order getOrderStatus(long orderId) throws NoSuchElementException {
		
		Optional<Order> order = orderRepository.findById(orderId);
		
	return orderRepository.findById(orderId).get();
		
	}

	public boolean addOrder(Order order) {
		boolean result = false;
		Iterable<Order> list = orderRepository.findAll();

		if (list.iterator().hasNext()) {
			LocalDateTime NewOrderEndDateTime = CalculateEndTime(order.getStartDateTime(), order.getDurationHours(),order.getDurationMinutes());
			if (order.getStartDateTime().isBefore(LocalDateTime.now())) {
				result = false;
				} 
			else {

				for (Order o : list) {
					if (o.getStatus().equals(OrderStatus.CANCELLED)) {

					} else {

						if ((order.getFarmId() == o.getFarmId())) {
							LocalDateTime oldOrderEndDateTime = CalculateEndTime(o.getStartDateTime(),
									o.getDurationHours(), o.getDurationMinutes());

							if ((order.getStartDateTime().equals(o.getStartDateTime()))
									&& (NewOrderEndDateTime.equals(oldOrderEndDateTime))) {
								result = false;
								System.out.println("duplicate order!");
							} else if ((order.getStartDateTime().isAfter(o.getStartDateTime()))
									&& (order.getStartDateTime().isBefore(oldOrderEndDateTime))) {
								result = false;
								System.out.println("startime overlaps");
							} else if ((NewOrderEndDateTime.isAfter(o.getStartDateTime()))
									&& (NewOrderEndDateTime.isBefore(oldOrderEndDateTime))) {
								result = false;
								System.out.println("endtime overlaps");
							} else if ((order.getStartDateTime().equals(o.getStartDateTime()))
									|| (NewOrderEndDateTime.equals(oldOrderEndDateTime))) {
								result = false;
								System.out.println("consider changing the time!");
							} else {
								insertOrderData(order);
								result = true;

							}
						} else {
							insertOrderData(order);
							result = true;
						}
					}
				}
			}
		} else {
			insertOrderData(order);
			result = true;

		}
		return result;
	}

	public void insertOrderData(Order order) {
		order.setStatus(OrderStatus.REQUESTED);
		order.setCreatedDateTime(LocalDateTime.now());
		orderRepository.save(order);
	}

	public boolean updateOrder(long orderId, Order neworder) {
		boolean result = false;
		try {
			Order order = orderRepository.findById(orderId).get();
			order.setDurationHours(neworder.getDurationHours());
			order.setDurationMinutes(neworder.getDurationMinutes());
			order.setStartDateTime(neworder.getStartDateTime());
			order.setStatus(OrderStatus.REQUESTED);
			order.setCreatedDateTime(LocalDateTime.now());
			orderRepository.save(order);
			result = true;
		} catch (IllegalArgumentException e) {
			result = false;
		}
		return result;
	}

	public boolean cancelOrder(long orderId) {
		boolean result = false;
		try {
			Order order = orderRepository.findById(orderId).get();
			order.setStatus(OrderStatus.CANCELLED);
			order.setCreatedDateTime(LocalDateTime.now());
			orderRepository.save(order);
			result = true;
		} catch (IllegalArgumentException e) {
			result = false;
		}
		return result;
	}

	public List showOrderStatusUpdates() {
		String resultString = "";
		Iterable<Order> list = orderRepository.findAll();
		List results = new ArrayList<String>();

		for (Order o : list) {
			if (o.getStatus().equals(OrderStatus.CANCELLED)) {

				if ((LocalDateTime.now().isEqual(o.getCreatedDateTime()))
						| (LocalDateTime.now().isAfter(o.getCreatedDateTime()))) {
					resultString = "ORDER NO: "+o.getOrderId() + " Cancelled";
					orderRepository.deleteById(o.getOrderId());
					results.add(resultString);
				}

			} else {

				if ((LocalDateTime.now().isEqual(o.getCreatedDateTime()))
						&& (o.getStatus().equals(OrderStatus.REQUESTED))) {
					resultString = "ORDER NO: "+o.getOrderId() + " Requested";
					results.add(resultString);
				}

				else if (((LocalDateTime.now().isAfter(o.getStartDateTime()))
						|| (LocalDateTime.now().isEqual(o.getStartDateTime())))
						&& (LocalDateTime.now().isBefore(CalculateEndTime(o.getStartDateTime(), o.getDurationHours(),
								o.getDurationMinutes())))) {
					resultString = "ORDER NO: "+o.getOrderId() + " is currently IN Progress";
					results.add(resultString);
				} else if (LocalDateTime.now().isAfter(
						CalculateEndTime(o.getStartDateTime(), o.getDurationHours(), o.getDurationMinutes()))) {
					resultString = "ORDER NO: "+o.getOrderId() + " Delivered";
					results.add(resultString);
				}

			}
		}
		return results;
	}

	public LocalDateTime CalculateEndTime(LocalDateTime start, int hours, int minutes) {
		LocalDateTime EndDateTime;
		EndDateTime = start.plusHours(hours).plusMinutes(minutes);
		return EndDateTime;
	}
}
