package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.net.http.HttpRequest;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonParser;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.automations.reports.OrderActivityList;
import com.mailerlite.sdk.ecommerce.CartBuilder;
import com.mailerlite.sdk.ecommerce.CartItemBuilderBody;
import com.mailerlite.sdk.ecommerce.CategoryBuilderBody;
import com.mailerlite.sdk.ecommerce.CustomerBuilderBody;
import com.mailerlite.sdk.ecommerce.Ecommerce;
import com.mailerlite.sdk.ecommerce.EcommerceReportRetriever;
import com.mailerlite.sdk.ecommerce.OrderBuilderBody;
import com.mailerlite.sdk.ecommerce.ProductBuilderBody;
import com.mailerlite.sdk.ecommerce.ShopsList;
import com.mailerlite.sdk.ecommerce.SingleEcommerceReport;
import com.mailerlite.sdk.ecommerce.SingleOrder;
import com.mailerlite.sdk.ecommerce.ProductVariantBuilderBody;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.MailerLiteHttpClientFactory;
import com.mailerlite.sdk.vcr.HttpClientVcr;
import com.mailerlite.sdk.vcr.HttpClientVcrResponse;
import com.mailerlite.sdk.vcr.VcrTape;

public class EcommerceTest extends TestBase {

	private static final String API = "https://connect.mailerlite.com/api";

	private static final String SHOP = API + "/ecommerce/shops/1";

	private HttpRequest lastRequest;

	private String responseBody;

	private MailerLite ml;

	private Ecommerce ecommerce;

	interface ApiCall {
		void run() throws MailerLiteException;
	}

	@BeforeEach
	public void setupEach()
	{
		responseBody = "{}";

		VcrTape capturingTape = new VcrTape() {
			@Override
			public HttpClientVcrResponse getRecordedResponse(HttpRequest request)
			{
				lastRequest = request;
				HttpClientVcrResponse response = new HttpClientVcrResponse();
				response.statusCode = 200;
				response.body = responseBody;
				response.headers = new HashMap<String, List<String>>();
				return response;
			}
		};

		HttpClientVcr client = new HttpClientVcr();
		client.setTape(capturingTape);
		MailerLiteHttpClientFactory.getInstance().setClient(client);

		ml = this.getMailerLite();
		ecommerce = ml.ecommerce();
	}

	@AfterEach
	public void afterEach()
	{
		MailerLiteHttpClientFactory.getInstance().setClient(null);
	}

	private void assertRequest(ApiCall call, String method, String url, String body) throws MailerLiteException
	{
		call.run();

		assertEquals(method, lastRequest.method());
		assertEquals(url, lastRequest.uri().toString());
		if (body != null) {
			assertEquals(JsonParser.parseString(body), JsonParser.parseString(readBody(lastRequest)));
		}
	}

