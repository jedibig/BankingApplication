package com.java.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class JournalEntry {
	private int transID;
	private int sender; 
	private int receiver;
	private double nominal;
	
	public JournalEntry(int sender, int receiver, double nominal){
		this.sender = sender;
		this.receiver = receiver;
		this.nominal = nominal;
	}
}
