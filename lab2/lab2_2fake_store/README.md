#### b)
I can use mocks to simulate the expected response on the http request. For example if I request the product with id 3 ir should return:

```
{
    "id":3,
    "title":"Mens Cotton Jacket",
    "price":55.99,
    "description":"great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.","category":"men's clothing",
    "image":"https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg","rating":{"rate":4.7,"count":500}
}
```

The following testing implementation might be valid:

```
@ExtendWith(MockitoExtension.class)
public class ProductFinderServiceTest {
    @Mock
    ISimpleHttpClient client;

    @InjectMocks
    ProductFinderService product_finder;

    @BeforeEach
    public void setup(){
        String base_url = "https://fakestoreapi.com/products/";

        when(client.doHttpGet(base_url+3))
        .thenReturn("{\"id\":3,\"title\":\"Mens Cotton Jacket\",\"price\":55.99,\"description\":\"great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.\",\"category\":\"men's clothing\",\"image\":\"https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg\",\"rating\":{\"rate\":4.7,\"count\":500}}");
    }

    @Test
    public void getProductTest(){
        Product product = product_finder.findProductDetails(3).get();
        assertEquals(3, product.getId());
        assertEquals("Mens Cotton Jacket", product.getTitle());
    }

    public void getNonExistingProductTest(){
        Optional<Product> product = product_finder.findProductDetails(300);
        assertTrue(product.isEmpty());
    }
}
```