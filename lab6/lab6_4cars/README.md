## Notas

- Exemplo da criação do projeto
![Exemplo da criação do projeto](image.png)


Spring MockMvc dependency with Rest Assured
```
<dependency>
 <groupId>io.rest-assured</groupId>
 <artifactId>spring-mock-mvc</artifactId>
 <scope>test</scope>
</dependency>
 ``` 

Put always this:
```
@BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mvc);
    }
```


 Example of a RestAssured get:

```
@Test
	void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
		Car car1 = new Car("Toyota", "Corolla");
		Car car2 = new Car("Toyota", "Supra");
		Car car3 = new Car("Honda", "Civic");

		List<Car> allCars = Arrays.asList(car1, car2, car3);

		when(service.getAllCars()).thenReturn(allCars);

		RestAssuredMockMvc.given().contentType(MediaType.APPLICATION_JSON)
		.mockMvc(mvc).when().get("/api/cars")
		.then().statusCode(200)
		.body("size()", is(3))
    	.body("[0].model", is(car1.getModel()))
    	.body("[1].model", is(car2.getModel()))
    	.body("[2].model", is(car3.getModel()));

		verify(service, times(1)).getAllCars();
	}
``` 

Example of a RestAssured post:
```
@Test
	void whenPostCar_thenCreateCar() throws Exception {
	    Car car = new Car("Toyota", "Corolla");

	    when(service.save(any(Car.class))).thenReturn(car);

	    RestAssuredMockMvc.given()
		    .contentType(MediaType.APPLICATION_JSON)
		    .body(asJsonString(car))
		.when()
		    .post("/api/cars")
		.then()
		    .statusCode(201)
		    .body("maker", equalTo("Toyota"))
		    .body("model", equalTo("Corolla"));

	    verify(service, times(1)).save(any(Car.class));
	}

```