package com.uo226321.joel.gymnastics.model;

import java.io.Serializable;

public class GymUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userCard;
	private String key;
	
	
	public GymUser() {

	}


	public GymUser(int userCard, String key) {
		super();
		this.userCard = userCard;
		this.key = key;
	}

	public int getUserCard() {
		return userCard;
	}


	public void setUserCard(int userCard) {
		this.userCard = userCard;
	}

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + userCard;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GymUser other = (GymUser) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (userCard != other.userCard)
			return false;
		return true;
	}
	
	
	
	

}
