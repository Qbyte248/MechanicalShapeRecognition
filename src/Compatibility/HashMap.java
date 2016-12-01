package Compatibility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

public class HashMap<K, V> {

	private Hashtable<K, V> hashTable = new Hashtable<>();
	
	public V get(K key) {
		return hashTable.get(key);
	}

	public ArrayList<K> keys() {
		Enumeration<K> enumeration = hashTable.keys();
		ArrayList<K> list = new ArrayList<K>();
		for(; enumeration.hasMoreElements();) {
			list.add(enumeration.nextElement());
		}
		return list;
	}

	public void put(K key, V value) {
		hashTable.put(key, value);
	}

	public Collection<V> values() {
		Enumeration<K> enumeration = hashTable.keys();
		ArrayList<V> list = new ArrayList<V>();
		for(; enumeration.hasMoreElements();) {
			list.add(hashTable.get(enumeration.nextElement()));
		}
		return list;
	}
	
}
