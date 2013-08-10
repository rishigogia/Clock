package com.visa.clock;

public class Clock {
	
	private final String[] MIN = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
	private final String[] HOUR = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven"};
	private final String TIME24HOURS_PATTERN = "([01][0-9]|2[0-3]):[0-5][0-9]";
	
	/**
	 * This method is used to get the time in conversational format
	 * @param time - String time in 24 hour format. (Format HH:MM)
	 * @return - The Time in English Conversational Format.
	 */
	public String tellTime(String time) {
		
		// Initialize the time to be returned
		StringBuffer conversationalTime = new StringBuffer();
		
		// Validate whether the time is in correct format
		if(!validate(time)) {
			return "Invalid Time Format. Please enter in HH:MM (24 hour format)";
		}
		
		// Getting the HH (hour) and MM (min) from the entered time. Converting it in integer format
		String[] hhmm = time.split(":");
		int hour = Integer.parseInt(hhmm[0]);
		int min = Integer.parseInt(hhmm[1]);
		
		if(min == 59 || min == 14 || min == 29 || min == 44) {
			// It's almost the referential time if it's just one minute left to it. Thus incrementing the hour and min
			hour = (min == 59) ? ((hour == 23)?0:++hour) : hour;
			min = (min == 59) ? 0 : ++min;
			conversationalTime.append("Almost ");
		} else if(min == 1 || min == 16 || min == 31 || min == 46) {
			// It's just after the referential time if it's just one minute past it.
			min--;
			conversationalTime.append("Just after ");
		}
		
		if(min > 0) {
			// Outputting the Minute. Nothing to do in case of 0 minutes past the hour
			if(min == 30) {
				// It's Half past the hour if it's 30 minutes past
				conversationalTime.append("Half past ");
			} else if(min == 15) {
				// It's Quarter past the hour if it's 15 minutes past
				conversationalTime.append("Quarter past ");
			} else if(min == 45) {
				// It's Quarter to the next hour if it's 45 minutes past
				conversationalTime.append("A quarter to ");
				// Need to increment the hour if it's quarter to the next hour
				hour = (hour == 23) ? 0 : ++hour;
			} else {
				if(min >= 2 && min <= 10) {
					// Printing 2 = Two for minutes from 2 to 10
					conversationalTime.append(MIN[min-2]);
				} else {
					// Printing number in case minutes are greater than 10
					conversationalTime.append(min);
				}
				conversationalTime.append(" past ");
			}
		}
		
		if(hour >= 12) {
			// Outputting the hour if hour is after noon
			if(hour == 12) {
				// The Noon hour
				conversationalTime.append("12 Noon");
			} else if(hour < 16) {
				// Hour 13-15 is afernoon
				conversationalTime.append(HOUR[(hour-12)-1]);
				conversationalTime.append(" in the afternoon");
			} else if(hour < 20) {
				// Hour 16-20 is evening
				conversationalTime.append(HOUR[(hour-12)-1]);
				conversationalTime.append(" in the evening");
			} else if(hour >= 20) {
				// Hour 20-23 is night
				conversationalTime.append(HOUR[(hour-12)-1]);
				conversationalTime.append(" in the night");
			}
		} else {
			// Outputting the hour if hour is past midnight till noon
			if(hour == 0) {
				// Hour 0 is midnight
				conversationalTime.append("Midnight");
			} else {
				// Hour 1-11 is morning
				conversationalTime.append(HOUR[hour-1]);
				conversationalTime.append(" in the morning");
			}
		}
		
		// Returning the time in Conversational Format
		return conversationalTime.toString();
	}
	
	/**
	 * This method is used to validate whether correct time was passed
	 * 
	 * @param time - The Time to be validated
	 * @return - Boolean whether the time is a valid 24 hour time in HH:MM Format
	 */
	private boolean validate(String time) {
		
		// Return whether the time matches the regular expression pattern
		return time.matches(TIME24HOURS_PATTERN);
	}
	
	/**
	 * This method is used as the point of entry to the application
	 * 
	 * @param ar - The command line arguments
	 */
	public static void main(String ar[]) {
		
		Clock clock = new Clock();
		
		// Validating the command line parameters
		if(ar.length < 1) {
			System.out.println("Invalid call to class. Please call using the command line arguement as following");
			System.out.println("java com.visa.clock.Clock <<HH:MM> [<<HH:MM>>, [<<HH:MM>>, .......]]");
			System.out.println("eg. java com.visa.clock.Clock 19:36");
			System.out.println("eg. java com.visa.clock.Clock 19:36 12:15");
			System.out.println("eg. java com.visa.clock.Clock 19:36 12:15 11:45");
			System.out.println("etc.");
		}
		
		// Giving the output
		for(int i=0; i<ar.length; i++) {
			System.out.println(clock.tellTime(ar[i]));
		}
	}
}
