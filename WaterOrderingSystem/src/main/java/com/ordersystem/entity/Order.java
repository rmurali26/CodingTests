package com.ordersystem.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name ="waterorder")
public class Order implements Serializable{

/**
	 * 
	 */
private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="orderId",updatable=false, nullable= false)
private long orderId;
@Column(name="farmId")
private long farmId;
@Column(name="startDateTime")
private LocalDateTime startDateTime;
@Column(name="durationHours")
private int durationHours;
@Column(name="durationMinutes")
private int durationMinutes;
@Column(name="orderStatus")
@Enumerated(EnumType.STRING)
private OrderStatus status;
@Column(name="createdDateTime")
private LocalDateTime createdDateTime;
public Order() {

}
public Order(long orderId,long farmId,LocalDateTime startDateTime,int durationHours,int durationMinutes,OrderStatus status,LocalDateTime createdDateTime) {
	this.orderId = orderId;
	this.farmId = farmId;
	this.startDateTime =startDateTime;
	this.durationHours = durationHours;
	this.durationMinutes = durationMinutes;
	this.status = status;
	this.createdDateTime = createdDateTime;
}


public long getOrderId() {
	return orderId;
}
public void setOrderId(long orderId) {
	this.orderId = orderId;
}
public OrderStatus getStatus() {
	return status;
}
public void setStatus(OrderStatus status) {
	this.status = status;
}
public long getFarmId() {
	return farmId;
}
public void setFarmId(long farmId) {
	this.farmId = farmId;
}
public LocalDateTime getStartDateTime() {
	return startDateTime;
}
public void setStartDateTime(LocalDateTime startDateTime) {
	this.startDateTime = startDateTime;
}
public int getDurationHours() {
	return durationHours;
}
public void setDurationHours(int durationHours) {
	this.durationHours = durationHours;
}
public int getDurationMinutes() {
	return durationMinutes;
}
public void setDurationMinutes(int durationMinutes) {
	this.durationMinutes = durationMinutes;
}
public LocalDateTime getCreatedDateTime() {
	return createdDateTime;
}
public void setCreatedDateTime(LocalDateTime createdDateTime) {
	this.createdDateTime = createdDateTime;
}


}
