package de.hackerspacebremen.domain.helper;

public enum FeedType {

	ATOM,
	
	RSS;
	
	public String toString() {
		String result = "";
		switch(this){
		case ATOM:
			result = "atom_1.0";
			break;
		case RSS:
			result = "rss_2.0";
		}
		return result;
	};
}
