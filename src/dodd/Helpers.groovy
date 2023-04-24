package dodd

class Helpers {
	
	static <T> Item<T> insertItem(Item<T> start, T val, insertBefore) {
		def current = start, previous
		
		while (current && !insertBefore(val, current)) {
			previous = current
			current = current.next
		}
		def item = new Item<T>(val, current)
		
		if (previous) {
			previous.next = item
		}
		else {
			start = item
		}
		
		start
	}
	
	static <T> Item<T> removeItem(Item<T> start, T val, valueEquals) {
		def current = start, previous
		
		while (current && !valueEquals(current, val)) {
			previous = current
			current = current.next
		}
		
		if (current) {
			if (previous) {
				previous.next = current.next
			}
			else {
				start = current.next
			}
			println "Removed item: $val"
		}
		else {
			println "Item $val does not exist!"
		}
		
		start
	}
	
	@SuppressWarnings("unused")
	static <T> Item<T> removeAll(Item<T> start) {
		null
	}
	
	static <T> void printLoop(Item<T> start) {
		while (start) {
			start = start.printGetNext()
		}
	}
	
	static <T> void printIterator(Item<T> start) {
		if (start) {
			for (Item<T> item : start) {
				item.printGetNext()
			}
		}
	}
	
	static <T> void printRecursive(Item<T> start) {
		if (start) {
			printRecursive start.printGetNext()
		}
	}

	static <T> void printFold(Item<T> start) {
		def fSome = { current, _, accumulator -> "$accumulator${current.value}, " }
		def fLast = { current, accumulator -> "$accumulator${current.value}\n" }
		def fEmpty = { it }
		print Item.fold(fSome, fLast, fEmpty, "", start)
	}

	static <T> void printFoldback(Item<T> start) {
		def fSome = { current, _, innerVal -> "${current.value}, $innerVal" }
		def fLast = { "${it.value}\n" }
		def fEmpty = { "" }
		print Item.foldback(fSome, fLast, fEmpty, { it }, start)
	}
}
