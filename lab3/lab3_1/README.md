## Resposta às alíneas da pergunta 1.

#### a)

In the file A_EmployeeRepositoryTest.java:

- test whenCreateAlex_thenReturnAlexEmployee()
```
assertThat(found).isNotNull().
                extracting(Employee::getName).isEqualTo(persistedAlex.getName());

```

- test givenSetOfEmployees_whenFindAll_thenReturnAllEmployees()
```
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

- test testFindEmplyeedByOrganizationDomain()
```
assertThat(results)
                .hasSize(2)
                .extracting(Employee::getEmail)
                .containsExactlyInAnyOrder(
                        "bob@ua.pt",
                        "ron@ua.pt"
                );

```

etc



## b) Take note of transitive annotations included in @DataJpaTest.

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(DataJpaTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration


## c) Identify an example in which you mock the behavior of the repository (and avoid involving a database).
In this example on the B_EmployeeService_UnitTest.java file.

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class B_EmployeeService_UnitTest {

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the BeforeEach
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }



## d) What is the difference between standard @Mock and @MockBean?

The **@Mock** is used to create a mock object within a test class (Mockito library) and run unit tests.
The **@MockBean** is used to mock objects in the Spring application context. It replaces the a specific bean for a mock one in the application context.


## e) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The file is used to configure the application properties for integration tests. When we run integration tests, it overrides the properties defined in the application.properties.


## f) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

The C is a unit tests that mocks the repository.
The D is a integration test that mocks the web enviroment and conection.
The E is a integration test that uses the real implementation of the components (no mocks used).