	private static String readBody(HttpRequest request)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		request.bodyPublisher().get().subscribe(new Flow.Subscriber<ByteBuffer>() {
			public void onSubscribe(Flow.Subscription subscription) { subscription.request(Long.MAX_VALUE); }
			public void onNext(ByteBuffer item) { byte[] bytes = new byte[item.remaining()]; item.get(bytes); out.write(bytes, 0, bytes.length); }
			public void onError(Throwable throwable) { }
			public void onComplete() { }
		});
		return new String(out.toByteArray(), StandardCharsets.UTF_8);
	}

	@Test
	public void TestShopsAndSettings() throws MailerLiteException
	{
		assertRequest(() -> ecommerce.shops().retriever().limit(10).get(), "GET", API + "/ecommerce/shops?limit=10", null);
		assertRequest(() -> ecommerce.shops().retriever().getSingle("1"), "GET", SHOP, null);
		assertRequest(() -> ecommerce.shops().builder().name("Shop").currency("EUR").create(), "POST", API + "/ecommerce/shops", "{\"name\":\"Shop\",\"currency\":\"EUR\"}");
		assertRequest(() -> ecommerce.shops().builder().name("New").update("1"), "PUT", SHOP, "{\"name\":\"New\"}");
		assertRequest(() -> ecommerce.shops().delete("1"), "DELETE", SHOP, null);
		assertRequest(() -> ecommerce.shops().ecommerceReport("1").startDate("2026-10-01").endDate("2026-10-31").stats(), "GET", SHOP + "/reports/stats?start_date=2026-10-01&end_date=2026-10-31", null);
		assertRequest(() -> ecommerce.getSettings(), "GET", API + "/ecommerce/settings", null);
	}

	@Test
	public void TestProductsAndVariants() throws MailerLiteException
	{
		assertRequest(() -> ecommerce.products("1").retriever().enabledFilter(true).sort("-name").get(), "GET", SHOP + "/products?filter%5Benabled%5D=true&sort=-name", null);
		assertRequest(() -> ecommerce.products("1").retriever().getSingle("7"), "GET", SHOP + "/products/7", null);
		assertRequest(() -> ecommerce.products("1").builder().name("Hat").price(19.99).categoryId("5").create(), "POST", SHOP + "/products", "{\"name\":\"Hat\",\"price\":19.99,\"categories\":[\"5\"]}");
		assertRequest(() -> ecommerce.products("1").builder().name("Cap").update("7"), "PUT", SHOP + "/products/7", "{\"name\":\"Cap\"}");
		assertRequest(() -> ecommerce.products("1").delete("7"), "DELETE", SHOP + "/products/7", null);
		assertRequest(() -> ecommerce.products("1").changeStatus("7", false), "PUT", SHOP + "/products/7/change-status", "{\"status\":false}");
		assertRequest(() -> ecommerce.products("1").assignCategories("7", new String[] {"5"}, true), "PUT", SHOP + "/products/7/categories/multiple", "{\"categories\":[\"5\"],\"replace\":true}");

		ProductVariantBuilderBody variant = new ProductVariantBuilderBody();
		variant.name = "Large";
		variant.attributes.put("size", "L");

		assertRequest(() -> ecommerce.products("1").variants("7").retriever().get(), "GET", SHOP + "/products/7/variants", null);
		assertRequest(() -> ecommerce.products("1").variants("7").sync(Arrays.asList(variant)), "POST", SHOP + "/products/7/variants", "[{\"name\":\"Large\",\"attributes\":{\"size\":\"L\"}}]");
		assertRequest(() -> ecommerce.products("1").variants("7").builder().name("Large").attribute("size", "L").update("3"), "PUT", SHOP + "/products/7/variants/3", "{\"name\":\"Large\",\"attributes\":{\"size\":\"L\"}}");
		assertRequest(() -> ecommerce.products("1").variants("7").delete("3"), "DELETE", SHOP + "/products/7/variants/3", null);
	}

	@Test
	public void TestCategories() throws MailerLiteException
	{
		assertRequest(() -> ecommerce.categories("1").retriever().get(), "GET", SHOP + "/categories", null);
		assertRequest(() -> ecommerce.categories("1").retriever().getSingle("5"), "GET", SHOP + "/categories/5", null);
		assertRequest(() -> ecommerce.categories("1").builder().name("Hats").create(), "POST", SHOP + "/categories", "{\"name\":\"Hats\"}");
		assertRequest(() -> ecommerce.categories("1").builder().name("Caps").update("5"), "PUT", SHOP + "/categories/5", "{\"name\":\"Caps\"}");
		assertRequest(() -> ecommerce.categories("1").delete("5"), "DELETE", SHOP + "/categories/5", null);
		assertRequest(() -> ecommerce.categories("1").retriever().getProducts("5"), "GET", SHOP + "/categories/5/products", null);
		assertRequest(() -> ecommerce.categories("1").assignProduct("5", "7"), "PUT", SHOP + "/categories/5/products/7", null);
		assertRequest(() -> ecommerce.categories("1").unassignProduct("5", "7"), "DELETE", SHOP + "/categories/5/products/7", null);
	}

	@Test
	public void TestCustomersOrdersAndCarts() throws MailerLiteException
	{
		assertRequest(() -> ecommerce.customers("1").retriever().emailFilter("a@b.test").get(), "GET", SHOP + "/customers?filter%5Bemail%5D=a%40b.test", null);
		assertRequest(() -> ecommerce.customers("1").retriever().getSingle("3"), "GET", SHOP + "/customers/3", null);
		assertRequest(() -> ecommerce.customers("1").builder().email("a@b.test").create(), "POST", SHOP + "/customers", "{\"email\":\"a@b.test\"}");
		assertRequest(() -> ecommerce.customers("1").builder().name("Jane").update("3"), "PUT", SHOP + "/customers/3", "{\"name\":\"Jane\"}");
		assertRequest(() -> ecommerce.customers("1").delete("3"), "DELETE", SHOP + "/customers/3", null);

		assertRequest(() -> ecommerce.orders("1").retriever().attributedFilter(true).emailIdFilter("55").get(), "GET", SHOP + "/orders?filter%5Battributed%5D=true&filter%5Bemail_id%5D=55", null);
		assertRequest(() -> ecommerce.orders("1").retriever().getSingle("9"), "GET", SHOP + "/orders/9", null);
		CustomerBuilderBody customer = new CustomerBuilderBody();
		customer.email = "a@b.test";
		CartItemBuilderBody item = new CartItemBuilderBody();
		item.productId = "7";
		item.quantity = 1;

		assertRequest(() -> ecommerce.orders("1").builder().customer(customer).item(item).status("complete").create(), "POST", SHOP + "/orders", "{\"customer\":{\"email\":\"a@b.test\"},\"cart\":{\"items\":[{\"ecommerce_product_id\":\"7\",\"quantity\":1}]},\"status\":\"complete\"}");
		assertRequest(() -> ecommerce.orders("1").builder().customer(customer).item(item).status("complete").update("9"), "PUT", SHOP + "/orders/9", "{\"status\":\"complete\"}");
		assertRequest(() -> ecommerce.orders("1").delete("9"), "DELETE", SHOP + "/orders/9", null);

		assertRequest(() -> ecommerce.carts("1").retriever().get(), "GET", SHOP + "/carts", null);
		assertRequest(() -> ecommerce.carts("1").retriever().getSingle("4"), "GET", SHOP + "/carts/4", null);
		assertRequest(() -> {
			CartBuilder cart = ecommerce.carts("1").builder().cartTotal(10.0);
			cart.body().items = new ArrayList<CartItemBuilderBody>(Arrays.asList(item));
			cart.update("4");
		}, "PUT", SHOP + "/carts/4", "{\"cart_total\":10.0}");
		assertRequest(() -> ecommerce.carts("1").items("4").retriever().get(), "GET", SHOP + "/carts/4/items", null);
		assertRequest(() -> ecommerce.carts("1").items("4").retriever().getSingle("8"), "GET", SHOP + "/carts/4/items/8", null);
		assertRequest(() -> ecommerce.carts("1").items("4").builder().productId("7").create(), "POST", SHOP + "/carts/4/items", "{\"ecommerce_product_id\":\"7\"}");
		assertRequest(() -> ecommerce.carts("1").items("4").builder().quantity(2).update("8"), "PUT", SHOP + "/carts/4/items/8", "{\"quantity\":2}");
		assertRequest(() -> ecommerce.carts("1").items("4").delete("8"), "DELETE", SHOP + "/carts/4/items/8", null);
	}

	@Test
	public void TestImports() throws MailerLiteException
	{
		ProductBuilderBody product = new ProductBuilderBody();
		product.name = "Hat";
		CategoryBuilderBody category = new CategoryBuilderBody();
		category.name = "Hats";
		CustomerBuilderBody customer = new CustomerBuilderBody();
		customer.email = "a@b.test";

		assertRequest(() -> ecommerce.imports("1").products(Arrays.asList(product)), "POST", SHOP + "/products/import", "[{\"name\":\"Hat\"}]");
		assertRequest(() -> ecommerce.imports("1").categories(Arrays.asList(category)), "POST", SHOP + "/categories/import", "[{\"name\":\"Hats\"}]");
		assertRequest(() -> ecommerce.imports("1").customers(Arrays.asList(customer)), "POST", SHOP + "/customers/import", "[{\"email\":\"a@b.test\"}]");

		OrderBuilderBody order = new OrderBuilderBody();
		order.status = "complete";
		order.customer = customer;

		assertRequest(() -> ecommerce.imports("1").orders(Arrays.asList(order)), "POST", SHOP + "/orders/import", "{\"orders\":[{\"customer\":{\"email\":\"a@b.test\"},\"status\":\"complete\"}]}");
	}

	@Test
	public void TestRevenueReports() throws MailerLiteException
	{
		assertRequest(() -> ml.campaigns().ecommerceReport("2").shopId("1").stats(), "GET", API + "/campaigns/2/reports/ecommerce?shop_id=1", null);
		assertRequest(() -> ml.campaigns().ecommerceReport("2").shopId("1").page(2).orders(), "GET", API + "/campaigns/2/reports/ecommerce/orders?shop_id=1&page=2", null);
		assertRequest(() -> ml.subscribers().ecommerceReport("6").shopId("1").stats(), "GET", API + "/subscribers/6/reports/ecommerce?shop_id=1", null);
		assertRequest(() -> ml.subscribers().ecommerceReport("6").shopId("1").orders(), "GET", API + "/subscribers/6/reports/ecommerce/orders?shop_id=1", null);
		assertRequest(() -> {
			EcommerceReportRetriever report = ml.campaigns().ecommerceReport("2").shopId("1").limit(10);
			report.removeQueryParameter("limit");
			report.stats();
		}, "GET", API + "/campaigns/2/reports/ecommerce?shop_id=1", null);
		assertRequest(() -> ml.campaigns().ecommerceReport("2").shopId("1").timeFrame("all_time").shopId(null).stats(), "GET", API + "/campaigns/2/reports/ecommerce?time_frame=all_time", null);
	}

	@Test
	public void TestReportsWithoutOrdersRejectOrders()
	{
		assertThrows(MailerLiteException.class, () -> ecommerce.shops().ecommerceReport("1").orders());
	}

	@Test
	public void TestResponsesAreParsed() throws MailerLiteException
	{
		responseBody = "{\"data\":{\"ecommerce\":{\"orders_count\":4,\"revenue\":100.5,\"average_order_value\":25.125,\"conversion_rate\":\"2.5%\"},\"best_performing_email\":null}}";
		SingleEcommerceReport report = ml.campaigns().ecommerceReport("2").shopId("1").stats();
		assertEquals("2.5%", report.report.ecommerce.conversionRate);
		assertEquals(4, report.report.ecommerce.ordersCount);

		responseBody = "{\"data\":[{\"subscriber\":{\"email\":\"a@b.test\",\"id\":\"6\"},\"total\":25.5,\"updated_at\":\"2026-10-01 10:00:00\",\"order\":\"Hat x1\"}]}";
		OrderActivityList orders = ml.campaigns().ecommerceReport("2").shopId("1").orders();
		assertEquals("a@b.test", orders.orders[0].subscriber.email);
		assertNotNull(orders.orders[0].updatedAt);

		responseBody = "{\"data\":{\"id\":\"9\",\"total\":25.5,\"status\":\"complete\",\"created_at\":\"2026-10-01T10:00:00.000000Z\",\"updated_at\":\"2026-10-01 10:00:00\",\"cart\":{\"items\":[{\"quantity\":1,\"product\":{\"name\":\"Hat\",\"created_at\":\"2026-09-01 10:00:00\",\"categories\":[{\"name\":\"Hats\",\"created_at\":\"2026-08-01 10:00:00\"}]}}]}}}";
		SingleOrder order = ecommerce.orders("1").retriever().getSingle("9");
		assertEquals("complete", order.order.status);
		assertNotNull(order.order.createdAt);
		assertNotNull(order.order.updatedAt);
		assertNotNull(order.order.cart.items[0].product.createdAt);
		assertNotNull(order.order.cart.items[0].product.categories[0].createdAt);

		responseBody = "{\"data\":[{\"id\":\"1\",\"name\":\"Shop\",\"created_at\":\"2026-10-01 10:00:00\",\"group\":{\"id\":\"3\",\"created_at\":\"2026-10-01 10:00:00\"}}]}";
		ShopsList shops = ecommerce.shops().retriever().get();
		assertNotNull(shops.shops[0].createdAt);
		assertNotNull(shops.shops[0].group.createdAt);
	}
}
