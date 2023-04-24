package dodd

class Item<T> implements Iterable<Item<T>> {
	
	final T value
	def next
	
	Item(value, next) {
		println "Creating item: $value"
		this.value = value
		this.next = next
	}
	
	Item<T> printGetNext() {
		print "$value${next ? ", " : "\n"}"
		next
	}
	
	@Override
	Iterator<Item<T>> iterator() {
		new Iterator<Item<T>>() {
			
			def item = Item.this
			
			@Override
			boolean hasNext() {
				item
			}
			
			@Override
			Item<T> next() {
				def next = item
				item = item.next
				return next
			}
		}
	}

	static <T, A, R> R fold(Closure<A> fSome, Closure<R> fLast, Closure<R> fEmpty, A accumulator, Item<T> item) {
		if (item != null) {
			Item<T> next = item.next
			if (next != null) {
				fold(fSome, fLast, fEmpty, fSome(item, next, accumulator), next) as R
			}
			else {
				fLast(item, accumulator)
			}
		}
		else {
			fEmpty(accumulator)
		}
	}

	static <T, A, R> R foldback(Closure<A> fSome, Closure<A> fLast, Closure<A> fEmpty, Closure<R> generator, Item<T> item) {
		if (item != null) {
			Item<T> next = item.next
			if (next != null) {
				foldback(fSome, fLast, fEmpty, { generator fSome(item, next, it) }, next) as R
			}
			else {
				generator(fLast(item))
			}
		}
		else {
			generator(fEmpty())
		}
	}
}
