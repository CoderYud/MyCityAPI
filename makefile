app:
	@echo "compile"
	javac -cp .:json-java.jar MyCity.java
	@echo
	
	@echo "Test Seattle"
	java -cp .:json-java.jar MyCity Seattle
	@echo

	@echo "Test Hong Kong"
	java -cp .:json-java.jar MyCity Hong Kong
	@echo

	@echo "Test Ho Chi Minh City"
	java -cp .:json-java.jar MyCity Ho Chi Minh City
	@echo "Test Renton"
	java -cp .:json-java.jar MyCity Renton