package dodd

import java.util.regex.Pattern

class Main {

	static final Pattern VALID_REGEX = Pattern.compile("^(0|-?[1-9][0-9]*|[A-Za-z][0-9A-Z_a-z]*)\$")
	static final Pattern NUMBER_REGEX = Pattern.compile("^-?[0-9]+\$")

	static isValidString(str) {
		VALID_REGEX.matcher(str).matches()
	}
	
	static isNumberString(str) {
		NUMBER_REGEX.matcher(str).matches()
	}
	
	static insertBefore(String val, Item<String> oth) {
		if (isNumberString(val) && isNumberString(oth.value)) {
			new BigInteger(val) <= new BigInteger(oth.value)
		}
		else {
			val <= oth.value
		}
	}
	
	static valueEquals(item, val) {
		item.value == val
	}
	
	static main(args) {
		BufferedReader reader = [[System.in] as InputStreamReader]
		
		def start = null
		
		def begin = true
		def input = null
		
		while (true) {
			if (!begin) {
				println()
			}
			else {
				begin = false
			}
			
			println "Awaiting input..."
			try {
				input = reader.readLine()
			}
			catch (e) {
				e.printStackTrace()
			}
			
			if (input.isEmpty()) {
				println "\nProgram terminated!"
				//start = Helpers.removeAll start
				return
			}
			else if (input.charAt(0) == '~' as char) {
				if (input.length() == 1) {
					println "\nDeleting list..."
					start = Helpers.removeAll start
				}
				else {
					input = input.substring 1
					if (isValidString input) {
						println "\nRemoving item..."
						start = Helpers.removeItem start, input, this.&valueEquals
					}
					else {
						println "\nCould not parse input!"
					}
				}
			}
			else if (input == "l") {
				println "\nList print..."
				Helpers.printList start
			}
			else if (input == "i") {
				println "\nIterator print..."
				Helpers.printIterator start
			}
			else if (input == "a") {
				println "\nArray print not implemented!"
			}
			else if (input == "r") {
				println "\nRecursive print..."
				Helpers.printRecursive start
			}
			else if (input == "f") {
				println "\nFold print..."
				Helpers.printFold start
			}
			else if (input == "b") {
				println "\nFoldback print..."
				Helpers.printFoldback start
			}
			else if (isValidString input) {
				println "\nInserting item..."
				start = Helpers.insertItem start, input, this.&insertBefore
			}
			else {
				println "\nCould not parse input!"
			}
		}
	}
}
