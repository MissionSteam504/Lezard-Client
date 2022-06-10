package fr.lezard.events.listeners;

import fr.lezard.events.Event;
import net.minecraft.client.KeyMapping;

public class EventKey extends Event<EventKey>{
	private int key;
	
	public EventKey(KeyMapping key) {
		this.key = key.getKey().getValue();
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
